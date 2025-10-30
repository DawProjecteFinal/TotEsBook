package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import cat.totesbook.service.LlibreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LlibreDetailsController {

    @Autowired
    private LlibreService llibreService;

    @GetMapping("/llibre")
    public ModelAndView veureLlibre(@RequestParam(name = "isbn", required = false) String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return new ModelAndView("paginaInici");
        }

        Optional<Llibre> opt = llibreService.getLlibreByIsbn(isbn);
        if (opt.isPresent()) {
            ModelAndView mv = new ModelAndView("fitxa_llibre");
            mv.addObject("llibre", opt.get());
            return mv;
        }

        ModelAndView mv = new ModelAndView("paginaInici");
        mv.addObject("errorLlibre", "No s'ha trobat cap llibre amb l'ISBN " + isbn);
        return mv;
    }
}
