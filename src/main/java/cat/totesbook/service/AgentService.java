package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import java.util.List;

/**
 * Interfície del servei de agents.
 * 
 * @author Equip TotEsBook
 */
public interface AgentService {

    /**
     * Mètode que retorna un agent segons el correu electrònic i la contrasenya.
     * 
     * @param email correu electrònic.
     * @param contrasenyaPlana contrasenya.
     * @return agent amb el correu electrònic i la contrasenya indicades.
     */
    Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana);

    /**
     * 
     * Mètode que actualitza el tipus d'agent.
     * 
     * @param idAgent id de l'agent.
     * @param nouTipus tipologia d'agent.
     */
    void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus);

    /**
     * Mètode retorna una llista de tots els agents.
     * 
     * @return llista de tots els agents.
     */
    List<Agent> getAllAgents();

    /**
     * Mètode que desa l'agent.
     * 
     * @param agent agent.
     */
    void saveAgent(Agent agent);
    
    /**
     * Mètode que retorna tots els bibliotecaris.
     * 
     * @return llistat amb tots els bibliotecaris.
     */
    List<Agent> getAllBibliotecaris();
    
     /**
     * Mètode que retorna l'agent amb l'id indicat.
     * 
     * @param idAgent id agent.
     * @return Objecte agent amb l'id passat.
     */
    Agent getAgentById(int idAgent);
}
