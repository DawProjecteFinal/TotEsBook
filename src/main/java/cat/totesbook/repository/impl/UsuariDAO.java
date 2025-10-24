/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.repository.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 *
 * @author jmiro
 */
@Repository
public class UsuariDAO implements UsuariRepository{
    // Creem EntityManager gestionat Spring
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Usuari> getAllUsuaris() {
        return entityManager.createQuery("SELECT u FROM Usuari u", Usuari.class).getResultList();
    }
    
}
