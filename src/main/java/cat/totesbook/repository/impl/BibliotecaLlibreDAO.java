package cat.totesbook.repository.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.BibliotecaLlibreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jmiro
 */
@Repository
public class BibliotecaLlibreDAO implements BibliotecaLlibreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    // Enlloc de fer servir aquest metode per retornar tots els llibres de una biblioteca utilitzem el metode findByBiblioteca de LlibreDAO
    @Override
    public List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca) {
        TypedQuery<BibliotecaLlibre> query = entityManager.createQuery(
                "SELECT bl FROM BibliotecaLlibre bl WHERE bl.biblioteca = :biblioteca",
                BibliotecaLlibre.class
        );
        query.setParameter("biblioteca", biblioteca);
        return query.getResultList();
    }

    @Override
    public Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre) {
        try {
            TypedQuery<BibliotecaLlibre> query = entityManager.createQuery(
                    "SELECT bl FROM BibliotecaLlibre bl "
                    + "WHERE bl.biblioteca.idBiblioteca = :idBiblioteca "
                    + "AND bl.llibre.isbn = :isbn",
                    BibliotecaLlibre.class
            );

            query.setParameter("idBiblioteca", biblioteca.getIdBiblioteca());
            query.setParameter("isbn", llibre.getIsbn());

            List<BibliotecaLlibre> resultats = query.getResultList();
            return resultats.isEmpty() ? Optional.empty() : Optional.of(resultats.get(0));

        } catch (Exception e) {
            System.err.println("Error a BibliotecaLlibreDAO.findByBibliotecaAndLlibre: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void addBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre) {
        entityManager.persist(bibliotecaLlibre);
    }

    @Override
    public void updateBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre) {
        entityManager.merge(bibliotecaLlibre);
    }
}
