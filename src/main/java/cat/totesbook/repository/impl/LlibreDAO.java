package cat.totesbook.repository.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.LlibreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery; // Import necessari
import java.util.Optional;

@Repository
public class LlibreDAO implements LlibreRepository {

    // Injectem EntityManager gestionat per Spring/GlassFish
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Llibre> getAllLlibres() {
        // Usem JPQL (Java Persistence Query Language)
        TypedQuery<Llibre> query = entityManager.createQuery("SELECT * FROM Llibre l ORDER BY l.titol", Llibre.class);
        return query.getResultList();
    }

    // Afegeix un llibre nou o actualitza un existent
    @Override
    public void addLlibre(Llibre llibre) {
         // merge fa un INSERT si l'entitat no existeix (per ISBN), o un UPDATE si ja existeix.
         // Això requereix que Llibre sigui una @Entity gestionada.
        entityManager.merge(llibre); 
    }

    @Override
    public Optional<Llibre> getLlibreByIsbn(String isbn) {
        // find busca per clau primària (@Id)
        Llibre llibre = entityManager.find(Llibre.class, isbn);
        // Retorna un Optional buit si no el troba, o un Optional amb el llibre si el troba
        return Optional.ofNullable(llibre); 
    }
}