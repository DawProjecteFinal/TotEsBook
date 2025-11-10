package cat.totesbook.repository.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.repository.AgentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class AgentDAO implements AgentRepository {

    @PersistenceContext(unitName = "totesbookPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Retorna un agent pel seu email i contrasenya (verificant hash BCrypt).
     */
    @Override
    public Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana) {
        try {
            // Fem un join per a carregar la biblioteca assignada al bibliotecari
            List<Agent> result = entityManager.createQuery(
                    "SELECT a FROM Agent a LEFT JOIN FETCH a.biblioteca WHERE a.email = :email",
                    Agent.class)
                    .setParameter("email", email)
                    .getResultList();

            if (!result.isEmpty()) {
                Agent agent = result.get(0);
                if (BCrypt.checkpw(contrasenyaPlana, agent.getContrasenya())) {
                    return agent;
                }
            }
        } catch (Exception e) {
            System.err.println("Error a AgentDAO.getAgentByEmailAndContrasenya: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualitza el tipus d'un agent (per exemple, de BIBLIOTECARI a
     * ADMINISTRADOR).
     */
    @Override
    public void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus) {
        try {
            Agent agent = entityManager.find(Agent.class, idAgent);
            if (agent != null) {
                agent.setTipus(nouTipus);
                entityManager.merge(agent);
                System.out.println(">>> [JPA] Tipus d'agent actualitzat: " + agent.getNom() + " → " + nouTipus);
            } else {
                System.out.println(">>> [JPA] No s'ha trobat cap agent amb id " + idAgent);
            }
        } catch (Exception e) {
            System.err.println("Error a AgentDAO.updateAgentTipus: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retorna tots els agents registrats.
     */
    @Override
    public List<Agent> getAllAgents() {
        try {
            return entityManager.createQuery("SELECT a FROM Agent a", Agent.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error a AgentDAO.getAllAgents: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * Desa o actualitza un agent (opcional, per compatibilitat futura).
     */
    @Override
    public void saveAgent(Agent agent) {
        Agent existent = entityManager.find(Agent.class, agent.getIdAgent());
        if (existent == null) {
            // Nova alta
            // Ens assegurem que la contrasenya està hashejada
            if (agent.getContrasenya() != null && !agent.getContrasenya().startsWith("$2a$")) {
                agent.setContrasenya(BCrypt.hashpw(agent.getContrasenya(), BCrypt.gensalt()));
            }
            entityManager.persist(agent);
            System.out.println(">>> [JPA] Agent nou afegit: " + agent.getNom());
        } else {
            // Actualització
            actualitzarAgentDesDe(existent, agent);
            entityManager.merge(existent);
            System.out.println(">>> [JPA] Agent actualitzat: " + existent.getNom());
        }
    }

    /**
     * Copia els camps d'un agent origen cap a un existent.
     */
    private void actualitzarAgentDesDe(Agent desti, Agent origen) {
        desti.setNom(origen.getNom());
        desti.setCognoms(origen.getCognoms());
        desti.setTelefon(origen.getTelefon());
        desti.setEmail(origen.getEmail());
        desti.setTipus(origen.getTipus());

        // Només actualitzem la contrasenya si s'ha passat una nova
        if (origen.getContrasenya() != null && !origen.getContrasenya().isBlank()) {
            desti.setContrasenya(BCrypt.hashpw(origen.getContrasenya(), BCrypt.gensalt()));
        }
    }

    /**
     * Nomes mostra els agents que són bibliotecaris
     *
     * @return
     */
    @Override
    public List<Agent> getAllBibliotecaris() {
        try {
            return entityManager.createQuery(
                    "SELECT a FROM Agent a WHERE a.tipus = :tipus", Agent.class)
                    .setParameter("tipus", Agent.TipusAgent.bibliotecari)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error a AgentDAO.getAllBibliotecaris: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    /**
     * 
     * @param idAgent
     * @return 
     */
    @Override
    public Agent getAgentById(int idAgent) {
        try {
            return entityManager.find(Agent.class, idAgent);
        } catch (Exception e) {
            System.err.println("Error a AgentDAO.getById: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
