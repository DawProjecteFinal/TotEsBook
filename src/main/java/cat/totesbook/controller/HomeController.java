package cat.totesbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Classe que 
 * 
 * @author equip TotEsBook
 */
@Controller
public class HomeController {
    /**
     * Mapping relatiu a l'arrel de l'aplicació: /TotEsBook/
     * 
     * @return 
     */
    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public ModelAndView paginaInici(){
        ModelAndView modelview = new ModelAndView("paginaInici");
        modelview.getModelMap().addAttribute("benvinguda", "Benvinguts a TotEsBook!!.");
        return modelview;
    }

}
