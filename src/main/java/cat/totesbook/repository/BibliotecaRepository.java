
package cat.totesbook.repository;

import cat.totesbook.domain.Biblioteca;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author jmiro
 */

public interface BibliotecaRepository {

    List<Biblioteca> getAllBiblioteques();

    Optional<Biblioteca> findById(int idBiblioteca);

    Optional<Biblioteca> findByNom(String nom);

    void addBiblioteca(Biblioteca biblioteca);
    
    int countLlibresByBiblioteca(int idBiblioteca);
    
    int countPrestecsByBiblioteca(int idBiblioteca);
}
