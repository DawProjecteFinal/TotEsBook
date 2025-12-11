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

/**
 * Classe DAO que gestiona l'accés a la base de dades per a les entitats Llibre.
 * 
 * @author Equip TotEsBook
 */
@Repository
/**
 * Implementació JPA del repositori de Llibre.
 */
public class LlibreDAO implements LlibreRepository {

    @PersistenceContext(unitName = "totesbookPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Obté tots els llibres de la base de dades.
     * 
     * @return llista de llibres.
     */
    
    @Override
    public List<Llibre> getAllLlibres() {
        return entityManager
                .createQuery("SELECT l FROM Llibre l", Llibre.class)
                .getResultList();
    }

    /**
     * Afegeix o actualitza (UPSERT) un llibre segons si ja existeix l'ISBN.
     * 
     * @param llibre El llibre.
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
     * 
     * @param isbn L'ISBN.
     * @return Un optional amb el llibre amb l'ISBN.
     */
    @Override
    public Optional<Llibre> getLlibreByIsbn(String isbn) {
        Llibre llibre = entityManager.find(Llibre.class, isbn);
        return Optional.ofNullable(llibre);
    }

    /**
     * Retorna els llibre de una biblioteca en concret
     * 
     * @param biblioteca La biblioteca.
     * @return Llista amb totes els llibres de la biblioteca especificada.
     */
    @Override
    public List<Llibre> findByBiblioteca(Biblioteca biblioteca) {
        return entityManager.createQuery(
                "SELECT bl.llibre FROM BibliotecaLlibre bl WHERE bl.biblioteca = :biblioteca", Llibre.class)
                .setParameter("biblioteca", biblioteca)
                .getResultList();
    }

    /**
     * Copia totes les propietats d'un llibre origen a un llibre existent.
     * 
     * @param desti El destí.
     * @param origen L'origen.
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
     * Retorna els llibres per una categoria concreta
     * 
     * @param categoria La categoria.
     * @return Llista amb totes els llibres de la categoria indicada.
     */
    @Override
    public List<Llibre> findByCategoria(String categoria) {
        return entityManager.createQuery(
                "SELECT l FROM Llibre l WHERE l.categoria = :categoria", Llibre.class).setParameter("categoria", categoria).getResultList();
    }

    /**
     * Retorna una llista de llibres amb el títol indicat sense tenir en compte
     * les majúscules ni les minúscules.
     * 
     * @param titol El títol.
     * @return Retorna una llista de llibres amb el títol indicat.
     */
    @Override
    public List<Llibre> findByTitolContainingIgnoreCase(String titol) {
        return entityManager.createQuery(
                "SELECT l FROM Llibre l " + "WHERE LOWER(l.titol) LIKE LOWER(CONCAT('%', :titol, '%'))", Llibre.class).setParameter("titol", titol).getResultList();
    }

    /**
     * Retorna una llista de llibres amb l'autor indicat sense tenir en compte
     * les majúscules ni les minúscules.
     * 
     * @param autor L'autor.
     * @return Retorna una llista de llibres amb l'autor indicat.
     */
    @Override
    public List<Llibre> findByAutorContainingIgnoreCase(String autor) {
        return entityManager.createQuery(
                "SELECT l FROM Llibre l " + "WHERE LOWER(l.autor) LIKE LOWER(CONCAT('%', :autor, '%'))", Llibre.class).setParameter("autor", autor).getResultList();
    }

    /**
     * Retorna una llista de llibres de l'idioma indicat.
     * 
     * @param idioma L'idioma.
     * @return Retorna una llista de llibres amb l'idioma indicat.
     */
    @Override
    public List<Llibre> findByIdioma(String idioma) {
        return entityManager.createQuery(
                "SELECT l FROM Llibre l " + "WHERE LOWER(l.idioma) = LOWER(:idioma)", Llibre.class).setParameter("idioma", idioma).getResultList();
    }

    /**
     * Mostra llibres de forma aleatoria
     *
     * @param limit El límit.
     * @return Una llista de llibres aleatoris.
     */
    @Override
    public List<Llibre> findRandom(int limit) {
        String sql = "SELECT * FROM Llibres ORDER BY RAND() LIMIT " + limit;

        return entityManager
                .createNativeQuery(sql, Llibre.class)
                .getResultList();

    }

    /**
     * Elimina el llibre segons l'ISBN indicat.
     * 
     * @param isbn L'ISBN.
     */
    @Override
    @Transactional
    public void deleteLlibreByIsbn(String isbn) {
        entityManager.createQuery(
                "DELETE FROM BibliotecaLlibre b WHERE b.llibre.isbn = :isbn")
                .setParameter("isbn", isbn)
                .executeUpdate();

        Llibre llibre = entityManager.find(Llibre.class, isbn);
        if (llibre != null) {
            entityManager.remove(llibre);
        }
    }

    // Setter només per a tests
    void setEntityManager(EntityManager em) {
        this.entityManager = em;
    }

}
