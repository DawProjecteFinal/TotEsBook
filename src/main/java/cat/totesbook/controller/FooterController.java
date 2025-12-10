package cat.totesbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador que gestiona les pàgines informatives del footer.
 * 
 * @author Equip TotEsBook
 */
@Controller
public class FooterController {

    /**
     * Mostra la pàgina de contacte.
     * 
     * @param model L'objecte Model de Spring per passar atributs a la vista.
     * @return El nom de la vista JSP ("contacte").
     */
    @GetMapping("/contacte")
    public String mostrarPaginaContacte(Model model) {
        return "contacte";
    }

    /**
     * Mostra el popup de "Correu electrònic enviat correctament"
     * @param nom El nom de l'usuari.
     * @param cognoms Els cognoms de l'usuari.
     * @param correu El correu electrònic de l'usuari.
     * @param missatge EL missatge.
     * @param model L'objecte Model de Spring per passar atributs a la vista.
     * @return El nom de la vista JSP ("contacte").
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

    /**
     * Mostra la pàgina de informació legal.
     * 
     * @param model L'objecte Model de Spring per passar atributs a la vista.
     * @return El nom de la vista JSP ("informacioLegal").
     */
    @GetMapping("/informacio-legal")
    public String mostrarPaginaInformacioLegal(Model model) {
        return "informacioLegal";
    }

    /**
     * Mostra la pàgina de "sobre nosaltres".
     * 
     * @param model L'objecte Model de Spring per passar atributs a la vista.
     * @return El nom de la vista JSP ("sobreNosaltres").
     */
    @GetMapping("/sobre-nosaltres")
    public String mostrarPaginaSobreNosaltres(Model model) {
        return "sobreNosaltres";
    }

    /**
     * Mostra la inforamció de privacitat.
     * 
     * @param model L'objecte Model de Spring per passar atributs a la vista.
     * @return El nom de la vista JSP ("informacioPrivacitat").
     */
    @GetMapping("/informacio-privacitat")
    public String mostrarPaginaInformacioPrivacitat(Model model) {
        return "informacioPrivacitat";
    }
}
