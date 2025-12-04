/**
 *
 * @author Equip TotEsBook
 */
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

    @Override
    public List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca) {
        return prestecRepository.findActiusByBiblioteca(biblioteca);
    }

    // hem de orquestrar diferents entitats per a poder registrar una devolució
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

    @Override
    public List<Prestec> findPrestecsActiusByUsuari(int idUsuari) {
        return prestecRepository.findPrestecsActiusByUsuari(idUsuari);
    }

    @Override
    public List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca) {
        return prestecRepository.findDevolucionsByBiblioteca(biblioteca);
    }

    // Implementació per renovar préstec
    @Override
    public void renovarPrestec(Integer idPrestec) {

        Prestec p = prestecRepository.findById(idPrestec)
                .orElseThrow(() -> new IllegalArgumentException("Préstec no trobat"));

        LocalDateTime novaDataInici = p.getDataPrestec().plusDays(30);
        p.setDataPrestec(novaDataInici);

        prestecRepository.updatePrestec(p);

        //prestecRepository.save(p);
    }

    @Override
    public List<Prestec> findPrestecsRetornatsByUsuari(Integer idUsuari) {
        return prestecRepository.findPrestecsRetornatsByUsuari(idUsuari);
    }

    // Retornar prèstec en botó
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
    @Override
    public List<LlibreEstadisticaDTO> getEstadistiquesLlibres(String autor, String categoria) {
        return prestecRepository.findEstadistiquesLlibres(autor, categoria);
    }

    @Override
    public List<AutorEstadisticaDTO> getEstadistiquesAutors() {
        return prestecRepository.findEstadistiquesAutors();
    }

    @Override
    public List<UsuariEstadisticaDTO> getEstadistiquesUsuaris() {
        return prestecRepository.findEstadistiquesUsuaris();
    }

    @Override
    public Prestec getPrestecPerId(Integer idPrestec) {
        return prestecRepository.findById(idPrestec).orElse(null);
    }
}
