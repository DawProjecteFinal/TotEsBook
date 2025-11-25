/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import java.util.List;
import java.util.Optional;


public interface BibliotecaLlibreRepository {

    List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca);

    Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre);

    void addBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre);

    void updateBibliotecaLlibre(BibliotecaLlibre bibliotecaLlibre);

    
    List<BibliotecaLlibre> findByLlibre(Llibre llibre);


    Optional<BibliotecaLlibre> findFirstByLlibreIsbn(String isbn);

    List<BibliotecaLlibre> findByLlibreIsbn(String isbn);


}
