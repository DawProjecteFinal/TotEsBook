package cat.totesbook.service.impl;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.BibliotecaLlibreRepository;
import cat.totesbook.service.BibliotecaLlibreService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BibliotecaLlibreServiceImpl implements BibliotecaLlibreService {

    @Autowired
    private BibliotecaLlibreRepository repo;

    @Override
    public Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre) {
        return repo.findByBibliotecaAndLlibre(biblioteca, llibre);
    }

    @Override
    public void afegirLlibre(Biblioteca biblioteca, Llibre llibre, int exemplars, int disponibles) {
        Optional<BibliotecaLlibre> existent = repo.findByBibliotecaAndLlibre(biblioteca, llibre);
        if (existent.isPresent()) {
            System.out.println("Aquest llibre ja existeix en aquesta biblioteca.");
            return;
        }

        BibliotecaLlibre bl = new BibliotecaLlibre(biblioteca, llibre, exemplars, disponibles);
        repo.addBibliotecaLlibre(bl);
    }

    @Override
    public void restarDisponible(Biblioteca biblioteca, Llibre llibre) {
        BibliotecaLlibre bl = repo.findByBibliotecaAndLlibre(biblioteca, llibre)
            .orElseThrow(() -> new RuntimeException("Aquest llibre no existeix a la biblioteca"));

        if (bl.getDisponibles() <= 0) {
            throw new RuntimeException("No hi ha exemplars disponibles");
        }

        bl.setDisponibles(bl.getDisponibles() - 1);
        repo.updateBibliotecaLlibre(bl);
    }

    @Override
    public void sumarDisponible(Biblioteca biblioteca, Llibre llibre) {
        BibliotecaLlibre bl = repo.findByBibliotecaAndLlibre(biblioteca, llibre)
            .orElseThrow(() -> new RuntimeException("Aquest llibre no existeix a la biblioteca"));

        bl.setDisponibles(bl.getDisponibles() + 1);
        repo.updateBibliotecaLlibre(bl);
    }

    @Override
    public List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca) {
        return repo.getLlibresPerBiblioteca(biblioteca);
    }
}
