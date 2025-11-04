/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.repository.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.BibliotecaLlibreRepository;
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
@Transactional
public class BibliotecaLlibreDAO implements BibliotecaLlibreRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca) {
        TypedQuery<BibliotecaLlibre> query = entityManager.createQuery(
            "SELECT bl FROM BibliotecaLlibre bl WHERE bl.biblioteca = :biblioteca",
            BibliotecaLlibre.class
        );
        query.setParameter("biblioteca", biblioteca);
        return query.getResultList();
    }

    @Override
    public Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre) {
        try {
            TypedQuery<BibliotecaLlibre> query = entityManager.createQuery(
                "SELECT bl FROM BibliotecaLlibre bl WHERE bl.biblioteca = :biblioteca AND bl.llibre = :llibre",
                BibliotecaLlibre.class
            );
            query.setParameter("biblioteca", biblioteca);
            query.setParameter("llibre", llibre);
            List<BibliotecaLlibre> resultats = query.getResultList();
            return resultats.isEmpty() ? Optional.empty() : Optional.of(resultats.get(0));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void addBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre) {
        entityManager.persist(bibliotecaLlibre);
    }

    @Override
    public void updateBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre) {
        entityManager.merge(bibliotecaLlibre);
    }
}
