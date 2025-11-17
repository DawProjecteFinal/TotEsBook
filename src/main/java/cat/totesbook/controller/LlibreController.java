package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cat.totesbook.service.LlibreService;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

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
    
    @RequestMapping("/llibre")
    public ModelAndView mostrarFitxaLlibre(@RequestParam("isbn") String isbn) {

        ModelAndView mav = new ModelAndView("fitxa_llibre");

        Optional<Llibre> optLlibre = llibreService.getLlibreByIsbn(isbn);

        if (optLlibre.isPresent()) {
            mav.addObject("llibre", optLlibre.get());
        } else {
            mav.addObject("error", "No s'ha trobat el llibre amb ISBN " + isbn);
        }

        return mav;
    }
}