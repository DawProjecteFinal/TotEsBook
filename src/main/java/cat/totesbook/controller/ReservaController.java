/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.controller;

import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador per a gestionar les operacions relacionades amb les reserves de llibres.
 */
@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    /**
     * Processa la petició per crear una nova reserva per a un llibre.
     *
     * @param isbn L'ISBN del llibre a reservar, rebut del formulari.
     * @param session La sessió HTTP per obtenir l'usuari loguejat.
     * @param redirectAttrs Atributs per enviar missatges de feedback a l'usuari.
     * @return Una redirecció al dashboard de l'usuari o a la fitxa del llibre en cas d'error.
     */
    @PostMapping("/reservar")
    public String processarReserva(@RequestParam("isbn") String isbn, HttpSession session, RedirectAttributes redirectAttrs) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        // Comprovació de seguretat: l'usuari ha d'estar loguejat i ser un lector.
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.USUARI) {
            redirectAttrs.addFlashAttribute("error", "Has d'iniciar sessió com a usuari per poder reservar.");
            return "redirect:/login";
        }

        try {
            int idUsuari = sessioUsuari.getId();

            // Cridem al servei per crear la reserva.
            reservaService.crearReserva(idUsuari, isbn);

            redirectAttrs.addFlashAttribute("success", "Llibre reservat correctament! El trobaràs al teu panell.");
            return "redirect:/dashboard_usuari";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "No s'ha pogut realitzar la reserva: " + e.getMessage());
            return "redirect:/llibre?isbn=" + isbn; // Tornem a la fitxa del llibre amb l'error.
        }
    }
}