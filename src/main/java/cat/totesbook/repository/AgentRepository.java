package cat.totesbook.repository;

import cat.totesbook.domain.Agent;
import java.util.List;

/**
 * Interfície per a les operacions de dades de la taula Agents.
 * 
 * @author Equip TotEsBook
 */
public interface AgentRepository {

    /**
     * Comprova el login d'un Agent verificant la seva contrasenya hashejada.
     *
     * @param email L'email del formulari.
     * @param passwordPla La contrasenya en TEXT PLA del formulari.
     * @return L'objecte Agent si és correcte, o null si no ho és.
     */
    Agent getAgentByEmailAndContrasenya(String email, String passwordPla);

    /**
     * Actualitza el 'tipus' (rol) d'un agent a la base de dades. Aquest mètode
     * és necessari per al CanviRolServlet.
     *
     * @param idAgent L'ID de l'agent a modificar.
     * @param nouTipus El tipus administrador o bibliotecari.
     */
    void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus);

    /**
     * Retorna una llista de tots els Agents (bibliotecaris i administradors).
     *
     * @return Llista d'objectes Agent.
     */
    List<Agent> getAllAgents();

    /**
     * Desa l'agent.
     * @param agent L'agent.
     */
    void saveAgent(Agent agent);

    /**
     * Retorna tots els bibliotecaris.
     * 
     * @return Llista de tots els bibliotecaris.
     */
    List<Agent> getAllBibliotecaris();

    /**
     * Retorna tots els agents segons l'ID.
     * 
     * @param idAgent ID de l'usuari.
     * @return Object agent amb el seu ID.
     */
    Agent getAgentById(int idAgent);

    /**
     * Actualitza l'agent.
     * 
     * @param desti El destí.
     * @param origen L'origen.
     */
    void actualitzarAgentDesDe(Agent desti, Agent origen);

    /**
     * Actualitza la contrasenya de l'agent.
     * 
     * @param idAgent L'ID de l'agent.
     * @param novaPwd La contrasenya nova.
     */
    void updatePassword(int idAgent, String novaPwd);

    /**
     * Cerca un agent només pel seu email (útil per recuperació de contrasenya).
     *
     * @param email L'email a cercar.
     * @return L'objecte Agent si es troba, o null si no es troba.
     */
    Agent getAgentByEmail(String email);
}
