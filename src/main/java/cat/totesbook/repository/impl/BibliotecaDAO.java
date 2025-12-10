package cat.totesbook.repository.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.repository.BibliotecaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe DAO que gestiona l'accés a la base de dades per a les entitats Biblioteca.
 * 
 * @author equip TotEsBook
 */
@Repository
/**
 * Implementació JPA del repositori de Biblioteca.
 */
public class BibliotecaDAO implements BibliotecaRepository {

    @PersistenceContext(unitName = "totesbookPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Mètode que retorna totes les biblioteques.
     * 
     * @return llista de totes les biblioteques.
     */
    @Override
    public List<Biblioteca> getAllBiblioteques() {
        return entityManager.createQuery("SELECT b FROM Biblioteca b", Biblioteca.class)
                .getResultList();
    }
    /**
     * Mètode que busca segons l'id de la biblioteca.
     * 
     * @param idBiblioteca id biblioteca.
     * @return optional que conté biblioteca amb id o null en cas de no existir.
     */
    @Override
    public Optional<Biblioteca> findById(int idBiblioteca) {
        Biblioteca biblioteca = entityManager.find(Biblioteca.class, idBiblioteca);
        return Optional.ofNullable(biblioteca);
    }

    /**
     * Mètode que busca la biblioteca pel nom.
     * 
     * @param nom nom de la biblioteca.
     * @return  retorna el nom de la biblioteca. 
     */
    @Override
    public Optional<Biblioteca> findByNom(String nom) {
        try {
            TypedQuery<Biblioteca> query = entityManager.createQuery(
                    "SELECT b FROM Biblioteca b WHERE b.nom = :nom", Biblioteca.class);
            query.setParameter("nom", nom);
            List<Biblioteca> resultats = query.getResultList();
            return resultats.isEmpty() ? Optional.empty() : Optional.of(resultats.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Ja no ens farà falta perque el metode saveOrUpdateBiblioteca guardarà i actualitzarà
    /**
     * Afegir una biblioteca.
     * 
     * @param biblioteca La Biblioteca.
     */
    @Override
    public void addBiblioteca(Biblioteca biblioteca) {
        entityManager.persist(biblioteca);
    }

    /**
     * Mètode que depenent de si és una nova biblioteca i ouna actualització fa un merge o un persist
     * @param biblioteca biblioteca. 
     */
    @Override
    public void saveOrUpdateBiblioteca(Biblioteca biblioteca) {
        if (biblioteca.getIdBiblioteca() == 0) {
            entityManager.persist(biblioteca); // nova biblioteca
            System.out.println(">>> [JPA] Nova biblioteca afegida: " + biblioteca.getNom());
        } else {
            entityManager.merge(biblioteca); // actualització de una biblioteca
            System.out.println(">>> [JPA] Biblioteca actualitzada: " + biblioteca.getNom());
        }
    }

    /**
     * Mètode que elimina una biblioteca.
     * 
     * @param idBiblioteca id de la biblioteca.
     */
    @Override
    public void deleteBiblioteca(int idBiblioteca) {
        try {
            Biblioteca b = entityManager.find(Biblioteca.class, idBiblioteca);
            if (b != null) {
                entityManager.remove(b);
                System.out.println("[DAO] Biblioteca eliminada: " + b.getNom());
            } else {
                System.out.println("[DAO] No s'ha trobat la biblioteca amb id=" + idBiblioteca);
            }
        } catch (Exception e) {
            System.err.println("[DAO] Error eliminant biblioteca id=" + idBiblioteca + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Mètode que compta els llibres que hi ha a cada biblioteca.
     * 
     * @param idBiblioteca id de la biblioteca.
     * @return el nombre de llibres que hi ha a una biblioteca si hi ha llibres.
     */
    @Override
    public int countLlibresByBiblioteca(int idBiblioteca) {
        Long resultat = entityManager.createQuery(
                "SELECT COUNT(bl) FROM BibliotecaLlibre bl WHERE bl.biblioteca.idBiblioteca = :id",
                Long.class)
                .setParameter("id", idBiblioteca)
                .getSingleResult();
        return resultat != null ? resultat.intValue() : 0;
    }

    /**
     * Mètode que compta el nombre de préstecs segons la biblioteca.
     * 
     * @param idBiblioteca id de la biblioteca.
     * @return el nombre de préstecs que hi ha a una biblioteca si han hagut 
     * préstecs.
     */
    @Override
    public int countPrestecsByBiblioteca(int idBiblioteca) {
        Long resultat = entityManager.createQuery(
                "SELECT COUNT(p) FROM Prestec p WHERE p.biblioteca.idBiblioteca = :id",
                Long.class)
                .setParameter("id", idBiblioteca)
                .getSingleResult();
        return resultat != null ? resultat.intValue() : 0;
    }

    /**
     * Mètode que retorna el bibliotecari que està assignat a la biblioteca.
     * @param idBiblioteca id de la biblioteca.
     * @return el bibliotecari assignat o null en cas de que no hi hagi cap 
     * bibliotecari assignat a aquella biblioteca.
     */
    @Override
    public Agent getBibliotecariByBiblioteca(int idBiblioteca) {
        List<Agent> result = entityManager.createQuery(
                "SELECT b.bibliotecari FROM Biblioteca b WHERE b.idBiblioteca = :id", Agent.class)
                .setParameter("id", idBiblioteca)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}