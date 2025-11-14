package cat.totesbook.repository.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import cat.totesbook.repository.PrestecRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmiro
 */
@Repository
//@Transactional
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

}
