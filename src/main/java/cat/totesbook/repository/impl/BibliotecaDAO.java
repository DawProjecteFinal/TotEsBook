

package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmiro
 */

@Repository
@Transactional
public class BibliotecaDAO implements BibliotecaRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Biblioteca> getAllBiblioteques() {
        return entityManager.createQuery("SELECT b FROM Biblioteca b", Biblioteca.class)
                .getResultList();
    }

    @Override
    public Optional<Biblioteca> findById(int idBiblioteca) {
        Biblioteca biblioteca = entityManager.find(Biblioteca.class, idBiblioteca);
        return Optional.ofNullable(biblioteca);
    }

    @Override
    public Optional<Biblioteca> findByNom(String nom) {
        try {
            TypedQuery<Biblioteca> query = entityManager.createQuery(
                    "SELECT b FROM Biblioteca b WHERE b.nom = :nom", Biblioteca.class);
            query.setParameter("nom", nom);
            List<Biblioteca> resultats = query.getResultList();
            return resultats.isEmpty() ? Optional.empty() : Optional.of(resultats.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void addBiblioteca(Biblioteca biblioteca) {
        entityManager.persist(biblioteca);
    }
}
