/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jmiro
 */
public interface BibliotecaService {

    List<Biblioteca> getAllBiblioteques();

    Optional<Biblioteca> findById(int idBiblioteca);

    Optional<Biblioteca> findByNom(String nom);

    void addBiblioteca(Biblioteca biblioteca);

    int countLlibresByBiblioteca(int idBiblioteca);

    int countPrestecsByBiblioteca(int idBiblioteca);
}
