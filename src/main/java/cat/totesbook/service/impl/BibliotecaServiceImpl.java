package cat.totesbook.service.impl;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.repository.BibliotecaRepository;
import cat.totesbook.service.BibliotecaService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe que implementa el servei de les biblioteques.
 * 
 * @author Equip TotEsBook
 */
@Service
@Transactional 
public class BibliotecaServiceImpl implements BibliotecaService {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    /**
     * Retorna totes les biblioteques.
     * 
     * @return Llistat amb totes les biblioteques.
     */
    @Override
    public List<Biblioteca> getAllBiblioteques() {
        return bibliotecaRepository.getAllBiblioteques();
    }

    /**
     * Cerca la biblioteca segons l'ID especificat.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     * @return Un opcional Biblioteca amb la biblioteca de l'ID passat.
     */
    @Override
    public Optional<Biblioteca> findById(int idBiblioteca) {
        return bibliotecaRepository.findById(idBiblioteca);
    }

    /**
     * Cerca la biblioteca segons el nom indicat.
     * 
     * @param nom El nom de la biblioteca.
     * @return Un optional Biblioteca amb la biblioteca del nom especificat.
     */
    @Override
    public Optional<Biblioteca> findByNom(String nom) {
        return bibliotecaRepository.findByNom(nom);
    }

    /**
     * Afegeix una biblioteca.
     * 
     * @param biblioteca La biblioteca.
     */
    @Override
    public void addBiblioteca(Biblioteca biblioteca) {
        bibliotecaRepository.addBiblioteca(biblioteca);
    }

    /**
     * Desa o guarda informació de la biblioteca.
     * 
     * @param biblioteca La biblioteca.
     */
    @Override
    public void saveOrUpdateBiblioteca(Biblioteca biblioteca) {
        bibliotecaRepository.saveOrUpdateBiblioteca(biblioteca);
    }

    /**
     * Elimina una biblioteca.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     */
    @Override
    public void deleteBiblioteca(int idBiblioteca) {
        try {
            bibliotecaRepository.deleteBiblioteca(idBiblioteca);
            System.out.println("[Service] Biblioteca eliminada correctament: id=" + idBiblioteca);
        } catch (Exception e) {
            System.err.println("[Service] Error eliminant biblioteca id=" + idBiblioteca + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Compta els llibres que hi ha en una biblioteca.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     * @return El nombre dels llibres d'una biblioteca.
     */
    @Override
    public int countLlibresByBiblioteca(int idBiblioteca) {
        return bibliotecaRepository.countLlibresByBiblioteca(idBiblioteca);

    }

    /**
     * Compta els préstecs que hi ha en una biblioteca.
     * 
     * @param idBiblioteca L'ID de la biblioteca.
     * @return El nombre de préstecs d'una biblioteca.
     */
    @Override
    public int countPrestecsByBiblioteca(int idBiblioteca) {
        return bibliotecaRepository.countPrestecsByBiblioteca(idBiblioteca);
    }

    /**
     * Retorna el bibliotecari assignat a la biblioteca.
     * 
     * @param biblioteca La biblioteca.
     * @return Agent assignat a la biblioteca.
     */
    @Override
    public Agent getBibliotecariByBiblioteca(Biblioteca biblioteca) {
        return bibliotecaRepository.getBibliotecariByBiblioteca(biblioteca.getIdBiblioteca());
    }
}
