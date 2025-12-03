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
import jakarta.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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

    @Override
    public Usuari findUsuariById(int id) {
        String sql = "SELECT * FROM Usuaris WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
        String sql = "UPDATE Usuaris SET nom = ?, cognoms = ?, email = ?, telefon = ?, llibresFavorits = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuari.getNom());
            ps.setString(2, usuari.getCognoms());
            ps.setString(3, usuari.getEmail());
            ps.setString(4, usuari.getTelefon());
            ps.setString(5, usuari.getLlibresFavorits());
            ps.setInt(6, usuari.getId());

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

        java.sql.Timestamp ts = rs.getTimestamp("dataFiSancio");
        if (ts != null) {
            usuari.setDataFiSancio(ts.toLocalDateTime());
        }
        usuari.setMotiuSancio(rs.getString("motiuSancio"));

        return usuari;
    }

    @Override
    public void updateSancioUsuari(int idUsuari, LocalDateTime dataFiSancio, String motiuSancio) {
        String sql = "UPDATE Usuaris SET dataFiSancio = ?, motiuSancio = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (dataFiSancio != null) {
                ps.setTimestamp(1, java.sql.Timestamp.valueOf(dataFiSancio));
            } else {
                ps.setNull(1, java.sql.Types.TIMESTAMP);
            }

            ps.setString(2, motiuSancio);
            ps.setInt(3, idUsuari);

            ps.executeUpdate();

        } catch (Exception ex) {
            System.getLogger(UsuariDAO.class.getName())
                    .log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    @Override
    public List<Usuari> getUsuarisAmbSancioActiva() {
        try {
            return entityManager.createQuery(
                    "SELECT u FROM Usuari u "
                    + "WHERE u.dataFiSancio IS NOT NULL "
                    + "AND u.dataFiSancio > :ara", Usuari.class)
                    .setParameter("ara", java.time.LocalDateTime.now())
                    .getResultList();
        } catch (Exception e) {
            System.err.println("Error a UsuariDAO.getUsuarisAmbSancioActiva: " + e.getMessage());
            e.printStackTrace();
            return List.of();
        }
    }

    @Override
    public List<Usuari> findUsuarisAmbSancioActiva() {
        LocalDateTime ara = LocalDateTime.now();

        return entityManager.createQuery(
                "SELECT u FROM Usuari u "
                + "WHERE u.dataFiSancio IS NOT NULL AND u.dataFiSancio > :ara",
                Usuari.class)
                .setParameter("ara", ara)
                .getResultList();
    }

}
