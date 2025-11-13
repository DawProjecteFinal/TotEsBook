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
    public ModelAndView mostrarLlibres(@RequestParam(name = "categoria", required = false)String categoria) {
        ModelAndView modelview = new ModelAndView("mostrarLlibres");
        
        if (categoria == null || categoria.isBlank()){
        modelview.addObject("llibres", llibreService.getAllLlibres());
        modelview.addObject("categoriaSeleccionada", null);
        
    } else {
            modelview.addObject("llibres", llibreService.getLlibresByCategoria(categoria));
            modelview.addObject("categoriaSeleccionada", categoria);
        }


        
        return modelview;
    }
 
}