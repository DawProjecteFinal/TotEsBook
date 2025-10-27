/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.repository.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.config.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author edinsonioc
 */
public class AgentDAO implements AgentRepository {

    @Override
    public Agent findAgentByEmailAndPassword(String email, String passwordPla) {
        
        String sql = "SELECT * FROM Agents WHERE email = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String passwordHashejatDeLaDB = rs.getString("contrasenya");

                    if (BCrypt.checkpw(passwordPla, passwordHashejatDeLaDB)) {
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
}
