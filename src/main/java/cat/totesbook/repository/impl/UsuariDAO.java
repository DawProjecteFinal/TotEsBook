/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.repository.impl;

import cat.totesbook.config.DBConnection;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.PersistenceContext; // Manté aquest import
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UsuariDAO implements UsuariRepository {

    @PersistenceContext(unitName = "totesbookPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Retorna un usuari pel seu email i contrasenya (verificant hash BCrypt).
     */
    @Override
    public Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana) {
        try {
            TypedQuery<Usuari> query = entityManager.createQuery(
                    "SELECT u FROM Usuari u WHERE u.email = :email", Usuari.class);
            query.setParameter("email", email);

            Usuari usuari = query.getResultStream().findFirst().orElse(null);

            if (usuari != null && BCrypt.checkpw(contrasenyaPlana, usuari.getContrasenya())) {
                return usuari;
            }

        } catch (Exception e) {
            System.err.println("Error a UsuariDAO.getUsuariByEmailAndContrasenya: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Desa un nou usuari (amb contrasenya ja hashejada).
     */
    @Override
    public void saveUsuari(Usuari usuari) {
        try {
            // Comprovem si ja existeix per email (clau única)
            List<Usuari> existents = entityManager.createQuery(
                    "SELECT u FROM Usuari u WHERE u.email = :email", Usuari.class)
                    .setParameter("email", usuari.getEmail())
                    .getResultList();

            if (existents.isEmpty()) {
                entityManager.persist(usuari);
                System.out.println(">>> [JPA] Usuari nou registrat: " + usuari.getEmail());
            } else {
                System.out.println(">>> [JPA] Usuari ja existent: " + usuari.getEmail());
            }
        } catch (Exception e) {
            System.err.println("Error a UsuariDAO.saveUsuari: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Busca un usuari pel seu email.
     */
    @Override
    public Usuari getUsuariByEmail(String email) {
        try {
            List<Usuari> result = entityManager.createQuery(
                    "SELECT u FROM Usuari u WHERE u.email = :email", Usuari.class)
                    .setParameter("email", email)
                    .getResultList();
            return result.isEmpty() ? null : result.get(0);
        } catch (Exception e) {
            System.err.println("Error a UsuariDAO.getUsuariByEmail: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retorna tots els usuaris (sense carregar contrasenyes).
     */
    @Override
    public List<Usuari> getAllUsuaris() {
        try {
            return entityManager.createQuery(
                    "SELECT u FROM Usuari u", Usuari.class)
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error a UsuariDAO.getAllUsuaris: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }


    /**
     * Copia camps d'un usuari origen cap a un usuari existent.
     */
    private void actualitzarUsuariDesDe(Usuari desti, Usuari origen) {
        desti.setNom(origen.getNom());
        desti.setCognoms(origen.getCognoms());
        desti.setEmail(origen.getEmail());
        desti.setTelefon(origen.getTelefon());
        desti.setLlibresFavorits(origen.getLlibresFavorits());

        // Només si s'ha passat una nova contrasenya, la canviem
        if (origen.getContrasenya() != null && !origen.getContrasenya().isBlank()) {
            desti.setContrasenya(BCrypt.hashpw(origen.getContrasenya(), BCrypt.gensalt()));
        }
    }
    
    // ===================================
    // --- INICI NOU CODI AFEGIT ---
    // ===================================
    
    @Override
    public Usuari findUsuariById(int id) {
        String sql = "SELECT * FROM Usuaris WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Fem servir el mètode d'ajuda per mapejar les dades
                    return mapRowToUsuari(rs);
                }
            }
        } catch (Exception ex) {
            System.getLogger(UsuariDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return null; // No trobat
    }

    @Override
    public void updatePerfil(Usuari usuari) {
        // Aquest UPDATE NO actualitza la contrasenya, només les dades del perfil
        // CORRECCIÓ: Afegim llibresFavorits a l'UPDATE
        String sql = "UPDATE Usuaris SET nom = ?, cognoms = ?, email = ?, telefon = ?, llibresFavorits = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getCognoms());
            ps.setString(3, usuari.getEmail());
            ps.setString(4, usuari.getTelefon());
            ps.setString(5, usuari.getLlibresFavorits()); // <-- CORRECCIÓ: S'ha afegit aquest paràmetre
            ps.setInt(6, usuari.getId());                 // <-- CORRECCIÓ: Ara és el paràmetre 6
            
            ps.executeUpdate();
            
        } catch (Exception ex) {
            System.getLogger(UsuariDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    /**
     * Mètode d'ajuda privat per convertir un ResultSet a un objecte Usuari
     * (Evita repetir codi)
     */
    private Usuari mapRowToUsuari(ResultSet rs) throws SQLException {
        Usuari usuari = new Usuari();
        usuari.setId(rs.getInt("id"));
        usuari.setNom(rs.getString("nom"));
        usuari.setCognoms(rs.getString("cognoms"));
        usuari.setEmail(rs.getString("email"));
        usuari.setTelefon(rs.getString("telefon"));
        usuari.setLlibresFavorits(rs.getString("llibresFavorits"));
        usuari.setContrasenya(rs.getString("contrasenya")); // Obtenim el hash (encara que no el fem servir a la vista)
        return usuari;
    }
    
    // ===================================
    // --- FI NOU CODI AFEGIT ---
    // ===================================
}
