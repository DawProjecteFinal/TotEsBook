package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.repository.AgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

@Controller
public class CanviRolController {

    @Autowired
    private AgentRepository agentRepo;

    @PostMapping("/canviRol")
    public ModelAndView canviarRol(@RequestParam("idCompte") int idCompte,
                                   @RequestParam("nouRol") String nouRolString,
                                   @RequestParam("tipusCompte") String tipusCompte,
                                   HttpSession session) {

        SessioUsuari sessio = (session != null) ? (SessioUsuari) session.getAttribute("sessioUsuari") : null;
        if (sessio == null || sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("paginaInici");
        }

        String feedbackMessage = null;

        try {
            if ("AGENT".equalsIgnoreCase(tipusCompte)) {
                Agent.TipusAgent tipus = ("ADMIN".equalsIgnoreCase(nouRolString)) ? Agent.TipusAgent.administrador : Agent.TipusAgent.bibliotecari;
                agentRepo.updateAgentTipus(idCompte, tipus);
                feedbackMessage = "Rol de l'agent ID " + idCompte + " actualitzat a " + nouRolString;
            } else {
                feedbackMessage = "Acció no implementada per a tipusCompte=" + tipusCompte;
            }
        } catch (Exception e) {
            feedbackMessage = "Error processant la sol·licitud: " + e.getMessage();
        }

        ModelAndView mv = new ModelAndView("dashboard_administrador");
        mv.addObject("feedbackMessage", feedbackMessage);
        return mv;
    }
}
