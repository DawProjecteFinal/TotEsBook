/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.controller;

import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.PrestecService;
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
    private PrestecService prestecService;

    @PostMapping
    public String registrarPrestec(@RequestParam String isbn,
            @RequestParam String emailUsuari,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {
            SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
            if (sessio == null) {
                return "redirect:/login";
            }
            
            prestecService.registrarPrestec(isbn, emailUsuari, sessio.getId());
            redirectAttributes.addFlashAttribute("missatge", "Préstec registrat correctament.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "No s'ha pogut registrar el préstec: " + e.getMessage());
        }
        return "redirect:/dashboard_bibliotecari";
    }
    
    // --- ENDPOINT PER RENOVAR PRÉSTEC ---
    @PostMapping("/renovar")
    public String renovarPrestec(@RequestParam("idPrestec") Integer idPrestec,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        try {
            SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
            if (sessio == null) {
                return "redirect:/login";
            }

            prestecService.renovarPrestec(idPrestec);
            redirectAttributes.addFlashAttribute("missatge", "Préstec renovat correctament (+30 dies).");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "No s'ha pogut renovar el préstec: " + e.getMessage());
        }

        return "redirect:/dashboard_bibliotecari";
    }
    
    // --- NOU ENDPOINT PER A DEVOLUCIÓ RÀPIDA AMB BOTÓ ---
    @PostMapping("/retornar")
    public String retornarPrestec(@RequestParam("idPrestec") Integer idPrestec,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        try {
            SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
            if (sessio == null) return "redirect:/login";

            // Cridem al nou mètode del servei passant l'ID del préstec i l'ID del bibliotecari
            prestecService.retornarPrestec(idPrestec, sessio.getId());
            
            redirectAttributes.addFlashAttribute("missatge", "Devolució registrada correctament.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error devolució: " + e.getMessage());
        }
        return "redirect:/dashboard_bibliotecari";
    }
}
