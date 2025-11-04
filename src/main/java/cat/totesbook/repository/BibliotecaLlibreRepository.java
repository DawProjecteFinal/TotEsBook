/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.repository;


import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import java.util.List;
import java.util.Optional;


/**
 *
 * @author jmiro
 */

public interface BibliotecaLlibreRepository {

    List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca);

    Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre);

    void addBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre);

    void updateBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre);
}
