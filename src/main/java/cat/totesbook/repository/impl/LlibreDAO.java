package cat.totesbook.repository.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.LlibreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery; // Import necessari
import java.util.Optional;

/**
 * Mètode que obté tots els llibres de la BD mitjançant EntityManager.
 * 
 * @author equip TotEsBook
 */
@Repository
public class LlibreDAO implements LlibreRepository {
    
    /**
     * Creem EntityManager gestionat Spring
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Mètode que obté tots els llibres de la BD.
     * 
     * @return llista de llibres.
     */
    @Override
    public List<Llibre> getAllLlibres() {
        return entityManager.createQuery("SELECT l FROM Llibre l", Llibre.class).getResultList();
    }

    /**
     * Mètode que afegeix un llibre a la base de dades
     * 
     * @param llibre 
     */
    @Override
    public void addLlibre(Llibre llibre) {
        entityManager.merge(llibre);
    }

    /**
     * Mètode que retorna un llibre segons el seu ISBN.
     * 
     * @param isbn
     * @return llibre amb ISBN passat per paràmetre.
     */
    @Override
    public Optional<Llibre> getLlibreByIsbn(String isbn) {
        try {
            Llibre llibre = entityManager.find(Llibre.class, isbn);
            return Optional.ofNullable(llibre);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}