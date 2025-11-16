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

    // En el cas de que intentem carregar de nou un fitxer de Isbn i un llibre ja 
    // existeix, s'augmentarà el número d'exemplars
    @Override
    public void afegirLlibre(Biblioteca biblioteca, Llibre llibre, int exemplars, int disponibles) {

        Optional<BibliotecaLlibre> existentOpt = repo.findByBibliotecaAndLlibre(biblioteca, llibre);

        if (existentOpt.isPresent()) {
            BibliotecaLlibre existent = existentOpt.get();

            // Actualitzar exemplars i disponibles
            existent.setExemplars(existent.getExemplars() + exemplars);
            existent.setDisponibles(existent.getDisponibles() + disponibles);

            repo.updateBibliotecaLlibre(existent);

            System.out.println(">>> [UPDATE] " + llibre.getTitol()
                    + " ja existeix a " + biblioteca.getNom()
                    + ". S'afegeixen " + exemplars + " exemplars (total: "
                    + existent.getExemplars() + ")");

            return;
        }

        // Crear entrada nova
        BibliotecaLlibre nou = new BibliotecaLlibre(biblioteca, llibre, exemplars, disponibles);
        repo.addBibliotecaLlibre(nou);

        System.out.println(">>> [NOU] Afegit llibre " + llibre.getTitol()
                + " a " + biblioteca.getNom()
                + " amb " + exemplars + " exemplars.");
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
