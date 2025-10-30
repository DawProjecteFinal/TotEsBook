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
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Mètode que obté tots els llibres de la BD.
     * 
     * @return llista de llibres.
     */
    @Override
    public List<Llibre> getAllLlibres() {
        TypedQuery<Llibre> query = entityManager.createQuery("SELECT l FROM Llibre l ORDER BY l.titol", Llibre.class);
        return query.getResultList();
    }

    @Override
    public void addLlibre(Llibre llibre) {
         // merge fa un INSERT si l'entitat no existeix (per ISBN), o un UPDATE si ja existeix.
         // Això requereix que Llibre sigui una @Entity gestionada.
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
        // find busca per clau primària (@Id)
        Llibre llibre = entityManager.find(Llibre.class, isbn);
        // Retorna un Optional buit si no el troba, o un Optional amb el llibre si el troba
        return Optional.ofNullable(llibre); 
    }
}