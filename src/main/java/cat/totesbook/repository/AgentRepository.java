package cat.totesbook.repository;

import cat.totesbook.domain.Agent;
import java.util.List;
import cat.totesbook.repository.impl.*;

/**
 * Interfície per a les operacions de dades de la taula Agents.
 */
public interface AgentRepository {

    /**
     * Comprova el login d'un Agent (staff) verificant la seva contrasenya
     * hashejada.
     *
     * @param email L'email del formulari.
     * @param passwordPla La contrasenya en TEXT PLA del formulari.
     * @return L'objecte Agent si és correcte, o null si no ho és.
     */
    Agent getAgentByEmailAndContrasenya(String email, String passwordPla);

    /**
     * AFEGIT: Actualitza el 'tipus' (rol) d'un agent a la base de dades. Aquest
     * mètode és necessari per al CanviRolServlet.
     *
     * @param idAgent L'ID de l'agent a modificar.
     * @param nouTipus El nou tipus ('administrador' o 'bibliotecari').
     */
    void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus);

    /**
     * AFEGIT: Retorna una llista de tots els Agents (bibliotecaris i
     * administradors). Aquest mètode és necessari per al dashboard_admin.jsp
     * per poder llistar els agents.
     *
     * @return Llista d'objectes Agent.
     */
    List<Agent> getAllAgents();

    void saveAgent(Agent agent);

    List<Agent> getAllBibliotecaris();
}
