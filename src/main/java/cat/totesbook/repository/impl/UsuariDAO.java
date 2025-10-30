package cat.totesbook.repository.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository; // <-- Implementem la interfície
import cat.totesbook.repository.*;
import cat.totesbook.config.DBConnection; 
import org.mindrot.jbcrypt.BCrypt; // <-- Import per a BCrypt

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Implementació del DAO per a la taula Usuaris.
 *
 * Aquesta implementació utilitza accés JDBC directe via DBConnection.
 */

@Repository
public class UsuariDAO implements UsuariRepository {

    @Override
    public Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana) {
        String sql = "SELECT * FROM Usuaris WHERE email = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String passwordHashejatDeLaDB = rs.getString("contrasenya");

                    if (BCrypt.checkpw(contrasenyaPlana, passwordHashejatDeLaDB)) {
                        Usuari usuari = new Usuari();
                        usuari.setId(rs.getInt("id"));
                        usuari.setNom(rs.getString("nom"));
                        usuari.setCognoms(rs.getString("cognoms"));
                        usuari.setEmail(rs.getString("email"));
                        usuari.setTelefon(rs.getString("telefon"));
                        usuari.setLlibresFavorits(rs.getString("llibresFavorits"));
                        usuari.setContrasenya(rs.getString("contrasenya"));
                        return usuari;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // No trobat o contrasenya incorrecta
    }

    /**
     * Mètode que desa un usuari a la BD via JDBC.
     *
     * @param usuari l'usuari a desar
     */
    @Override
    public void saveUsuari(Usuari usuari) {
        String sql = "INSERT INTO Usuaris (nom, cognoms, email, contrasenya, telefon) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getCognoms());
            ps.setString(3, usuari.getEmail());
            ps.setString(4, usuari.getContrasenya()); // Guardem el hash
            ps.setString(5, usuari.getTelefon());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Usuari getUsuariByEmail(String email) {
        String sql = "SELECT * FROM Usuaris WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Retornem un usuari (sense contrasenya)
                    Usuari usuari = new Usuari();
                    usuari.setId(rs.getInt("id"));
                    usuari.setNom(rs.getString("nom"));
                    usuari.setCognoms(rs.getString("cognoms"));
                    usuari.setEmail(rs.getString("email"));
                    return usuari;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override // <-- Implementació del mètode que faltava (Error 5)
    public List<Usuari> getAllUsuaris() {
        List<Usuari> usuaris = new ArrayList<>();
        String sql = "SELECT id, nom, cognoms, telefon, email, llibresFavorits FROM Usuaris";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Usuari usuari = new Usuari();
                usuari.setId(rs.getInt("id"));
                usuari.setNom(rs.getString("nom"));
                usuari.setCognoms(rs.getString("cognoms"));
                usuari.setEmail(rs.getString("email"));
                usuari.setTelefon(rs.getString("telefon"));
                usuari.setLlibresFavorits(rs.getString("llibresFavorits"));
                usuaris.add(usuari);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
    }
}
