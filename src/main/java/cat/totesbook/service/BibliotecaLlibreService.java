package cat.totesbook.service;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import java.util.List;
import java.util.Optional;

public interface BibliotecaLlibreService {

    Optional<BibliotecaLlibre> findByBibliotecaAndLlibre(Biblioteca biblioteca, Llibre llibre);

    void afegirLlibre(Biblioteca biblioteca, Llibre llibre, int exemplars, int disponibles);

    void restarDisponible(Biblioteca biblioteca, Llibre llibre);

    void sumarDisponible(Biblioteca biblioteca, Llibre llibre);

    List<BibliotecaLlibre> getLlibresPerBiblioteca(Biblioteca biblioteca);
    
    List<BibliotecaLlibre> findByLlibre(Llibre llibre);
}
