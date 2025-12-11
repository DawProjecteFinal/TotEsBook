package cat.totesbook.service;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import java.util.List;
import java.util.Optional;

/**
 * Interfície del servei de biblioteques.
 * 
 * @author Equip TotEsBook
 */
public interface BibliotecaService {

    /**
     * Retorna totes les biblioteques.
     * 
     * @return Llistat amb totes les biblioteques.
     */
    List<Biblioteca> getAllBiblioteques();

    /**
     * Cerca la biblioteca segons l'ID especificat.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     * @return Un opcional Biblioteca amb la biblioteca de l'ID passat.
     */
    Optional<Biblioteca> findById(int idBiblioteca);

    /**
     * Cerca la biblioteca segons el nom indicat.
     * 
     * @param nom El nom de la biblioteca.
     * @return Un optional Biblioteca amb la biblioteca del nom especificat.
     */
    Optional<Biblioteca> findByNom(String nom);
    
    /**
     * Elimina una biblioteca.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     */
    void deleteBiblioteca(int idBiblioteca);

    /**
     * Afegeix una biblioteca.
     * 
     * @param biblioteca La biblioteca.
     */
    void addBiblioteca(Biblioteca biblioteca);
    
    /**
     * Desa o guarda informació de la biblioteca.
     * 
     * @param biblioteca La biblioteca.
     */
    void saveOrUpdateBiblioteca(Biblioteca biblioteca);

    /**
     * Compta els llibres que hi ha en una biblioteca.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     * @return El nombre dels llibres d'una biblioteca.
     */
    int countLlibresByBiblioteca(int idBiblioteca);

    /**
     * Compta els préstecs que hi ha en una biblioteca.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     * @return El nombre dels préstecs d'una biblioteca.
     */
    int countPrestecsByBiblioteca(int idBiblioteca);

    /**
     * Retorna el bibliotecari assignat a la biblioteca.
     * 
     * @param biblioteca La biblioteca.
     * @return Agent assignat a la biblioteca.
     */
    Agent getBibliotecariByBiblioteca(Biblioteca biblioteca);
}
