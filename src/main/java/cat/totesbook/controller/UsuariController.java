/**
 * 
 * 
 * @author equip TotEsBook
 */
package cat.totesbook.controller;

import cat.totesbook.service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsuariController {
    @Autowired
    private UsuariService usuariService;

    /**
     * Mapping relatiu a l'arrel de l'aplicació: /TotEsBook/mostrarUsuaris
     * 
     * @return 
     */
    @RequestMapping("/mostrarUsuaris")
    public ModelAndView mostrarUsuaris() {
        ModelAndView modelview = new ModelAndView("mostrarUsuaris");
        
        modelview.addObject("usuaris", usuariService.getAllUsuaris());
        return modelview;
    }
}
