package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cat.totesbook.service.LlibreService;
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

    // 
    /**
     * Mapping relatiu a l'arrel de l'aplicació: /TotEsBook/mostrarLlibres
     * 
     * @return 
     */
    @RequestMapping("/mostrarLlibres")
    public ModelAndView mostrarLlibres() {
        ModelAndView modelview = new ModelAndView("mostrarLlibres");


        modelview.addObject("llibres", llibreService.getAllLlibres());
        return modelview;
    }

    /**
     * Mostra la fitxa detallada d'un llibre a partir del seu ISBN.
     * Aquest mètode respon a URLs com /llibre/978-84-9932-123-4
     *
     * @param isbn L'ISBN del llibre, rebut des de la URL.
     * @param model El model de Spring per passar dades a la vista.
     * @param redirectAttrs Per enviar missatges en cas de redirecció.
     * @return El nom de la vista ("fitxa_llibre") o una redirecció si no es troba.
     */
    @GetMapping("/llibre")
    public String mostrarFitxaLlibre(@RequestParam("isbn") String isbn, Model model, RedirectAttributes redirectAttrs) {
        try {
            // Busquem el llibre pel seu ISBN a través del servei
            Optional<Llibre> llibreOpt = llibreService.getLlibreByIsbn(isbn);

            if (llibreOpt.isPresent()) {
                // Si el llibre existeix, l'afegim al model i mostrem la vista
                model.addAttribute("llibre", llibreOpt.get());
                return "fitxa_llibre"; // El ViewResolver buscarà /WEB-INF/views/fitxa_llibre.jsp
            } else {
                // Si no es troba, redirigim a l'inici amb un missatge d'error
                redirectAttrs.addFlashAttribute("error", "El llibre amb ISBN '" + isbn + "' no s'ha trobat.");
                return "redirect:/";
            }
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "S'ha produït un error en carregar la fitxa del llibre.");
            return "redirect:/";
        }
    }
 
}