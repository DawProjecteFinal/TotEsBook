package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import java.util.List;
import java.util.Optional;

/**
 * Interfície per a les operacions de dades de la taula BibliotecaLlibre.
 * 
 * @author Equip TotEsBook
 */
public interface BibliotecaLlibreRepository {

    /**
     * Retornar llibres de cada biblioteca.
     * 
     * @param biblioteca La biblioteca.
     * @return Una llista de libres de cada biblioteca.
     */
    List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca);

    /**
     * Cerca el llibre i la biblioteca especificats.
     * 
     * @param biblioteca La biblioteca.
     * @param llibre El llibre.
     * @return Un opcional amb una BibliotecaLlibre trobada.
     */
    Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre);

    /**
     * Afegir un llibre a una biblioteca.
     * 
     * @param bibliotecaLlibre El llbire d'una biblioteca.
     */
    void addBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre);

    /**
     * 
     * Actualitza la informació d'un llibre d'una biblitoca.
     * 
     * @param bibliotecaLlibre El llibre d'una biblioteca.
     */
    void updateBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre);

    /**
     * Cerca un llibre espeficicat.
     * 
     * @param llibre El llibre.
     * @return Una llista BibliotecaLlibre amb el llibre especificat.
     */
    List<BibliotecaLlibre> findByLlibre(Llibre llibre);

    /**
     * Cerca el primer llibre segons l'ISBN passat.
     * 
     * @param isbn L'ISBN.
     * @return Un optional amb el primer llibre que troba amb l'ISBN indicat.
     */
    Optional<BibliotecaLlibre> findFirstByLlibreIsbn(String isbn);

    /**
     * Cerca un llibre segons l'ISBN passat.
     * 
     * @param isbn L'ISBN.
     * @return Una llista BibliotecaLlibre que conté el llibre.
     */
    List<BibliotecaLlibre> findByLlibreIsbn(String isbn);


}
