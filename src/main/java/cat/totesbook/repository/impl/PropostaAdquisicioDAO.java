package cat.totesbook.repository.impl;

import cat.totesbook.domain.PropostaAdquisicio;
import cat.totesbook.repository.PropostaAdquisicioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author equip TotEsBook
 */
@Repository
public class PropostaAdquisicioDAO implements PropostaAdquisicioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void guardarProposta(PropostaAdquisicio proposta) {
        entityManager.persist(proposta);
    }

    @Override
    public List<PropostaAdquisicio> findByUsuari(int idUsuari) {
        String jpql = "SELECT p FROM PropostaAdquisicio p WHERE p.idUsuari = :idUsuari";
        return entityManager.createQuery(jpql, PropostaAdquisicio.class)
                .setParameter("idUsuari", idUsuari)
                .getResultList();
    }

    @Override
    public List<PropostaAdquisicio> findAllPropostes() {
        return entityManager.createQuery(
                "SELECT p FROM PropostaAdquisicio p ORDER BY p.dataProposta DESC",
                PropostaAdquisicio.class
        ).getResultList();
    }

    @Override
    public PropostaAdquisicio findByIdProposta(int id) {
        return entityManager.find(PropostaAdquisicio.class, id);
    }

    @Override
    public void eliminarProposta(int id) {
        PropostaAdquisicio p = entityManager.find(PropostaAdquisicio.class, id);
        if (p != null) {
            entityManager.remove(p);
        }
    }

}
