package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;

/**
 * Controller per autentificació (login/logout).
 */
@Controller
public class AuthController {

    @Autowired
    private UsuariRepository usuariRepo;

    @Autowired
    private AgentRepository agentRepo;

    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@RequestParam String email,
                                @RequestParam("contrasenya") String contrasenya,
                                HttpSession session) {

        // 1) Intentem autenticar com a agent (staff)
        Agent agent = agentRepo.getAgentByEmailAndContrasenya(email, contrasenya);
        if (agent != null) {
            session.setAttribute("sessioUsuari", new SessioUsuari(agent));
            // Decideix on enviar segons rol
            if (agent.getTipus() == Agent.TipusAgent.administrador) {
                return new ModelAndView("dashboard_administrador");
            } else {
                return new ModelAndView("dashboard_bibliotecari");
            }
        }

        // 2) Intentem autenticar com a usuari lector
        Usuari usuari = usuariRepo.getUsuariByEmailAndContrasenya(email, contrasenya);
        if (usuari != null) {
            session.setAttribute("sessioUsuari", new SessioUsuari(usuari));
            return new ModelAndView("dashboard_usuari");
        }

        // 3) Credencials incorrectes
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("error", "Credencials incorrectes. Torna-ho a provar.");
        return mv;
    }

    @GetMapping("/logout")
    public ModelAndView doLogout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return new ModelAndView("redirect:/");
    }
}
