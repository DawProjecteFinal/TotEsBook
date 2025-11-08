package cat.totesbook.repository.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
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
     * Actualitza les dades d'un usuari existent (sense modificar contrasenya si
     * no es passa nova).
     */
    @Override
    public void updateUsuari(Usuari actualitzat) {
        Usuari existent = entityManager.find(Usuari.class, actualitzat.getId());
        if (existent != null) {
            actualitzarUsuariDesDe(existent, actualitzat);
            entityManager.merge(existent);
            System.out.println(">>> [JPA] Usuari actualitzat: " + existent.getEmail());
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
}
