package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Reserva.EstatReserva;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.AgentService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.PrestecService;
import cat.totesbook.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador que gestiona la reserva que fa el bibliotecari.
 * 
 * @author Equip TotEsBook
 */
@Controller
public class BibliotecariReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private PrestecService prestecService;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private AgentService agentService;

    /**
     * Mostra la vista de les reserves.
     * 
     * @param idReserva L'ID de la reserva.
     * @param session La sessió HTTP per comprovar l'autorització.
     * @param redirect Atribut per enviar missatges de feedback.
     * @return Redirecció al panell del bibliotecari.
     */
    @GetMapping("/bibliotecari/reserves/gestionar")

    public String gestionarReserva(@RequestParam("id") int idReserva,
            HttpSession session,
            RedirectAttributes redirect) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessio == null || sessio.getRol() == null
                || (sessio.getRol() != Rol.BIBLIOTECARI && sessio.getRol() != Rol.ADMIN)) {
            return "redirect:/login";
        }

        Reserva reserva = reservaService.findByIdReserva(idReserva);

        if (reserva == null) {
            redirect.addFlashAttribute("error", "La reserva no existeix.");
            return "redirect:/dashboard_bibliotecari";
        }

        if (reserva.getEstat() != EstatReserva.pendent) {
            redirect.addFlashAttribute("error", "Aquesta reserva no està pendent i no es pot gestionar.");
            return "redirect:/dashboard_bibliotecari";
        }

        // Recuperem l’Agent a partir del seu ID guardat a la sessió:
        Agent agentBibliotecari = agentService.getAgentById(sessio.getId());

        // Crear préstec passant reserva + agent
        prestecService.crearPrestecDesDeReserva(reserva, agentBibliotecari);

        // Eliminar reserva
        reservaService.eliminarReserva(idReserva);

        redirect.addFlashAttribute("success",
                "S'ha creat un préstec actiu per al llibre: «" + reserva.getLlibre().getTitol() + "»");

        return "redirect:/dashboard_bibliotecari";
    }

    /**
     * Eliminar una reserva.
     * 
     * @param idReserva L'ID de la reserva.
     * @param session La sessió HTTP per comprovar l'autorització.
     * @param redirect Atribut per enviar missatges de feedback.
     * @return Redirecció al panell del bibliotecari.
     */
    @GetMapping("/bibliotecari/reserves/eliminar")
    public String eliminarReserva(@RequestParam("id") int idReserva,
            HttpSession session,
            RedirectAttributes redirect) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessio == null || (sessio.getRol() != Rol.BIBLIOTECARI && sessio.getRol() != Rol.ADMIN)) {
            return "redirect:/login";
        }

        Reserva reserva = reservaService.findByIdReserva(idReserva);

        if (reserva == null) {
            redirect.addFlashAttribute("error", "Aquesta reserva no existeix.");
            return "redirect:/dashboard_bibliotecari";
        }

        reservaService.eliminarReserva(idReserva);

        redirect.addFlashAttribute("success",
                "La reserva per al llibre «" + reserva.getLlibre().getTitol() + "» ha estat eliminada.");

        return "redirect:/dashboard_bibliotecari";
    }

}
