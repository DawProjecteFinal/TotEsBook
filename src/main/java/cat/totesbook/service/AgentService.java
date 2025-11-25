/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import java.util.List;


public interface AgentService {

    Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana);

    void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus);

    List<Agent> getAllAgents();

    void saveAgent(Agent agent);
    
    List<Agent> getAllBibliotecaris();
    
    Agent getAgentById(int idAgent);
}
