package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari;
import cat.totesbook.service.AgentService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.UsuariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 * Controller per autentificació (login/logout).
 */
@Controller
public class AuthController {

    @Autowired
    private UsuariService usuariService;             //Cridem a la capa de servei

    @Autowired
    private AgentService agentService;

    @Autowired
    private BibliotecaService bibliotecaService;    //Cridem a la capa de servei

    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@RequestParam String email,
            @RequestParam("contrasenya") String contrasenya,
            HttpSession session) {

        // Intentem autenticar com a agent
        Agent agent = agentService.getAgentByEmailAndContrasenya(email, contrasenya);
        if (agent != null) {
            session.setAttribute("sessioUsuari", new SessioUsuari(agent));

            // Si és Administrador
            if (agent.getTipus() == Agent.TipusAgent.administrador) {
                List<Biblioteca> biblioteques = bibliotecaService.getAllBiblioteques();
                List<Agent> agents = agentService.getAllAgents();
                List<Usuari> usuaris = usuariService.getAllUsuaris();

                // Calcular el nombre de llibres i préstecs per a cada biblioteca
                for (Biblioteca b : biblioteques) {
                    int numLlibres = bibliotecaService.countLlibresByBiblioteca(b.getIdBiblioteca());
                    int numPrestecs = bibliotecaService.countPrestecsByBiblioteca(b.getIdBiblioteca());
                    b.setNumLlibres(numLlibres);
                    b.setNumPrestecs(numPrestecs);
                }
                ModelAndView mv = new ModelAndView("dashboard_administrador");
                mv.addObject("llistaBiblioteques", biblioteques);
                mv.addObject("llistaAgents", agents);
                mv.addObject("llistaUsuaris", usuaris);
                return mv;

                // Si és bibliotecari
            } else {
                return new ModelAndView("dashboard_bibliotecari");
            }
        }

        // Intentem autenticar com a usuari lector
        Usuari usuari = usuariService.getUsuariByEmailAndContrasenya(email, contrasenya);
        if (usuari != null) {
            session.setAttribute("sessioUsuari", new SessioUsuari(usuari));
            return new ModelAndView("dashboard_usuari");
        }

        // Credencials incorrectes
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
