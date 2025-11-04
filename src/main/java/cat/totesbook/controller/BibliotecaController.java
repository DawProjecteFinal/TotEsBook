
package cat.totesbook.controller;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.service.BibliotecaLlibreService;
import cat.totesbook.service.BibliotecaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author jmiro
 */

@Controller
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private BibliotecaLlibreService bibliotecaLlibreService;

    /**
     * Mostra tots els llibres d’una biblioteca concreta.
     * Exemple d’URL: /biblioteca/1/llibres
     */

    @GetMapping("/biblioteca/{id}/llibres")
    public String mostrarLlibresPerBiblioteca(@PathVariable("id") int idBiblioteca, Model model) {
        // Busquem la biblioteca
        Biblioteca biblioteca = bibliotecaService.findById(idBiblioteca)
                .orElseThrow(() -> new RuntimeException("Biblioteca no trobada"));

        // Obtenim la llista de llibres d'aquesta biblioteca
        List<BibliotecaLlibre> llibres = bibliotecaLlibreService.getLlibresPerBiblioteca(biblioteca);

        // Afegim dades al model per a la vista
        model.addAttribute("biblioteca", biblioteca);
        model.addAttribute("llibresBiblioteca", llibres);

        // Retornem el nom de la vista JSP
        return "mostrarLlibresBiblioteca";
    }
}
