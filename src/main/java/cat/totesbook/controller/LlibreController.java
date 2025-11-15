package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cat.totesbook.service.LlibreService;
import java.util.List;
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

}
