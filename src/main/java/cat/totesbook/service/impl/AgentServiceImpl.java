/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.service.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.service.AgentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    @Override
    //@Transactional(readOnly = true)
    public List<Agent> getAllAgents() {
        return agentRepository.getAllAgents();
    }

    @Override
    //@Transactional
    public void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus) {
        agentRepository.updateAgentTipus(idAgent, nouTipus);
    }

    @Override
    //@Transactional(readOnly = true)
    public Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana) {
        return agentRepository.getAgentByEmailAndContrasenya(email, contrasenyaPlana);
    }

    @Override
    //@Transactional
    public void saveAgent(Agent agent) {
        agentRepository.saveAgent(agent);
    }
    
    @Override
    //@Transactional(readOnly = true)
    public List<Agent> getAllBibliotecaris() {
        return agentRepository.getAllBibliotecaris();
    }
    
    @Override
    //@Transactional(readOnly = true)
    public Agent getAgentById(int idAgent){
        return agentRepository.getAgentById(idAgent);
    }
}
