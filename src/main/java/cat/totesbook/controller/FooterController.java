package cat.totesbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Diana Martin Vilá
 */

@Controller
public class FooterController {

    @GetMapping("/contacte")
    public String mostrarPaginaContacte(Model model) {
        return "contacte";
    }
    
    @GetMapping("/informacio-legal")
    public String mostrarPaginaInformacioLegal(Model model) {
        return "informacioLegal";
    }
    
    @GetMapping("/sobre-nosaltres")
    public String mostrarPaginaSobreNosaltres(Model model) {
        return "sobreNosaltres";
    }
    
    @GetMapping("/informacio-privacitat")
    public String mostrarPaginaInformacioPrivacitat(Model model) {
        return "informacioPrivacitat";
    }
}



