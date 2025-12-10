package cat.totesbook.repository.impl;

import cat.totesbook.domain.PropostaAdquisicio;
import cat.totesbook.repository.PropostaAdquisicioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Classe DAO que gestiona l'accés a la base de dades per a les entitats PropostaAdquisició.
 * 
 * @author Equip TotEsBook
 */
@Repository
/**
 * Implementació JPA del repositori de Proposta d'Adquisició.
 */
public class PropostaAdquisicioDAO implements PropostaAdquisicioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Guadar les dades de la proposta.
     * 
     * @param proposta La proposta.
     */
    @Override
    public void guardarProposta(PropostaAdquisicio proposta) {
        entityManager.persist(proposta);
    }

    /**
     * Cercar propostes segons l'usuari que l'hagi fet.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return Una llista amb les propostes fetes per un usuari.
     */
    @Override
    public List<PropostaAdquisicio> findByUsuari(int idUsuari) {
        String jpql = "SELECT p FROM PropostaAdquisicio p WHERE p.idUsuari = :idUsuari";
        return entityManager.createQuery(jpql, PropostaAdquisicio.class)
                .setParameter("idUsuari", idUsuari)
                .getResultList();
    }

    /**
     * Cerca totes les propostes fetes.
     * 
     * @return Una llista amb totes les propostes fetes.
     */
    @Override
    public List<PropostaAdquisicio> findAllPropostes() {
        return entityManager.createQuery(
                "SELECT p FROM PropostaAdquisicio p ORDER BY p.dataProposta DESC",
                PropostaAdquisicio.class
        ).getResultList();
    }

    /**
     * Cercar proposta segons el seu ID.
     * 
     * @param id L'ID de la proposta.
     * @return Objecte PropostaAdquisició segons el seu ID.
     */
    @Override
    public PropostaAdquisicio findByIdProposta(int id) {
        return entityManager.find(PropostaAdquisicio.class, id);
    }

    /**
     * Eliminar la proposta segons el seu ID.
     * 
     * @param id L'ID.
     */
    @Override
    public void eliminarProposta(int id) {
        PropostaAdquisicio p = entityManager.find(PropostaAdquisicio.class, id);
        if (p != null) {
            entityManager.remove(p);
        }
    }

    /**
     * Actualitzar la informació de la proposta d'adquisició.
     * 
     * @param p La proposta d'adquisició.
     */
    public void actualitzar(PropostaAdquisicio p) {
        entityManager.merge(p);
    }

}
