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
     * @param llibre
     */
    void guardarLlibre(Llibre llibre);

    /**
     * Mètode que retorna el llibre amb l'ISBN indicat.
     *
     * @param isbn
     * @return llibre amb l'ISBN passat per paràmetre.
     */
    Optional<Llibre> getLlibreByIsbn(String isbn);

    // Retorna els llibres de una biblioteca en concret
    List<Llibre> findByBiblioteca(Biblioteca biblioteca);

    //Retorna els llibres per una categoria concreta
    List<Llibre> getLlibresByCategoria(String categoria);

    //Retorna els llibres per un títol determinat
    List<Llibre> getLlibreByTitol(String titol);

    //Retorna els llisbres per  un autor determinat
    List<Llibre> getLlibresByAutor(String autor);

    //Retorna els llibres per un idioma determinat
    List<Llibre> getLlibresByIdioma(String idioma);

    List<Llibre> findRandom(int limit);

}
