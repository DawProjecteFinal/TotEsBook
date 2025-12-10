package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import java.util.List;
import java.util.Optional;

/**
 * Interfície del servei de BibliotecaLlibre.
 * 
 * @author Equip TotEsBook
 */
public interface BibliotecaLlibreService {
    /**
     * Mètode que busca el llibre dintre de la biblioteca.
     * 
     * @param biblioteca biblioteca.
     * @param llibre llibre.
     * @return  llibre i biblioteca.
     */
    Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre);

    /**
     * En el cas de que intentem carregar de nou un fitxer de Isbn i un llibre ja 
     * existeix, s'augmentarà el número d'exemplars
     * 
     * @param biblioteca biblioteca.
     * @param llibre llibre.
     * @param exemplars exemplars.
     * @param disponibles llibres disponibles.
     */
    void afegirLlibre(Biblioteca biblioteca, Llibre llibre, int exemplars, int disponibles);

    /**
     * Mètode que resta el llibre disponible.
     * 
     * @param biblioteca biblioteca.
     * @param llibre llibre.
     */
    void restarDisponible(Biblioteca biblioteca, Llibre llibre);

    /**
     * Mètode que suma llibres disponibles a una biblioteca.
     * 
     * @param biblioteca biblioteca.
     * @param llibre llibre.
     */
    void sumarDisponible(Biblioteca biblioteca, Llibre llibre);

    /**
     * Mètode que retorna un llibre d'una biblioteca.
     * 
     * @param biblioteca biblioteca.
     * @return llibre de la biblioteca.
     */
    List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca);
    
    /**
     * Mètode que retorna tots els llibres d'una biblioteca.
     * 
     * @param llibre llibre.
     * @return Una llista de BibliotecaLlibre que contenen llibres.
     */
    List<BibliotecaLlibre> findByLlibre(Llibre llibre);
    
    /**
     * Mètode qeu actualitza la relació.
     * 
     * @param relacio La relació.
     * @param biblioteca La biblioteca.
     * @param exemplars Els exemplars.
     */
    void actualitzarRelacio(BibliotecaLlibre relacio, Biblioteca biblioteca, int exemplars);
}
