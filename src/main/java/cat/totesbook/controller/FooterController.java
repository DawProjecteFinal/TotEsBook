package cat.totesbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author equip TotEsBook
 */
@Controller
public class FooterController {

    @GetMapping("/contacte")
    public String mostrarPaginaContacte(Model model) {
        return "contacte";
    }

    /**
     * M;ostra el popup de "Correu electrònic enviat correctament"
     * @param nom
     * @param cognoms
     * @param correu
     * @param missatge
     * @param model
     * @return 
     */
    @PostMapping("/contacte")
    public String processarFormulariContacte(
            @RequestParam("nom") String nom,
            @RequestParam("cognoms") String cognoms,
            @RequestParam("correu") String correu,
            @RequestParam("missatge") String missatge,
            Model model) {

        // Com que és un projecte educatiu, simulem que s'ha enviat el correu.
        model.addAttribute("popup", true);
        model.addAttribute("nomUsuari", nom);

        return "contacte"; // Tornem a la mateixa vista però ara mostrant el pop-up
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
