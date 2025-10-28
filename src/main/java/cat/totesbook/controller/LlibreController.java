package cat.totesbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cat.totesbook.service.LlibreService;

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
}
