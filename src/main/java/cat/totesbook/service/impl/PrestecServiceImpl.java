package cat.totesbook.service.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Prestec.EstatPrestec;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Reserva.EstatReserva;
import cat.totesbook.domain.Usuari;
import cat.totesbook.dto.AutorEstadisticaDTO;
import cat.totesbook.dto.LlibreEstadisticaDTO;
import cat.totesbook.dto.UsuariEstadisticaDTO;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.repository.BibliotecaLlibreRepository;
import cat.totesbook.repository.LlibreRepository;
import cat.totesbook.repository.PrestecRepository;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.PrestecService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe que implementa el servei dels préstecs.
 * 
 * @author Equip TotEsBook
 */
@Service
@Transactional
public class PrestecServiceImpl implements PrestecService {

    @Autowired
    private PrestecRepository prestecRepository;

    @Autowired
    private AgentRepository agentRepository;

    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private LlibreRepository llibreRepository;

    @Autowired
    private BibliotecaLlibreRepository bibliotecaLlibreRepository;

    // Tota aquesta lògica s'executa dins d'UNA ÚNICA transacció.
    // Hem de realitzar diferents 
    // accions a dintre de una mateixa transacció. Per això ho coordinem tot a 
    // dintre de la capa de servei
    
    /**
     * Registrar un préstec.
     * 
     * @param isbn L'ISBN de l'usari.
     * @param emailUsuari El correu electrònic de l'usuari.
     * @param idAgentBibliotecari L'ID del bibliotecari.
     */
    @Override
    public void registrarPrestec(String isbn, String emailUsuari, int idAgentBibliotecari) {

        // Buscar usuari
        Usuari usuari = usuariRepository.getUsuariByEmail(emailUsuari);

        if (usuari == null) {
            throw new RuntimeException("No s'ha trobat cap usuari amb aquest email.");
        }

        // Buscar llibre
        Llibre llibre = llibreRepository.getLlibreByIsbn(isbn)
                .orElseThrow(() -> new RuntimeException("No existeix cap llibre amb aquest ISBN."));

        // Buscar biblioteca del bibliotecari
        Agent agentPrestec = agentRepository.getAgentById(idAgentBibliotecari);
        Biblioteca biblioteca = agentPrestec.getBiblioteca();

        if (biblioteca == null) {
            throw new RuntimeException("El bibliotecari no té una biblioteca assignada.");
        }

        // Buscar relació biblioteca i llibre
        Optional<BibliotecaLlibre> optBL
                = bibliotecaLlibreRepository.findByBibliotecaAndLlibre(biblioteca, llibre);

        if (optBL.isEmpty()) {
            throw new RuntimeException("Aquest llibre no està disponible en aquesta biblioteca.");
        }

        BibliotecaLlibre bl = optBL.get();

        // Comprovar exemplars disponibles
        if (bl.getDisponibles() <= 0) {
            throw new RuntimeException("No hi ha exemplars disponibles per fer el préstec.");
        }

        // Descomptar un exemplar
        bl.setDisponibles(bl.getDisponibles() - 1);
        bibliotecaLlibreRepository.updateBibliotecaLlibre(bl);

        // Crear préstec
        Prestec prestec = new Prestec();
        prestec.setUsuari(usuari);
        prestec.setLlibre(llibre);
        prestec.setBiblioteca(biblioteca);
        prestec.setDataPrestec(LocalDateTime.now());
        prestec.setEstat(EstatPrestec.actiu);
        prestec.setAgentPrestec(agentPrestec);

        prestecRepository.registrarPrestec(prestec);
    }

