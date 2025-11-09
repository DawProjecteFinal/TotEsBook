package cat.totesbook.service.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.service.AgentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jmiro
 */
@Service
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.getAllAgents();
    }

    @Override
    public void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus) {
        agentRepository.updateAgentTipus(idAgent, nouTipus);
    }

    @Override
    public Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana) {
        return agentRepository.getAgentByEmailAndContrasenya(email, contrasenyaPlana);
    }

    @Override
    public void saveAgent(Agent agent) {
        agentRepository.saveAgent(agent);
    }
    
    @Override
    public List<Agent> getAllBibliotecaris() {
        return agentRepository.getAllBibliotecaris();
    }
}
