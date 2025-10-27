package cat.totesbook.repository;

import cat.totesbook.domain.Agent;

/**
 * Interfície per a les operacions de dades de la taula Agents.
 */
public interface AgentRepository {
    
    /**
     * Comprova el login d'un Agent (staff) verificant la seva contrasenya hashejada.
     * @param email L'email del formulari.
     * @param passwordPla La contrasenya en TEXT PLA del formulari.
     * @return L'objecte Agent si és correcte, o null si no ho és.
     */
    Agent findAgentByEmailAndPassword(String email, String passwordPla);
    
    // Pots afegir altres mètodes necessaris aquí (ex: findAllAgents(), saveAgent(), etc.)
}