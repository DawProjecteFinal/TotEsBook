package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import java.util.List;
import cat.totesbook.domain.Llibre;
import java.util.Optional;

/**
 * Interfície de la capa repositori que gestiona les operacions de l'accés de dades dels llibres.
 * 
 * @author Equip TotEsBook
 */

public interface LlibreRepository {
    
    /**
     * Mètode que obté tots els llibres.
     * 
     * @return llista de llibres.
     */
    List<Llibre> getAllLlibres();
    
    /**
     * Mètode que serveix per afegir un llibre.
     * 
     * @param llibre El llibre.
     */
    void addLlibre(Llibre llibre);
    
    /**
     * Mètode que comprova si el ISBN existeix.
     * 
     * @param isbn L'ISBN.
     * @return El llibre amb l'ISBN.
     */
    Optional<Llibre> getLlibreByIsbn(String isbn);

    /**
     * Retorna una llista de llibres de la biblioteca indicada.
     * 
     * @param biblioteca La biblioteca.
     * @return Llista amb totes els llibres de la biblioteca especificada.
     */
    List<Llibre> findByBiblioteca(Biblioteca biblioteca);
    
    /**
     * Retorna una llista de llibres de la categoria indicada.
     * 
     * @param categoria La categoria.
     * @return Llista amb totes els llibres de la categoria indicada.
     */
    List<Llibre> findByCategoria(String categoria);
    
    /**
     * Retorna una llista de llibres amb el títol indicat sense tenir en compte
     * les majúscules ni les minúscules.
     * 
     * @param titol El títol.
     * @return Retorna una llista de llibres amb el títol indicat.
     */
    List<Llibre>findByTitolContainingIgnoreCase(String titol);
    
    /**
     * Retorna una llista de llibres amb l'autor indicat sense tenir en compte
     * les majúscules ni les minúscules.
     * 
     * @param autor L'autor.
     * @return Retorna una llista de llibres amb l'autor indicat.
     */
    List<Llibre>findByAutorContainingIgnoreCase(String autor);
    
    /**
     * Retorna una llista de llibres de l'idioma indicat.
     * 
     * @param idioma L'idioma.
     * @return Retorna una llista de llibres amb l'idioma indicat.
     */
    List<Llibre>findByIdioma(String idioma);

    /**
     * Retorna una llista de llibres aleatoris.
     * 
     * @param limit El límit.
     * @return Una llista de llibres aleatoris.
     */
    List<Llibre> findRandom(int limit);
    
    /**
     * Elimina el llibre segons l'ISBN indicat.
     * 
     * @param isbn L'ISBN.
     */
    void deleteLlibreByIsbn(String isbn);

}