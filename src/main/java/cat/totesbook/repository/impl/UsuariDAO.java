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

/**
 * Classe DAO que gestiona l'accés a la base de dades per a les entitats Usuari.
 * 
 * @author Equip TotEsBook
 */

@Repository

/**
 * Implementació JPA del repositori d'Usuaris.
 */
public class UsuariDAO implements UsuariRepository {

    @PersistenceContext(unitName = "totesbookPersistenceUnit")
    private EntityManager entityManager;
    
    /**
     * Retorna un usuari pel seu email i contrasenya (verificant hash BCrypt).
     * 
     * @param email L'email del formulari.
     * @param contrasenyaPlana La contrasenya en TEXT PLA del formulari.
     * @return L'objecte Usuari si és correcte, o null si no ho és.
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
     * 
     * @param usuari L'objecte Usuari (amb la contrasenya ja hashejada).
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
     * 
     * @param email L'email a cercar.
     * @return L'objecte Usuari si es troba, o null.
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
     * 
     * @return Llista d'objectes Usuari.
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
     * 
     * @param desti El destí.
     * @param origen L'origen.
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

    /**
     * Busca un usuari complet per la seva ID.
     * @param id L'ID de l'usuari.
     * @return L'objecte Usuari, o null si no es troba.
     */
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

    /**
     * Actualitza les dades bàsiques del perfil d'un usuari (sense contrasenya).
     * 
     * @param usuari L'objecte usuari amb les dades actualitzades (necessita ID, nom, cognoms, email, telefon).
     */
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
     * 
     * @param rs resultat.
     * @return Un usuari.
     * @throws SQLException Exepció d'SQL.
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

    /**
     * Actualiza les dades bàsiques de la sanció de l'usuari.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @param dataFiSancio La data de la finalització.
     * @param motiuSancio El motiu de la sanció.
     */
    @Override
    public void updateSancioUsuari(int idUsuari, LocalDateTime dataFiSancio, String motiuSancio) {
        try {
            Usuari u = entityManager.find(Usuari.class, idUsuari);
            if (u == null) {
                System.err.println("Usuari no trobat amb id = " + idUsuari);
                return;
            }

            u.setDataFiSancio(dataFiSancio);
            u.setMotiuSancio(motiuSancio);

            // Opcional si vols assegurar que es fa l’UPDATE immediatament
            // entityManager.flush();
        } catch (Exception e) {
            System.err.println("Error a UsuariDAO.updateSancioUsuari: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retorna una llista d'usuaris amb la sanció activa.
     * 
     * @return Una llista d'usuaris amb la sanció activa.
     */
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
}
