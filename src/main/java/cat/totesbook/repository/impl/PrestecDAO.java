package cat.totesbook.repository.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Prestec.EstatPrestec;
import cat.totesbook.repository.PrestecRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmiro
 */
@Repository
public class PrestecDAO implements PrestecRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void registrarPrestec(Prestec prestec) {
        System.out.println("***** DEBUG → Persistint préstec: " + prestec);
        entityManager.persist(prestec);
        System.out.println("***** DEBUG → Préstec persistit correctament.");

    }

    @Override
    public List<Prestec> findActiusByBiblioteca(Biblioteca biblioteca) {
        String jpql = "SELECT p FROM Prestec p WHERE p.biblioteca = :b AND p.dataDevolucio IS NULL";
        return entityManager.createQuery(jpql, Prestec.class)
                .setParameter("b", biblioteca)
                .getResultList();
    }

    /**
     * Metode per a comprovar si un usuari te en prestec un llibre en concret
     *
     * @param isbn
     * @param idUsuari
     * @return
     */
    @Override
    public Optional<Prestec> findPrestecActiu(String isbn, Integer idUsuari) {
        try {
            TypedQuery<Prestec> query = entityManager.createQuery(
                    "SELECT p FROM Prestec p "
                    + "WHERE p.llibre.isbn = :isbn "
                    + "AND p.usuari.id = :idUsuari "
                    + "AND p.estat = :estat",
                    Prestec.class
            );

            query.setParameter("isbn", isbn);
            query.setParameter("idUsuari", idUsuari);
            query.setParameter("estat", EstatPrestec.actiu);

            List<Prestec> resultats = query.getResultList();

            return resultats.isEmpty() ? Optional.empty() : Optional.of(resultats.get(0));

        } catch (Exception e) {
            System.err.println("Error a PrestecDAO.findPrestecActiu: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public void updatePrestec(Prestec prestec) {
        entityManager.merge(prestec);
    }

    /**
     * Mètode per a poder representar les devolucions que ha fet un bibliotecari
     *
     * @param biblioteca
     * @return
     */
    @Override
    public List<Prestec> findDevolucionsByBiblioteca(Biblioteca biblioteca) {
        try {
            return entityManager.createQuery(
                    "SELECT p FROM Prestec p "
                    + "WHERE p.biblioteca.idBiblioteca = :idBiblioteca "
                    + "AND p.estat = :estat "
                    + "ORDER BY p.dataDevolucio DESC",
                    Prestec.class)
                    .setParameter("idBiblioteca", biblioteca.getIdBiblioteca())
                    .setParameter("estat", EstatPrestec.retornat)
                    .getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    /**
     * Metode que retorna tots els prestecs actius d'un ususari per a
     * mostrar-los a la pàgina inicial del usuari
    *
     */
    @Override
    public List<Prestec> findPrestecsActiusByUsuari(int idUsuari) {

        return entityManager.createQuery(
                "SELECT p FROM Prestec p "
                + "WHERE p.usuari.id = :idUsuari "
                + "AND p.estat = :estat "
                + "ORDER BY p.dataPrestec DESC",
                Prestec.class
        )
                .setParameter("idUsuari", idUsuari)
                .setParameter("estat", EstatPrestec.actiu)
                .getResultList();
    }

}
