/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.repository;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import java.util.List;
import java.util.Optional;


public interface BibliotecaRepository {

    List<Biblioteca> getAllBiblioteques();

    Optional<Biblioteca> findById(int idBiblioteca);

    Optional<Biblioteca> findByNom(String nom);

    void addBiblioteca(Biblioteca biblioteca);
    
    void saveOrUpdateBiblioteca(Biblioteca biblioteca);
    
    void deleteBiblioteca(int idBiblioteca);
    
    int countLlibresByBiblioteca(int idBiblioteca);
    
    int countPrestecsByBiblioteca(int idBiblioteca);
    
    Agent getBibliotecariByBiblioteca(int idBiblioteca);
}
