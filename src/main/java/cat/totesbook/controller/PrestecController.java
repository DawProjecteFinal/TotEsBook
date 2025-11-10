package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari;
import cat.totesbook.service.AgentService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.LlibreService;
import cat.totesbook.service.PrestecService;
import cat.totesbook.service.UsuariService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author jmiro
 */
@Controller
@RequestMapping("/gestionarPrestec")
public class PrestecController {

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private LlibreService llibreService;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private PrestecService prestecService;

    @PostMapping
    public String registrarPrestec(@RequestParam String isbn,
            @RequestParam String emailUsuari,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");

        // Recuperem el bibliotecari actual
        Agent agent = agentService.getAgentById(sessio.getId());

        // Recuperem l’usuari
        Usuari usuari = usuariService.getUsuariByEmail(emailUsuari);

        //Llibre. Fem servir Optional per a evitar null
        Llibre llibre = llibreService.getLlibreByIsbn(isbn).orElse(null);
        if (llibre == null) {
            redirectAttributes.addFlashAttribute("error", "No s’ha trobat cap llibre amb l’ISBN " + isbn);
            return "redirect:/dashboard_bibliotecari";
        }

        // Biblioteca del bibliotecari
        Biblioteca biblioteca = agent.getBiblioteca();

        if (usuari == null || llibre == null || biblioteca == null) {
            redirectAttributes.addFlashAttribute("error", "No s’ha pogut registrar el préstec. Verifica les dades.");
            return "redirect:/dashboard_bibliotecari";
        }

        // Guardem el préstec
        prestecService.registrarPrestec(usuari, llibre, biblioteca, agent);

        redirectAttributes.addFlashAttribute("missatge", "Préstec registrat correctament per a " + usuari.getNom());
        return "redirect:/dashboard_bibliotecari";
    }
}
