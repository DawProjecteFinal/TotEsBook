package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari;
import cat.totesbook.service.AgentService;
import cat.totesbook.service.BibliotecaLlibreService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.LlibreService;
import cat.totesbook.service.UsuariService;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller per autentificació (login/logout).
 */
@Controller
public class AuthController {

    @Autowired
    private UsuariService usuariService;             // Cridem a la capa de servei

    @Autowired
    private AgentService agentService;

    @Autowired
    private BibliotecaService bibliotecaService;     // Cridem a la capa de servei

    @Autowired
    private LlibreService llibreService;

    @Autowired
    private BibliotecaLlibreService bibliotecaLlibreService; // Servei necessari per carregar biblioteques i exemplars


    @GetMapping("/login")
    public ModelAndView loginForm() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@RequestParam String email,
                                @RequestParam("contrasenya") String contrasenya,
                                HttpSession session) {

        // Intentem autenticar com a AGENT
        Agent agent = agentService.getAgentByEmailAndContrasenya(email, contrasenya);

        if (agent != null) {

            // Creem sessió
            SessioUsuari sessioUsuari = new SessioUsuari(agent);
            session.setAttribute("sessioUsuari", sessioUsuari);

            //     Si és ADMINISTRADOR
            if (agent.getTipus() == Agent.TipusAgent.administrador) {

                List<Biblioteca> biblioteques = bibliotecaService.getAllBiblioteques();
                List<Agent> agents = agentService.getAllAgents();
                List<Usuari> usuaris = usuariService.getAllUsuaris();
                List<Llibre> llibres = llibreService.getAllLlibres();

                // Calcular el nombre de llibres i préstecs per a cada biblioteca
                for (Biblioteca b : biblioteques) {
                    int numLlibres = bibliotecaService.countLlibresByBiblioteca(b.getIdBiblioteca());
                    int numPrestecs = bibliotecaService.countPrestecsByBiblioteca(b.getIdBiblioteca());
                    b.setNumLlibres(numLlibres);
                    b.setNumPrestecs(numPrestecs);
                }

                // Carregar relacions Biblioteca-Llibre per ISBN (per a la taula admin)
                Map<String, List<BibliotecaLlibre>> bibliosPerIsbn = new HashMap<>();

                for (Llibre l : llibres) {
                    List<BibliotecaLlibre> relacions = bibliotecaLlibreService.findByLlibre(l);
                    bibliosPerIsbn.put(l.getIsbn(), relacions);
                }

                // Retornem la vista del panell d'administració
                ModelAndView mv = new ModelAndView("dashboard_administrador");
                mv.addObject("llistaBiblioteques", biblioteques);
                mv.addObject("llistaAgents", agents);
                mv.addObject("llistaUsuaris", usuaris);
                mv.addObject("llibres", llibres);

               
                mv.addObject("bibliosPerIsbn", bibliosPerIsbn);

                return mv;
            }

            //     Si és BIBLIOTECARI
            else {
                Biblioteca biblioteca = agent.getBiblioteca();
                if (biblioteca != null) {
                    sessioUsuari.assignarBiblioteca(biblioteca);
                    session.setAttribute("sessioUsuari", sessioUsuari);
                }
                return new ModelAndView("redirect:/dashboard_bibliotecari");
            }
        }

        // Intentem autenticar com a USUARI
        Usuari usuari = usuariService.getUsuariByEmailAndContrasenya(email, contrasenya);

        if (usuari != null) {
            session.setAttribute("sessioUsuari", new SessioUsuari(usuari));
            return new ModelAndView("redirect:/dashboard_usuari");
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

