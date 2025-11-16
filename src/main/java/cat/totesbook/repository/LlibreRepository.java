package cat.totesbook.repository;
import cat.totesbook.domain.Biblioteca;
import java.util.List;
import cat.totesbook.domain.Llibre;
import java.util.Optional;

/**
 * Interfície de la capa repositori que gestiona les operacions de l'accés de dades dels llibres.
 * 
 * @author equip TotEsBook
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
     * @param llibre 
     */
    void addLlibre(Llibre llibre);
    
    /**
     * Mètode que comprova si el ISBN existeix
     * 
     * @param isbn
     * @return el llibre amb l'ISBN.
     */
    Optional<Llibre> getLlibreByIsbn(String isbn);

    List<Llibre> findByBiblioteca(Biblioteca biblioteca);
    
    List<Llibre> findByCategoria(String categoria);
    
    List<Llibre>findByTitolContainingIgnoreCase(String titol);
    
    List<Llibre>findByAutorContainingIgnoreCase(String autor);
    
    List<Llibre>findByIdioma(String idioma);
}