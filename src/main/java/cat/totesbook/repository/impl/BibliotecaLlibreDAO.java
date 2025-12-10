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
 * Classe DAO que gestiona l'accés a la base de dades per a les entitats BibliotecaLlibre.
 * 
 * @author equip TotEsBook
 */
@Repository
/**
 * Implementació JPA del repositori de BibliotecaLlibre.
 */
public class BibliotecaLlibreDAO implements BibliotecaLlibreRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    // Enlloc de fer servir aquest metode per retornar tots els llibres de una biblioteca utilitzem el metode findByBiblioteca de LlibreDAO
    
    /**
     * Retornar llibres de cada biblioteca.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llista de libres de cada biblioteca.
     */
    @Override
    public List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca) {
        TypedQuery<BibliotecaLlibre> query = entityManager.createQuery(
                "SELECT bl FROM BibliotecaLlibre bl WHERE bl.biblioteca = :biblioteca",
                BibliotecaLlibre.class
        );
        query.setParameter("biblioteca", biblioteca);
        return query.getResultList();
    }

    /**
     * Cerca el llibre i la biblioteca especificats.
     * 
     * @param biblioteca La biblioteca.
     * @param llibre El llibre.
     * @return Un opcional amb una BibliotecaLlibre trobada.
     */
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

    /**
     * Afegir un llibre a una biblioteca.
     * 
     * @param bibliotecaLlibre El llbire d'una biblioteca.
     */
    @Override
    public void addBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre) {
        entityManager.persist(bibliotecaLlibre);
    }

    /**
     * 
     * Actualitza la informació d'un llibre d'una biblitoca.
     * 
     * @param bibliotecaLlibre El llibre d'una biblioteca.
     */
    @Override
    public void updateBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre) {
        entityManager.merge(bibliotecaLlibre);
    }


    //Busquem per llibre per retornar la biblioteca
    /**
     * Cerca un llibre espeficicat.
     * 
     * @param llibre El llibre.
     * @return Una llista BibliotecaLlibre amb el llibre especificat.
     */
    @Override
    public List<BibliotecaLlibre> findByLlibre(Llibre llibre) {
        TypedQuery<BibliotecaLlibre> query = entityManager.createQuery(
                "SELECT bl FROM BibliotecaLlibre bl WHERE bl.llibre = :llibre",
                BibliotecaLlibre.class
        );
        query.setParameter("llibre", llibre);
        return query.getResultList();
    }

    /**
     * Retorna el primer llibre que troba a una biblioteca
     *
     * @param isbn L'ISBN.
     * @return Un optional amb el primer llibre que troba amb l'ISBN indicat.
     */
    @Override
    public Optional<BibliotecaLlibre> findFirstByLlibreIsbn(String isbn) {

        List<BibliotecaLlibre> resultats = entityManager.createQuery(
                "SELECT bl FROM BibliotecaLlibre bl WHERE bl.llibre.isbn = :isbn",
                BibliotecaLlibre.class
        )
                .setParameter("isbn", isbn)
                .setMaxResults(1)
                .getResultList();

        return resultats.isEmpty() ? Optional.empty() : Optional.of(resultats.get(0));
    }

    /**
     * Versió que retorna tots els registres per si el mateix llibre està a
     * diverses biblioteques
     *
     * @param isbn L'ISBN.
     * @return Una llista BibliotecaLlibre que conté el llibre.
     */
    @Override
    public List<BibliotecaLlibre> findByLlibreIsbn(String isbn) {

        return entityManager.createQuery(
                "SELECT bl FROM BibliotecaLlibre bl WHERE bl.llibre.isbn = :isbn",
                BibliotecaLlibre.class
        )
                .setParameter("isbn", isbn)
                .getResultList();
    }


}
