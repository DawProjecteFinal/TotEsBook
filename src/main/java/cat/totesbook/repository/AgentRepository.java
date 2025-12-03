/**
 *
 * @author Equip TotEsBook
 */
package cat.totesbook.repository;

import cat.totesbook.domain.Agent;
import java.util.List;

/**
 * Interfície per a les operacions de dades de la taula Agents.
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

    void saveAgent(Agent agent);

    List<Agent> getAllBibliotecaris();

    Agent getAgentById(int idAgent);

    void actualitzarAgentDesDe(Agent desti, Agent origen);

    void updatePassword(int idAgent, String novaPwd);

    /**
     * Cerca un agent només pel seu email (útil per recuperació de contrasenya).
     *
     * @param email L'email a cercar.
     * @return L'objecte Agent si es troba, o null si no es troba.
     */
    Agent getAgentByEmail(String email);
}
