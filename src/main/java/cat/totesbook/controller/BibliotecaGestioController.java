package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.BibliotecaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import cat.totesbook.domain.Rol;
import cat.totesbook.service.AgentService;
import java.util.List;
import java.util.Optional;
import cat.totesbook.domain.Usuari;
import cat.totesbook.service.UsuariService;

/**
 *
 * @author jmiro
 */
@Controller
@RequestMapping("/gestio/biblioteques")
public class BibliotecaGestioController {

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private UsuariService usuariService;

    /**
     * Mostra el llistat de biblioteques per a administradors o bibliotecaris
     */
    @GetMapping
    public ModelAndView llistarBiblioteques(HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        // Si l'usuari no és ADMIN, el redirigim a la seva pàgina corresponent
        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/dashboard_bibliotecari");
        }

        // Carreguem TOTES les dades necessàries per al dashboard d'administrador
        List<Biblioteca> llistaBiblioteques = bibliotecaService.getAllBiblioteques();
        List<Agent> llistaAgents = agentService.getAllAgents();
        List<Usuari> llistaUsuaris = usuariService.getAllUsuaris();

        // Calculem les estadístiques per a cada biblioteca
        for (Biblioteca b : llistaBiblioteques) {
            int numLlibres = bibliotecaService.countLlibresByBiblioteca(b.getIdBiblioteca());
            int numPrestecs = bibliotecaService.countPrestecsByBiblioteca(b.getIdBiblioteca());
            b.setNumLlibres(numLlibres);
            b.setNumPrestecs(numPrestecs);
        }

        // Enviem les dades a la vista del dashboard principal
        ModelAndView mv = new ModelAndView("dashboard_administrador");
        mv.addObject("llistaBiblioteques", llistaBiblioteques);
        mv.addObject("llistaAgents", llistaAgents);
        mv.addObject("llistaUsuaris", llistaUsuaris);
        // El rol ja està a la sessió, no cal passar-lo per separat
        return mv;
    }

    /**
     * Mostra la gestió d'una biblioteca concreta
     */
    @GetMapping("/{id}")
    public ModelAndView gestionarBiblioteca(@PathVariable("id") int idBiblioteca, HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        Optional<Biblioteca> bibliotecaOpt = bibliotecaService.findById(idBiblioteca);
        if (bibliotecaOpt.isEmpty()) {
            return new ModelAndView("redirect:/gestio/biblioteques").addObject("error", "Biblioteca no trobada.");
        }

        ModelAndView mv = new ModelAndView("biblioteques/gestionar_biblioteca");
        mv.addObject("biblioteca", bibliotecaOpt.get());
        mv.addObject("rol", sessio.getRol());
        return mv;
    }

    /**
     * Formulari per afegir una biblioteca (només admin)
     */
    @GetMapping("/afegir")
    public ModelAndView mostrarFormulariAfegir(HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/gestio/biblioteques")
                    .addObject("error", "No tens permisos per crear biblioteques.");
        }

        List<Agent> bibliotecaris = agentService.getAllBibliotecaris();

        ModelAndView mv = new ModelAndView("biblioteques/afegir_biblioteca");
        mv.addObject("biblioteca", new Biblioteca());
        mv.addObject("llistaBibliotecaris", bibliotecaris);
        return mv;
    }

    /**
     * Guarda una nova biblioteca
     */
    @PostMapping("/afegir")
    public ModelAndView afegirBiblioteca(@ModelAttribute Biblioteca biblioteca,
                                       @RequestParam(value = "idBibliotecari", required = false) Integer idBibliotecari,
                                       HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        // Si s'ha seleccionat un bibliotecari, l'assignem a la biblioteca
        if (idBibliotecari != null) {
            Agent bibliotecari = new Agent();
            bibliotecari.setIdAgent(idBibliotecari);
            biblioteca.setBibliotecari(bibliotecari);
        } else {
            // Si no, ens assegurem que el bibliotecari sigui nul
            biblioteca.setBibliotecari(null);
        }

        bibliotecaService.addBiblioteca(biblioteca);

        return new ModelAndView("redirect:/gestio/biblioteques")
                .addObject("success", "Biblioteca creada correctament.");
    }

    /**
     * Elimina una biblioteca (només admin)
     */
    @PostMapping("/{id}/eliminar")
    public ModelAndView eliminarBiblioteca(@PathVariable("id") int idBiblioteca, HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/gestio/biblioteques")
                    .addObject("error", "No tens permisos per eliminar biblioteques.");
        }

        bibliotecaService.deleteBiblioteca(idBiblioteca);
        return new ModelAndView("redirect:/gestio/biblioteques")
                .addObject("success", "Biblioteca eliminada correctament.");
    }

    /**
     * Mètode que serveix el formulari per a editar les dades de la biblioteca
     *
     * @param idBiblioteca
     * @param session
     * @return
     */
    @GetMapping("/{id}/editar")
    public ModelAndView mostrarFormulariEditar(@PathVariable("id") int idBiblioteca, HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        Optional<Biblioteca> bibliotecaOpt = bibliotecaService.findById(idBiblioteca);
        if (bibliotecaOpt.isEmpty()) {
            return new ModelAndView("redirect:/gestio/biblioteques")
                    .addObject("error", "Biblioteca no trobada.");
        }

        ModelAndView mv = new ModelAndView("biblioteques/editar_biblioteca");
        mv.addObject("biblioteca", bibliotecaOpt.get());
        mv.addObject("llistaBibliotecaris", agentService.getAllBibliotecaris());
        mv.addObject("rol", sessio.getRol());
        return mv;
    }

    /**
     * Mètode per guaradr els canvis de la edició
     *
     * @param idBiblioteca
     * @param biblioteca
     * @param session
     * @return
     */
    @PostMapping("/{id}/editar")
    public ModelAndView editarBiblioteca(@PathVariable("id") int idBiblioteca,
            @ModelAttribute Biblioteca biblioteca, HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        biblioteca.setIdBiblioteca(idBiblioteca);
        // Guardem o actualitzem segons toqui
        bibliotecaService.saveOrUpdateBiblioteca(biblioteca);

        return new ModelAndView("redirect:/gestio/biblioteques")
                .addObject("success", "Biblioteca actualitzada correctament.");
    }

}
