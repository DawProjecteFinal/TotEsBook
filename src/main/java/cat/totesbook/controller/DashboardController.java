package cat.totesbook.controller;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.AgentService;
import cat.totesbook.service.PrestecService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.UsuariService;
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

    // Descomentar quan tinguem el servei de reserves creat
    // @Autowired private ReservaService reservaService;
    // SECCIÓ DEL BIBLIOTECARI
    @GetMapping("/dashboard_bibliotecari")
    public String mostrarDashboardBibliotecari(Model model, HttpSession session) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessioUsuari == null
                || (sessioUsuari.getRol() != Rol.BIBLIOTECARI && sessioUsuari.getRol() != Rol.ADMIN)) {
            return "redirect:/login";
        }

        // Quan el bibliotecari no té biblioteca assiganada se li informa
        if (sessioUsuari.getBibliotecaId() == null) {
            return "biblioteca_no_assignada"; // Vista JSP
        }
        Integer idBiblioteca = sessioUsuari.getBibliotecaId();
        Biblioteca biblioteca = bibliotecaService.findById(idBiblioteca).orElse(null);

        if (biblioteca == null) {
            return "biblioteca_no_assignada";
        }

        List<Prestec> prestecsActius = prestecService.findActiusByBiblioteca(biblioteca);
        List<Prestec> devolucions = prestecService.findDevolucionsByBiblioteca(biblioteca);

        // Informació necessaria per a les targetes de resum
        model.addAttribute("numPrestecsActius", prestecsActius.size());
        model.addAttribute("numDevolucionsPendents", devolucions.size());
        model.addAttribute("numReservesPendents", 0);
        model.addAttribute("numLlibresRetard", 0);

        // Informació necessària per a la realització de prèstecs, reserves...
        model.addAttribute("prestecsActius", prestecsActius);
        model.addAttribute("devolucions", devolucions);
        //model.addAttribute("reservesPendents", 8);

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

    // SECCIÓ DE L'ADMINISTRADOR
    @GetMapping("/dashboard_administrador")
    public String mostrarDashboardAdministrador(Model model, HttpSession session) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
            return "redirect:/login";
        }

        model.addAttribute("llistaBiblioteques", bibliotecaService.getAllBiblioteques());
        model.addAttribute("llistaAgents", agentService.getAllAgents());
        model.addAttribute("llistaUsuaris", usuariService.getAllUsuaris());

        return "dashboard_administrador";
    }

}
