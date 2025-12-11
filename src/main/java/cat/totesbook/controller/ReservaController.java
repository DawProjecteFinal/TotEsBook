package cat.totesbook.controller;

import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari;
import cat.totesbook.service.ReservaService;
import cat.totesbook.service.UsuariService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador per a gestionar les operacions relacionades amb les reserves de
 * llibres.
 * 
 * @author Equip TotEsBook
 */
@Controller
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private UsuariService usuariService;
    
    /**
     * Processa la petició per crear una nova reserva per a un llibre.
     *
     * @param isbn L'ISBN del llibre a reservar, rebut del formulari.
     * @param session La sessió HTTP per obtenir l'usuari loguejat.
     * @param redirectAttrs Atributs per enviar missatges de feedback a
     * l'usuari.
     * @return Una redirecció al dashboard de l'usuari o a la fitxa del llibre
     * en cas d'error.
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
            
            if (usuariService.teSancioActiva(idUsuari)) {
            
            Usuari u = usuariService.findUsuariById(idUsuari);

            String missatge = "No pots fer la reserva. Tens una sanció activa fins al "
                    + u.getDataFiSancioFormatted()
                    + " motiu: " + u.getMotiuSancio()
                    + ". No podràs fer cap reserva fins aquesta data.";

            redirectAttrs.addFlashAttribute("error", missatge);

            
            return "redirect:/llibre?isbn=" + isbn;
        }

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

    /**
     * Gestiona la cancel·lació d'una reserva feta per un usuari.
     * 
     * @param idReserva ID de la reserva.
     * @param session La sessió HTTP per obtenir l'usuari loguejat.
     * @param redirect Atributs de redirecció.
     * @return a la dashboard de l'usuari amb un missatge si la cancel·lació s'ha
     * fet de manera correcta. 
     */
    @PostMapping("/cancelReserva")
    public String cancelReserva(@RequestParam("idReserva") int idReserva,
            HttpSession session,
            RedirectAttributes redirect) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return "redirect:/login";
        }

        Reserva reserva = reservaService.findByIdReserva(idReserva);

        if (reserva == null) {
            redirect.addFlashAttribute("error", "La reserva no existeix.");
            return "redirect:/usuari/panell";
        }

        // Comprovació propietari
        if (reserva.getUsuari().getId() != sessio.getId()) {
            redirect.addFlashAttribute("error", "No tens permís per cancel·lar aquesta reserva.");
            return "redirect:/usuari/panell";
        }

        // Si estava disponible alliberem exemplar
        /*if (reserva.getEstat().equals("disponible")) {
            reservaService.alliberarExemplar(reserva);
        }*/

        // Eliminar la reserva
        reservaService.eliminarReserva(idReserva);

        redirect.addFlashAttribute("success", "Reserva cancel·lada i eliminada correctament.");
        return "redirect:/dashboard_usuari";
    }

}
