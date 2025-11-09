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
 *
 * @author jmiro
 */
@Service
@Transactional
public class BibliotecaServiceImpl implements BibliotecaService {

    @Autowired
    private BibliotecaRepository bibliotecaRepository;

    @Override
    public List<Biblioteca> getAllBiblioteques() {
        return bibliotecaRepository.getAllBiblioteques();
    }

    @Override
    public Optional<Biblioteca> findById(int idBiblioteca) {
        return bibliotecaRepository.findById(idBiblioteca);
    }

    @Override
    public Optional<Biblioteca> findByNom(String nom) {
        return bibliotecaRepository.findByNom(nom);
    }

    @Override
    public void addBiblioteca(Biblioteca biblioteca) {
        bibliotecaRepository.addBiblioteca(biblioteca);
    }

    @Override
    public void saveOrUpdateBiblioteca(Biblioteca biblioteca) {
        bibliotecaRepository.saveOrUpdateBiblioteca(biblioteca);
    }

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

    @Override
    public int countLlibresByBiblioteca(int idBiblioteca) {
        return bibliotecaRepository.countLlibresByBiblioteca(idBiblioteca);

    }

    @Override
    public int countPrestecsByBiblioteca(int idBiblioteca) {
        return bibliotecaRepository.countPrestecsByBiblioteca(idBiblioteca);
    }

    @Override
    public Agent getBibliotecariByBiblioteca(Biblioteca biblioteca) {
        return bibliotecaRepository.getBibliotecariByBiblioteca(biblioteca.getIdBiblioteca());
    }
}
