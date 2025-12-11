package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import java.util.List;
import cat.totesbook.domain.Llibre;
import java.util.Optional;

/**
 * Interfície del servei de llibres.
 * 
 * @author equip TotEsBook
 */
public interface LlibreService {

    /**
     * Mètode que retorna tots els llibres.
     *
     * @return tots els llibres.
     */
    List<Llibre> getAllLlibres();

    /**
     * Mètode que guarda un llibre.
     *
     * @param llibre El llibre.
     */
    void guardarLlibre(Llibre llibre);

    /**
     * Mètode que retorna el llibre amb l'ISBN indicat.
     *
     * @param isbn L'ISBN.
     * @return llibre amb l'ISBN passat per paràmetre.
     */
    Optional<Llibre> getLlibreByIsbn(String isbn);

    /**
     * Retorna els llibres de una biblioteca en concret
     * 
     * @param biblioteca La biblioteca.
     * @return Els llibes d'una biblioteca en concret.
     */
    List<Llibre> findByBiblioteca(Biblioteca biblioteca);

    /**
     * Retorna els llibres per una categoria concreta
     * 
     * @param categoria La categoria.
     * @return Llibres d'una categoria en concret.
     */
    List<Llibre> getLlibresByCategoria(String categoria);

    /**
     * Retorna els llibres per un títol determinat
     * 
     * @param titol El títol del libre.
     * @return Una llista amb llibres que continguin aquell títol.
     */
    List<Llibre> getLlibreByTitol(String titol);

    /**
     * Retorna els llisbres per  un autor determinat
     * 
     * @param autor L'autor d'un llibre.
     * @return Una llista amb llibres escrits per l'autor indicat.
     */
    List<Llibre> getLlibresByAutor(String autor);

    /**
     * Retorna els llibres per un idioma determinat
     * 
     * @param idioma L'idioma.
     * @return Una llista amb llibres escrits en aquell idioma.
     */
    List<Llibre> getLlibresByIdioma(String idioma);

    /**
     * Retorna una llista de llibres aleatoris.
     * 
     * @param limit El límit.
     * @return Una llista de llibres aleatoris.
     */
    List<Llibre> findRandom(int limit);
    
    /**
     * Mètode que elimina un llibre segons el seu ISBN..
     * 
     * @param isbn ISBN del llibre.
     */
    void deleteLlibre(String isbn);

}
