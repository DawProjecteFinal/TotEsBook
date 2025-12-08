/**
 *
 * @author Equip TotEsBook
 */
package cat.totesbook.controller;

import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.service.BibliotecaLlibreService;
import cat.totesbook.service.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cat.totesbook.service.LlibreService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author equip totEsBook
 */
@Controller
public class LlibreController {

    @Autowired
    private LlibreService llibreService;

    @Autowired
    private BibliotecaLlibreService bibliotecaLlibreService;

    @Autowired
    private GoogleBooksService googleBooksService;

    // 
    /**
     * Mapping relatiu a l'arrel de l'aplicació: /TotEsBook/mostrarLlibres
     *
     * @return
     */
    @RequestMapping("/mostrarLlibres")
    public ModelAndView mostrarLlibres(
            @RequestParam(name = "categoria", required = false) String categoria,
            @RequestParam(name = "q", required = false) String titol) {
        ModelAndView modelview = new ModelAndView("mostrarLlibres");

        //Cerca per títol
        if (titol != null && !titol.isBlank()) {
            List<Llibre> llibres = llibreService.getLlibreByTitol(titol);
            modelview.addObject("llibres", llibres);
            modelview.addObject("categoriaSeleccionada", null);
            modelview.addObject("textCerca", titol);
            return modelview;
        }

        //Si no hi ha títol però sí categoria, filtrarà per categoria
        if (categoria != null && !categoria.isBlank()) {
            List<Llibre> llibres = llibreService.getLlibresByCategoria(categoria);
            modelview.addObject("llibres", llibres);
            modelview.addObject("categoriaSeleccionada", categoria);

        } else {
            //Mostrará tots els llibres
            List<Llibre> llibres = llibreService.getAllLlibres();
            modelview.addObject("llibres", llibres);
            modelview.addObject("categoriaSeleccionada", null);
        }
        return modelview;
    }

    @RequestMapping("/cercar")
    public ModelAndView buscarAvancat(@RequestParam("field") String field,
            @RequestParam("q") String valor) {

        ModelAndView mv = new ModelAndView("mostrarLlibres");

        List<Llibre> resultats;

        switch (field) {
            case "autor":
                resultats = llibreService.getLlibresByAutor(valor);
                mv.addObject("tipusCerca", "autor");
                break;

            case "isbn":
                Optional<Llibre> optLlibre = llibreService.getLlibreByIsbn(valor.trim());
                // Convertim l'Optional a una llista (0 o 1 element)
                resultats = optLlibre
                        .map(Collections::singletonList)
                        .orElseGet(Collections::emptyList);
                mv.addObject("tipusCerca", "isbn");
                break;

            case "idioma":
                resultats = llibreService.getLlibresByIdioma(valor);
                mv.addObject("tipusCerca", "idioma");
                break;

            default:
                resultats = llibreService.getAllLlibres();
                mv.addObject("tipusCerca", null);
                break;
        }

        mv.addObject("llibres", resultats);
        mv.addObject("categoriaSeleccionada", null);
        mv.addObject("textCerca", valor);

        return mv;
    }

    /**
     * Mostra la fitxa detallada d'un llibre a partir del seu ISBN. Aquest
     * mètode respon a URLs com /llibre/978-84-9932-123-4
     *
     * @param isbn L'ISBN del llibre, rebut des de la URL.
     * @param model El model de Spring per passar dades a la vista.
     * @param redirectAttrs Per enviar missatges en cas de redirecció.
     * @return El nom de la vista ("fitxa_llibre") o una redirecció si no es
     * troba.
     */
    @GetMapping("/llibre")
    public String mostrarFitxaLlibre(@RequestParam("isbn") String isbn,
            @RequestParam(value = "mode", required = false, defaultValue = "reserva") String mode,
            Model model, RedirectAttributes redirectAttrs) {
        try {
            // Bùsqueda del llibre pel seu ISBN a través del servei
            Optional<Llibre> llibreOpt = llibreService.getLlibreByIsbn(isbn);

            if (llibreOpt.isPresent()) {
                Llibre llibre = llibreOpt.get();

                // Biblioteques on es troba aquest llibre
                List<BibliotecaLlibre> ubicacions = bibliotecaLlibreService.findByLlibre(llibre);

                model.addAttribute("llibre", llibre);
                model.addAttribute("mode", mode);
                model.addAttribute("ubicacions", ubicacions);

                return "fitxa_llibre";
            } else {
                redirectAttrs.addFlashAttribute("error", "El llibre amb ISBN '" + isbn + "' no s'ha trobat.");
                return "redirect:/";
            }
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "S'ha produït un error en carregar la fitxa del llibre.");
            return "redirect:/";
        }
    }

    /**
     * Quan no troba un llibre a la base de dades de les biblioteques, dóna la
     * opció de buscar-lo a la Api de Google Books
     *
     * @param titol
     * @param autor
     * @param isbn
     * @return
     */
    @GetMapping("/llibres/cercar_api")
    public ModelAndView cercarApi(
            @RequestParam(required = false) String titol,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String isbn) {

        List<Llibre> resultats = googleBooksService.cercarLlibres(titol, autor, isbn);

        ModelAndView mv = new ModelAndView("llibres/llibres_resultats_api");
        mv.addObject("resultats", resultats);
        mv.addObject("titol", titol);
        mv.addObject("autor", autor);
        mv.addObject("isbn", isbn);

        return mv;
    }

    /**
     * Obre la fitxa del llibre que ha trobat a la api de Google Books
     *
     * @param isbn
     * @param titol
     * @param mode
     * @param isbn
     * @param autor
     * @return
     */
    @GetMapping("/llibres/fitxa_api")
    public ModelAndView fitxaApi(@RequestParam String isbn,
            @RequestParam(required = false) String titol,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String mode){

        Optional<Llibre> llibreOpt = googleBooksService.getLlibreByIsbn(isbn);

        ModelAndView mv = new ModelAndView("fitxa_llibre");

        mv.addObject("llibre", llibreOpt.orElse(null));
        mv.addObject("mode", "api");

        mv.addObject("titol", titol);
        mv.addObject("autor", autor);
        mv.addObject("isbn", isbn);

        return mv;
    }

}
