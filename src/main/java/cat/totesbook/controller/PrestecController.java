/**
 *
 * @author Equip TotEsBook
 */
package cat.totesbook.controller;

import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.PrestecService;
import cat.totesbook.service.UsuariService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jmiro
 */
@Controller
@RequestMapping("/gestionarPrestec")
public class PrestecController {

    @Autowired
    private PrestecService prestecService;

    @Autowired
    private UsuariService usuariService;
    
    @GetMapping
    public String mostrarGestioPrestec(@RequestParam("idPrestec") Integer idPrestec,
            HttpSession session,
            org.springframework.ui.Model model,
            RedirectAttributes redirectAttributes) {

        try {
            SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
            if (sessio == null) {
                return "redirect:/login";
            }

            var prestec = prestecService.getPrestecPerId(idPrestec);

            if (prestec == null) {
                redirectAttributes.addFlashAttribute("error", "El préstec no existeix.");
                return "redirect:/dashboard_bibliotecari";
            }

            model.addAttribute("prestec", prestec);

            boolean usuariSancionat = usuariService.teSancioActiva(prestec.getUsuari().getId());
            model.addAttribute("usuariSancionat", usuariSancionat);

            return "gestionarPrestec";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error carregant el préstec: " + e.getMessage());
            return "redirect:/dashboard_bibliotecari";
        }
    }

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

        return "redirect:/gestionarPrestec?idPrestec=" + idPrestec;
    }

    // --- NOU ENDPOINT PER A DEVOLUCIÓ RÀPIDA AMB BOTÓ ---
    @PostMapping("/retornar")
    public String retornarPrestec(@RequestParam("idPrestec") Integer idPrestec,
            HttpSession session,
            RedirectAttributes redirectAttributes) {
        try {
            SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
            if (sessio == null) {
                return "redirect:/login";
            }

            // Cridem al nou mètode del servei passant l'ID del préstec i l'ID del bibliotecari
            prestecService.retornarPrestec(idPrestec, sessio.getId());

            redirectAttributes.addFlashAttribute("missatge", "Devolució registrada correctament.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error devolució: " + e.getMessage());
        }
        return "redirect:/dashboard_bibliotecari";
    }

    @PostMapping("/sancionar")
    public String sancionarPrestec(@RequestParam("idPrestec") Integer idPrestec,
            @RequestParam("tipus") String tipus,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        try {

            SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
            if (sessio == null) {
                return "redirect:/login";
            }

            var prestec = prestecService.getPrestecPerId(idPrestec);
            if (prestec == null) {
                redirectAttributes.addFlashAttribute("error", "El préstec no existeix.");
                return "redirect:/dashboard_bibliotecari";
            }

            int dies;
            String motiu;

            if ("RETARD".equalsIgnoreCase(tipus)) {
                dies = 15;
                motiu = "Retorn del llibre amb retard";
            } else if ("MAL_ESTAT".equalsIgnoreCase(tipus)) {
                dies = 45;
                motiu = "Llibre retornat en mal estat";
            } else {
                redirectAttributes.addFlashAttribute("error", "Tipus de sanció no vàlid.");
                return "redirect:/gestionarPrestec?idPrestec=" + idPrestec;
            }

            LocalDateTime dataFiSancio = LocalDateTime.now().plusDays(dies);

            Integer idUsuari = prestec.getUsuari().getId();
            usuariService.aplicarSancio(idUsuari, dataFiSancio, motiu);

            String dataFiText = dataFiSancio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            redirectAttributes.addFlashAttribute(
                    "missatge",
                    "Sanció aplicada a l'usuari fins al " + dataFiText + ". Motiu: " + motiu
            );

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute(
                    "error",
                    "No s'ha pogut aplicar la sanció: " + e.getMessage()
            );
        }

        return "redirect:/gestionarPrestec?idPrestec=" + idPrestec;
    }

}
