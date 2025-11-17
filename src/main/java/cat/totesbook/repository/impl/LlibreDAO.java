package cat.totesbook.repository.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.LlibreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class LlibreDAO implements LlibreRepository {

    @PersistenceContext(unitName = "totesbookPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Obté tots els llibres de la base de dades.
     */
    @Override
    public List<Llibre> getAllLlibres() {
        return entityManager
                .createQuery("SELECT l FROM Llibre l", Llibre.class)
                .getResultList();
    }

    /**
     * Afegeix o actualitza (UPSERT) un llibre segons si ja existeix l'ISBN.
     */
    @Override
    public void addLlibre(Llibre llibre) {
        Llibre existent = entityManager.find(Llibre.class, llibre.getIsbn());

        if (existent == null) {
            entityManager.persist(llibre);
            System.out.println(">>> [JPA] Llibre nou afegit: " + llibre.getTitol());
        } else {
            actualitzarLlibreDesDe(existent, llibre);
            entityManager.merge(existent);
            System.out.println(">>> [JPA] Llibre actualitzat: " + llibre.getTitol());
        }
    }

    /**
     * Busca un llibre pel seu ISBN.
     */
    @Override
    public Optional<Llibre> getLlibreByIsbn(String isbn) {
        Llibre llibre = entityManager.find(Llibre.class, isbn);
        return Optional.ofNullable(llibre);
    }

    // Retorna els llibre de una biblioteca en concret
    @Override
    public List<Llibre> findByBiblioteca(Biblioteca biblioteca) {
        return entityManager.createQuery(
                "SELECT bl.llibre FROM BibliotecaLlibre bl WHERE bl.biblioteca = :biblioteca", Llibre.class)
                .setParameter("biblioteca", biblioteca)
                .getResultList();
    }

    /**
     * Copia totes les propietats d'un llibre origen a un llibre existent.
     */
    private void actualitzarLlibreDesDe(Llibre desti, Llibre origen) {
        desti.setTitol(origen.getTitol());
        desti.setAutor(origen.getAutor());
        desti.setEditorial(origen.getEditorial());
        desti.setCategoria(origen.getCategoria());
        desti.setSinopsis(origen.getSinopsis());
        desti.setImatgeUrl(origen.getImatgeUrl());
        desti.setIdioma(origen.getIdioma());
        desti.setExemplars(origen.getExemplars());
        desti.setDisponibles(origen.getDisponibles());
    }

    /**
     * Mostra llibres de forma aleatoria
     *
     * @param limit
     * @return
     */
    @Override
    public List<Llibre> findRandom(int limit) {
        String sql = "SELECT * FROM Llibres ORDER BY RAND() LIMIT " + limit;

        return entityManager
                .createNativeQuery(sql, Llibre.class)
                .getResultList();
    }

}
