package cat.totesbook.service.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.service.AgentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe que implementa el servei dels agents.
 * 
 * @author Equip TotEsBook
 */
@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    @Autowired
    private AgentRepository agentRepository;

    /**
     * Mètode retorna una llista de tots els agents.
     * 
     * @return llista de tots els agents.
     */
    @Override
    //@Transactional(readOnly = true)
    public List<Agent> getAllAgents() {
        return agentRepository.getAllAgents();
    }

    /**
     * 
     * Mètode que actualitza el tipus d'agent.
     * 
     * @param idAgent id de l'agent.
     * @param nouTipus tipologia d'agent.
     */
    @Override
    //@Transactional
    public void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus) {
        agentRepository.updateAgentTipus(idAgent, nouTipus);
    }

    /**
     * Mètode que retorna un agent segons el correu electrònic i la contrasenya.
     * 
     * @param email correu electrònic.
     * @param contrasenyaPlana contrasenya.
     * @return agent amb el correu electrònic i la contrasenya indicades.
     */
    @Override
    //@Transactional(readOnly = true)
    public Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana) {
        return agentRepository.getAgentByEmailAndContrasenya(email, contrasenyaPlana);
    }

    /**
     * Mètode que desa l'agent.
     * 
     * @param agent agent.
     */
    @Override
    //@Transactional
    public void saveAgent(Agent agent) {
        agentRepository.saveAgent(agent);
    }
    
    /**
     * Mètode que retorna tots els bibliotecaris.
     * 
     * @return llistat amb tots els bibliotecaris.
     */
    @Override
    //@Transactional(readOnly = true)
    public List<Agent> getAllBibliotecaris() {
        return agentRepository.getAllBibliotecaris();
    }
    
     /**
     * Mètode que retorna l'agent amb l'id indicat.
     * 
     * @param idAgent id agent.
     * @return Objecte agent amb l'id passat.
     */
    @Override
    //@Transactional(readOnly = true)
    public Agent getAgentById(int idAgent){
        return agentRepository.getAgentById(idAgent);
    }
}
