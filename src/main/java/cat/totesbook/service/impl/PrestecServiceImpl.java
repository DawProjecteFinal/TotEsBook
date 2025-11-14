package cat.totesbook.service.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.repository.LlibreRepository;
import cat.totesbook.repository.PrestecRepository;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.PrestecService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmiro
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

    @Override
    @Transactional
    public void registrarPrestec(String isbn, String emailUsuari, int idAgent) {
        // Tota aquesta lògica s'executa dins d'UNA ÚNICA transacció.

        // 1. Recuperem l'agent i la seva biblioteca. Com que estem dins d'una transacció,
        // la càrrega LAZY de la biblioteca funcionarà correctament.
        Agent agent = agentRepository.getAgentById(idAgent);
        if (agent == null) {
            throw new IllegalArgumentException("L'agent amb ID " + idAgent + " no existeix.");
        }
        Biblioteca biblioteca = agent.getBiblioteca();
        if (biblioteca == null) {
            throw new IllegalStateException("L'agent " + agent.getEmail() + " no té una biblioteca assignada.");
        }

        // 2. Recuperem l'usuari i el llibre
        Usuari usuari = usuariRepository.getUsuariByEmail(emailUsuari);
        if (usuari == null) {
            throw new IllegalArgumentException("No s'ha trobat cap usuari amb l'email " + emailUsuari);
        }
        Llibre llibre = llibreRepository.getLlibreByIsbn(isbn)
                .orElseThrow(() -> new IllegalArgumentException("No s'ha trobat cap llibre amb l'ISBN " + isbn));

        // 3. Creem i persistim el préstec
        Prestec prestec = new Prestec(usuari, llibre, biblioteca, agent);
        prestecRepository.registrarPrestec(prestec);
    }

    @Override
    public List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca) {
        return prestecRepository.findActiusByBiblioteca(biblioteca);
    }

}
