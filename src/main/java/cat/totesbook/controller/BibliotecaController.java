package cat.totesbook.controller;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.service.BibliotecaLlibreService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.LlibreService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Classe que...
 * 
 * @author equip TotEsBook
 */
@Controller
public class BibliotecaController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private BibliotecaLlibreService bibliotecaLlibreService;
    
    @Autowired
    private LlibreService llibreService;

    /**
     * Envia a la vista tot el llistat de biblioteques per e aveure-les els usuaris normals
     *
     * @param model
     * @return
     */
    @GetMapping("/biblioteques")
    public String mostrarBiblioteques(Model model) {
        // Obtenim totes les biblioteques de la base de dades.
        List<Biblioteca> biblioteques = bibliotecaService.getAllBiblioteques();

        // Afegim la llista al model perquè la vista la pugui utilitzar.
        model.addAttribute("biblioteques", biblioteques);

        // Retornem el nom de la vista JSP (sense .jsp ni ruta completa).
        return "biblioteques/llistar_public";
    }

    /**
     * Mostra els llibres de una biblioteca determinada
     * @param idBiblioteca
     * @param model
     * @return 
     */
    @GetMapping("/biblioteques/{id}/llibres")
    public String mostrarLlibresPerBiblioteca(@PathVariable("id") int idBiblioteca, Model model) {
        // Buscar la biblioteca
        Optional<Biblioteca> bibliotecaOpt = bibliotecaService.findById(idBiblioteca);
        if (bibliotecaOpt.isEmpty()) {
            return "error/404"; // No hi ha llibres a la biblioteca
        }

        Biblioteca biblioteca = bibliotecaOpt.get();

        // Obtenir els llibres associats a aquesta biblioteca
        List<Llibre> llibres = llibreService.findByBiblioteca(biblioteca);

        // Afegir-ho al model
        model.addAttribute("biblioteca", biblioteca);
        model.addAttribute("llibres", llibres);

        // Mostrar la vista amb els llibres
        return "llibresPerBiblioteca";
    }
}
