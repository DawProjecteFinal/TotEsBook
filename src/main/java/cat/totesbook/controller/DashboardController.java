package cat.totesbook.controller;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;

import cat.totesbook.service.AgentService;
import cat.totesbook.service.BibliotecaLlibreService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.LlibreService;
import cat.totesbook.service.PrestecService;
import cat.totesbook.service.UsuariService;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DashboardController {

    @Autowired
    private PrestecService prestecService;

    @Autowired
    private BibliotecaService bibliotecaService;

    @Autowired
    private AgentService agentService;

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private LlibreService llibreService;

    @Autowired
    private BibliotecaLlibreService bibliotecaLlibreService;

    @GetMapping("/dashboard_bibliotecari")
    public String mostrarDashboardBibliotecari(Model model, HttpSession session) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessioUsuari == null
                || (sessioUsuari.getRol() != Rol.BIBLIOTECARI && sessioUsuari.getRol() != Rol.ADMIN)) {
            return "redirect:/login";
        }

        if (sessioUsuari.getBibliotecaId() == null) {
            return "biblioteca_no_assignada";
        }

        Integer idBiblioteca = sessioUsuari.getBibliotecaId();
        Biblioteca biblioteca = bibliotecaService.findById(idBiblioteca).orElse(null);

        if (biblioteca == null) {
            return "biblioteca_no_assignada";
        }

        List<Prestec> prestecsActius = prestecService.findActiusByBiblioteca(biblioteca);
        List<Prestec> devolucions = prestecService.findDevolucionsByBiblioteca(biblioteca);

        model.addAttribute("numPrestecsActius", prestecsActius.size());
        model.addAttribute("numDevolucionsPendents", devolucions.size());
        model.addAttribute("numReservesPendents", 0);
        model.addAttribute("numLlibresRetard", 0);

        model.addAttribute("prestecsActius", prestecsActius);
        model.addAttribute("devolucions", devolucions);
        model.addAttribute("biblioteca", biblioteca);

        return "dashboard_bibliotecari";
    }

    @PostMapping("/gestionarDevolucio")
    public String gestionarDevolucio(
            @RequestParam("isbn") String isbn,
            @RequestParam("emailUsuari") String emailUsuari,
            HttpSession session,
            Model model) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessioUsuari == null) {
            model.addAttribute("error", "No hi ha sessió activa.");
            return "redirect:/login";
        }

        try {
            prestecService.registrarDevolucio(isbn, emailUsuari, sessioUsuari.getId());
            model.addAttribute("missatge", "Devolució registrada correctament.");
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "redirect:/dashboard_bibliotecari#devolucions";
    }

    @GetMapping("/dashboard_administrador")
    public String mostrarDashboardAdministrador(Model model, HttpSession session) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
            return "redirect:/login";
        }

        List<Biblioteca> biblioteques = bibliotecaService.getAllBiblioteques();
        for (Biblioteca b : biblioteques) {
            int numLlibres = bibliotecaService.countLlibresByBiblioteca(b.getIdBiblioteca());
            int numPrestecs = bibliotecaService.countPrestecsByBiblioteca(b.getIdBiblioteca());
            b.setNumLlibres(numLlibres);
            b.setNumPrestecs(numPrestecs);
        }
        model.addAttribute("llistaBiblioteques", biblioteques);

        model.addAttribute("llistaAgents", agentService.getAllAgents());
        model.addAttribute("llistaUsuaris", usuariService.getAllUsuaris());

        List<Llibre> llibres = llibreService.getAllLlibres();
        model.addAttribute("llibres", llibres);

        Map<String, List<BibliotecaLlibre>> bibliosPerIsbn = new HashMap<>();
        for (Llibre l : llibres) {
            List<BibliotecaLlibre> relacions = bibliotecaLlibreService.findByLlibre(l);
            bibliosPerIsbn.put(l.getIsbn(), relacions);
        }

        model.addAttribute("bibliosPerIsbn", bibliosPerIsbn);

        return "dashboard_administrador";
    }
}