    /**
     * Cerca els préstecs actius segons la biblioteca.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llistat amb els préstecs actius segons la biblioteca.
     */
    @Override
    public List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca) {
        return prestecRepository.findActiusByBiblioteca(biblioteca);
    }

    // hem de orquestrar diferents entitats per a poder registrar una devolució
    
    /**
     * Registrar una devolució de llibre.
     * 
     * @param isbn L'ISBN.
     * @param emailUsuari El correu de l'usuari.
     * @param idAgentBibliotecari L'ID del bibliotecari.
     */
    @Override
    public void registrarDevolucio(String isbn, String emailUsuari, Integer idAgentBibliotecari) {

        // Buscar usuari pel seu email
        Usuari usuari = usuariRepository.getUsuariByEmail(emailUsuari);
        if (usuari == null) {
            throw new RuntimeException("No s'ha trobat cap usuari amb aquest email.");
        }

        // Buscar préstec actiu
        Prestec prestec = prestecRepository.findPrestecActiu(isbn, usuari.getId())
                .orElseThrow(() -> new RuntimeException(
                "No existeix cap préstec actiu amb aquest ISBN i usuari."));

        // Buscar el bibliotecari que fa la devolució
        Agent agentDevolucio = agentRepository.getAgentById(idAgentBibliotecari);
        if (agentDevolucio == null) {
            throw new RuntimeException("No s'ha trobat l'agent que registra la devolució.");
        }

        // Actualitzar dades del préstec
        prestec.setDataDevolucio(LocalDateTime.now());
        prestec.setAgentDevolucio(agentDevolucio);
        prestec.setEstat(EstatPrestec.retornat);

        prestecRepository.updatePrestec(prestec);

        // Incrementar exemplars disponibles
        Llibre llibre = prestec.getLlibre();
        Biblioteca biblioteca = prestec.getBiblioteca();

        Optional<BibliotecaLlibre> optBL
                = bibliotecaLlibreRepository.findByBibliotecaAndLlibre(biblioteca, llibre);

        if (optBL.isEmpty()) {
            throw new RuntimeException("ERROR: No s'ha trobat la relació Biblioteca-Llibre per tornar exemplars.");
        }

        BibliotecaLlibre bl = optBL.get();
        bl.setDisponibles(bl.getDisponibles() + 1);

        bibliotecaLlibreRepository.updateBibliotecaLlibre(bl);
    }

    /**
     * Retorna els préstecs actius segons l'usuari indicat.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb els préstecs actius segons l'usuari indicat.
     */
    @Override
    public List<Prestec> findPrestecsActiusByUsuari(int idUsuari) {
        return prestecRepository.findPrestecsActiusByUsuari(idUsuari);
    }

    /**
     * Retorna les devolucions segons a les biblioteques on s'han fet.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llista amb les devolucions segons a les biblioteques on s'han fet.
     */
    @Override
    public List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca) {
        return prestecRepository.findDevolucionsByBiblioteca(biblioteca);
    }

    // Implementació per renovar préstec
    /**
     * Renovar el préstec.
     * 
     * @param idPrestec L'ID del préstec.
     */
    @Override
    public void renovarPrestec(Integer idPrestec) {

        Prestec p = prestecRepository.findById(idPrestec)
                .orElseThrow(() -> new IllegalArgumentException("Préstec no trobat"));

        LocalDateTime novaDataInici = p.getDataPrestec().plusDays(30);
        p.setDataPrestec(novaDataInici);

        prestecRepository.updatePrestec(p);

        //prestecRepository.save(p);
    }

    /**
     * Retorna els préstecs retornats segons l'usuari indicat.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb els préstecs retornts segons l'usuari indicat.
     */
    @Override
    public List<Prestec> findPrestecsRetornatsByUsuari(Integer idUsuari) {
        return prestecRepository.findPrestecsRetornatsByUsuari(idUsuari);
    }

    // Retornar prèstec en botó
    
    /**
     * Retornar el préstec.
     * 
     * @param idPrestec L'ID del préstec.
     * @param idAgentBibliotecari L'ID del bibliotecari.
     */
    @Override
    public void retornarPrestec(Integer idPrestec, Integer idAgentBibliotecari) {
        Prestec prestec = prestecRepository.findById(idPrestec)
                .orElseThrow(() -> new RuntimeException("No s'ha trobat el préstec amb ID: " + idPrestec));

        if (prestec.getDataDevolucio() != null) {
            throw new RuntimeException("Aquest préstec ja ha estat retornat.");
        }

        finalitzarPrestec(prestec, idAgentBibliotecari);
    }

    // Mètode privat per no duplicar codi entre les dues formes de retornar
    /**
     * Mètode que finalitza el préstec.
     * 
     * @param prestec El préstec.
     * @param idAgentBibliotecari L'ID del bibliotecari. 
     */
    private void finalitzarPrestec(Prestec prestec, Integer idAgentBibliotecari) {
        Agent agent = agentRepository.getAgentById(idAgentBibliotecari);

        // Tancar el préstec
        prestec.setDataDevolucio(LocalDateTime.now());
        prestec.setAgentDevolucio(agent);
        prestec.setEstat(EstatPrestec.retornat);
        prestecRepository.updatePrestec(prestec);

        // Incrementar l'estoc
        Optional<BibliotecaLlibre> optBL = bibliotecaLlibreRepository.findByBibliotecaAndLlibre(prestec.getBiblioteca(), prestec.getLlibre());
        if (optBL.isPresent()) {
            BibliotecaLlibre bl = optBL.get();
            bl.setDisponibles(bl.getDisponibles() + 1);
            bibliotecaLlibreRepository.updateBibliotecaLlibre(bl);
        }
    }

    /**
     * Crear un préstec des de la reserva.
     * 
     * @param reserva La reseva.
     * @param agentBibliotecari El bibliotecari.
     * @return Objecte Préstec.
     */
    public Prestec crearPrestecDesDeReserva(Reserva reserva, Agent agentBibliotecari) {

        if (reserva == null) {
            throw new RuntimeException("La reserva és nul·la.");
        }

        if (reserva.getEstat() != EstatReserva.pendent) {
            throw new RuntimeException("Només es poden gestionar reserves pendents.");
        }

        Usuari usuari = reserva.getUsuari();
        Llibre llibre = reserva.getLlibre();
        Biblioteca biblioteca = agentBibliotecari.getBiblioteca();
        // Validar agent
        if (agentBibliotecari == null) {
            throw new RuntimeException("No s'ha proporcionat cap bibliotecari.");
        }

        if (agentBibliotecari.getBiblioteca() == null
                || !agentBibliotecari.getBiblioteca().equals(biblioteca)) {

            throw new RuntimeException("El bibliotecari no pertany a la biblioteca d'aquesta reserva.");
        }

        // Buscar relació Biblioteca - Llibre
        Optional<BibliotecaLlibre> optBL
                = bibliotecaLlibreRepository.findByBibliotecaAndLlibre(biblioteca, llibre);

        if (optBL.isEmpty()) {
            throw new RuntimeException("Aquest llibre no està disponible en aquesta biblioteca.");
        }

        BibliotecaLlibre bl = optBL.get();

        // Comprovar disponibilitat
        if (bl.getDisponibles() <= 0) {
            throw new RuntimeException("No hi ha exemplars disponibles per fer el préstec.");
        }

        // Descomptar un exemplar
        bl.setDisponibles(bl.getDisponibles() - 1);
        bibliotecaLlibreRepository.updateBibliotecaLlibre(bl);

        // Crear objecte préstec
        Prestec prestec = new Prestec();
        prestec.setUsuari(usuari);
        prestec.setLlibre(llibre);
        prestec.setBiblioteca(biblioteca);
        prestec.setAgentPrestec(agentBibliotecari);
        prestec.setDataPrestec(LocalDateTime.now());
        prestec.setEstat(EstatPrestec.actiu);

        // Guardar el préstec
        prestecRepository.registrarPrestec(prestec);

        return prestec;
    }

    // --- ===== SPRINT 3 (TEA 5) ===== ---
    // Mètodes per treure estadístiques
    
    /**
     * Retorna les estadístiques dels llibres segons la categoria.
     * 
     * @param autor L'autor.
     * @param categoria La categoria.
     * @return Llista de les estadístiques dels llibres segons la categoria..
     */
    @Override
    public List<LlibreEstadisticaDTO> getEstadistiquesLlibres(String autor, String categoria) {
        return prestecRepository.findEstadistiquesLlibres(autor, categoria);
    }

    /**
     * Retorna les estadístiques dels autors.
     * 
     * @return Llista de les estadístiques dels autors.
     */
    @Override
    public List<AutorEstadisticaDTO> getEstadistiquesAutors() {
        return prestecRepository.findEstadistiquesAutors();
    }

    /**
     * Retorna les estadístiques dels usuaris.
     * 
     * @return Llista de les estadístiques dels usuaris.
     */
    @Override
    public List<UsuariEstadisticaDTO> getEstadistiquesUsuaris() {
        return prestecRepository.findEstadistiquesUsuaris();
    }

    /**
     * Retornar el préstec amb l'ID indicat.
     * 
     * @param idPrestec L'ID del préstec.
     * @return Objecte préstec segons l'ID passat.
     */
    @Override
    public Prestec getPrestecPerId(Integer idPrestec) {
        return prestecRepository.findById(idPrestec).orElse(null);
    }
}
