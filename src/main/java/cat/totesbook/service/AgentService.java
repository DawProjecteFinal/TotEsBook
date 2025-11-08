package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import java.util.List;

/**
 *
 * @author jmiro
 */
public interface AgentService {

    Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana);

    void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus);

    List<Agent> getAllAgents();

    void saveAgent(Agent agent);
}
