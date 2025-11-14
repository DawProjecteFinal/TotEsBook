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
 *
 * @author jmiro
 */
@Repository
public class BibliotecaDAO implements BibliotecaRepository {

    @PersistenceContext(unitName = "totesbookPersistenceUnit")
    private EntityManager entityManager;

    @Override
    public List<Biblioteca> getAllBiblioteques() {
        return entityManager.createQuery("SELECT b FROM Biblioteca b", Biblioteca.class)
                .getResultList();
    }

    @Override
    public Optional<Biblioteca> findById(int idBiblioteca) {
        Biblioteca biblioteca = entityManager.find(Biblioteca.class, idBiblioteca);
        return Optional.ofNullable(biblioteca);
    }

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
    @Override
    public void addBiblioteca(Biblioteca biblioteca) {
        entityManager.persist(biblioteca);
    }

    /**
     * Depenent de si és una noa biblioteca i ouna actualització fa un merge o un persist
     * @param biblioteca 
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

    @Override
    public int countLlibresByBiblioteca(int idBiblioteca) {
        Long resultat = entityManager.createQuery(
                "SELECT COUNT(bl) FROM BibliotecaLlibre bl WHERE bl.biblioteca.idBiblioteca = :id",
                Long.class)
                .setParameter("id", idBiblioteca)
                .getSingleResult();
        return resultat != null ? resultat.intValue() : 0;
    }

    @Override
    public int countPrestecsByBiblioteca(int idBiblioteca) {
        Long resultat = entityManager.createQuery(
                "SELECT COUNT(p) FROM Prestec p WHERE p.biblioteca.idBiblioteca = :id",
                Long.class)
                .setParameter("id", idBiblioteca)
                .getSingleResult();
        return resultat != null ? resultat.intValue() : 0;
    }

    @Override
    public Agent getBibliotecariByBiblioteca(int idBiblioteca) {
        List<Agent> result = entityManager.createQuery(
                "SELECT b.bibliotecari FROM Biblioteca b WHERE b.idBiblioteca = :id", Agent.class)
                .setParameter("id", idBiblioteca)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }
}
