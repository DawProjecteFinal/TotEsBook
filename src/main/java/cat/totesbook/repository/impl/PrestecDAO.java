
package cat.totesbook.repository.impl;

import cat.totesbook.domain.Prestec;
import cat.totesbook.repository.PrestecRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmiro
 */
@Repository
@Transactional
public class PrestecDAO implements PrestecRepository{

    @PersistenceContext
    private EntityManager entityManager;

    public void guardarPrestec(Prestec prestec) {
        entityManager.persist(prestec);
    }
}
