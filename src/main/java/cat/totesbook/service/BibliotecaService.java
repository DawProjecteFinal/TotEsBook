/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import java.util.List;
import java.util.Optional;


public interface BibliotecaService {

    List<Biblioteca> getAllBiblioteques();

    Optional<Biblioteca> findById(int idBiblioteca);

    Optional<Biblioteca> findByNom(String nom);
    
    void deleteBiblioteca(int idBiblioteca);

    void addBiblioteca(Biblioteca biblioteca);
    
    void saveOrUpdateBiblioteca(Biblioteca biblioteca);

    int countLlibresByBiblioteca(int idBiblioteca);

    int countPrestecsByBiblioteca(int idBiblioteca);

    Agent getBibliotecariByBiblioteca(Biblioteca biblioteca);
}
