/**
 *
 * @author edinsonioc
 */
package cat.totesbook.repository.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.repository.impl.*;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.config.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgentDAO implements AgentRepository {

    /**
     *
     * @param email
     * @param contrasenyaPlana
     * @return
     */
    @Override
    public Agent getAgentByEmailAndContrasenya(String email, String contrasenyaPlana) {
        
        String sql = "SELECT * FROM Agents WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String contrasenyaHashejatDeLaDB = rs.getString("contrasenya");

                    if (BCrypt.checkpw(contrasenyaPlana, contrasenyaHashejatDeLaDB)) {
                        Agent agent = new Agent();
                        agent.setIdAgent(rs.getInt("idAgent"));
                        agent.setNom(rs.getString("nom"));
                        agent.setCognoms(rs.getString("cognoms"));
                        agent.setEmail(rs.getString("email"));
                        agent.setTelefon(rs.getString("telefon"));
                        agent.setTipus(Agent.TipusAgent.valueOf(rs.getString("tipus")));
                        return agent;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No trobat o contrasenya incorrecta
    }
    
    @Override
    public void updateAgentTipus(int idAgent, Agent.TipusAgent nouTipus) {

        String sql = "UPDATE Agents SET tipus = ? WHERE idAgent = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Convertim l'Enum de Java (ex: BIBLIOTECARI) 
            // al String que espera la BBDD (ex: "bibliotecari")
            ps.setString(1, nouTipus.name()); 
            ps.setInt(2, idAgent);

            ps.executeUpdate(); // Executem l'actualització

        } catch (SQLException e) {
            e.printStackTrace();
            // Aquí hauries de gestionar l'error
        }
    }

    @Override
    public List<Agent> getAllAgents() {
        List<Agent> agents = new ArrayList<>();
        String sql = "SELECT idAgent, nom, cognoms, telefon, email, tipus FROM Agents";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Agent agent = new Agent();
                agent.setIdAgent(rs.getInt("idAgent"));
                agent.setNom(rs.getString("nom"));
                agent.setCognoms(rs.getString("cognoms"));
                agent.setTelefon(rs.getString("telefon"));
                agent.setEmail(rs.getString("email"));
                agent.setTipus(Agent.TipusAgent.valueOf(rs.getString("tipus")));
                agents.add(agent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return agents;
    }

}
