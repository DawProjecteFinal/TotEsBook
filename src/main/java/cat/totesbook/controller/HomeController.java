package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import cat.totesbook.service.LlibreService;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LlibreService llibreService;
    private static final Logger logger = Logger.getLogger(HomeController.class.getName());

    /**
     * Mapping relatiu a l'arrel de l'aplicació: /TotEsBook/
     *
     * @return
     */
    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView paginaInici() {
        ModelAndView modelview = new ModelAndView("paginaInici");
        logger.info("********************* Hola Josep ***************************");
        System.out.println("********************* TEST DIRECTE HOLA JOSEP ***************************");
        List <Llibre> totalLlibres = llibreService.getAllLlibres();
        
        // retallem la llista sencera de llibres per  aque només en mostri 8
        List<Llibre> primersVuit = totalLlibres.size() > 8 ? totalLlibres.subList(0, 8) : totalLlibres;
        modelview.addObject("llibres", primersVuit);

        return modelview;
    }

}
