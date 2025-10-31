package cat.totesbook.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @RequestMapping(value = {"","/"}, method = RequestMethod.GET)
    public ModelAndView paginaInici(){
        ModelAndView modelview = new ModelAndView("paginaInici");
        modelview.getModelMap().addAttribute("benvinguda", "Benvinguts a TotEsBook. Missatge antic!!.");
        return modelview;
    }

}
