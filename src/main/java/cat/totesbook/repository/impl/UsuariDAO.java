package cat.totesbook.repository.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * Mètode que obté tots els usuaris de la BD mitjançant EntityManager.
 * 
 * @author equip TotEsBook
 */
@Repository
public class UsuariDAO implements UsuariRepository{
    
    /**
     * Creem EntityManager gestionat Spring
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Mètode que obté tots els usuaris de la BD.
     * 
     * @return llista d'usuaris.
     */
    @Override
    public List<Usuari> getAllUsuaris() {
        return entityManager.createQuery("SELECT u FROM Usuari u", Usuari.class).getResultList();
    }
    
}
