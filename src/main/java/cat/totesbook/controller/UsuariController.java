package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari; // Import afegit
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.repository.UsuariRepository;

import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt; // Import afegit
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Import afegit
import org.springframework.web.bind.annotation.RequestParam; // Import afegit
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Import afegit

import java.util.List;

/**
 * Controlador Spring MVC per a les funcions de gestió d'usuaris.
 * Substitueix el 'MostrarUsuarisServlet'.
 * Només accessible per a l'ADMIN.
 */
@Controller
public class UsuariController {

    // Spring injectarà automàticament les implementacions (DAO)
    @Autowired
    private UsuariRepository usuariRepo;
    
    @Autowired
    private AgentRepository agentRepo;

    /**
     * Gestiona les peticions GET a /mostrarUsuaris.
     * Carrega les llistes d'usuaris i agents i les passa a la vista.
     *
     * @param model   L'objecte Model de Spring per passar atributs a la vista.
     * @param session La sessió HTTP per comprovar l'autorització.
     * @return El nom de la vista JSP (sense el prefix /WEB-INF/views/) o una redirecció.
     */
    @GetMapping("/mostrarUsuaris")
    public String mostrarLlistatUsuaris(Model model, HttpSession session) {
        
        // --- Comprovació de Seguretat (idèntica a la del filtre) ---
        SessioUsuari sessioUsuari = (session != null) ? (SessioUsuari) session.getAttribute("sessioUsuari") : null;
        
        // Si no està loguejat o no és ADMIN, redirigim a l'inici
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
            // Fem un redirect a la pàgina d'inici
            return "redirect:/paginaInici.jsp";
        }
        // --- Fi Comprovació Seguretat ---

        try {
            // 1. Obtenim les llistes des dels repositoris injectats
            List<Usuari> llistaUsuaris = usuariRepo.getAllUsuaris();
            List<Agent> llistaAgents = agentRepo.getAllAgents();

            // 2. Desem les llistes al Model (en lloc de la request)
            model.addAttribute("llistaUsuaris", llistaUsuaris);
            model.addAttribute("llistaAgents", llistaAgents);

            // 3. Retornem la RUTA COMPLETA al fitxer JSP dins de WEB-INF
            //    (Ja que probablement no hi ha un ViewResolver configurat)
            return "/WEB-INF/views/mostrarUsuaris.jsp";

        } catch (Exception e) {
            System.err.println("Error a UsuariController.mostrarLlistatUsuaris: " + e.getMessage());
            e.printStackTrace(); 
            // Podem afegir un atribut d'error al model
            model.addAttribute("error", "No s'han pogut carregar les dades d'usuaris.");
            // I redirigir a l'inici (o a una pàgina d'error)
            return "redirect:/paginaInici.jsp";
        }
    }
    
    // --- INICI DEL NOU CODI CONVERTIT (DES DE RegistreServlet) ---

    /**
     * Gestiona les peticions GET a /registre.
     * Mostra la pàgina (formulari) de registre.
     * @param session La sessió HTTP per comprovar si l'usuari ja està loguejat.
     * @return El nom de la vista JSP o una redirecció.
     */
    
    @GetMapping("/registre")
    public String mostrarFormulariRegistre(HttpSession session) {
        // Comprovem si l'usuari ja està loguejat
        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessioUsuari != null) {
            // Si ja està loguejat, el redirigim a l'inici
            return "redirect:/paginaInici.jsp";
        }
        
        // Retornem la ruta completa al JSP privat (com feia el teu servlet)
        return "/WEB-INF/views/registre.jsp";
    }

    /**
     * Processa l'enviament del formulari de registre (POST a /registre).
     *
     * @param nom              Nom de l'usuari.
     * @param cognoms          Cognoms de l'usuari.
     * @param email            Email de l'usuari.
     * @param contrasenyaPlana Contrasenya en text pla (name="contrasenya").
     * @param telefon          Telèfon (opcional).
     * @param model            Model per afegir errors (si fem forward).
     * @param redirectAttrs    Atributs per afegir missatges (si fem redirect).
     * @return Redirecció al login (èxit) o forward a registre (error).
     */
    @PostMapping("/registre")
    public String processarRegistre(
            @RequestParam("nom") String nom,
            @RequestParam("cognoms") String cognoms,
            @RequestParam("email") String email,
            @RequestParam("contrasenya") String contrasenyaPlana,
            @RequestParam(value = "telefon", required = false) String telefon,
            Model model,
            RedirectAttributes redirectAttrs) {

        // 1. Comprovar si l'usuari ja existeix (fent servir el mètode corregit 'getUsuariByEmail')
        Usuari usuariExistent = usuariRepo.getUsuariByEmail(email);
        
        if (usuariExistent != null) {
            // Error: L'email ja existeix
            model.addAttribute("error", "Aquest correu electrònic ja està registrat.");
            // Retornem (forward) a la vista de registre
            return "/WEB-INF/views/registre.jsp";
        }

        // 2. Hashejem la contrasenya
        String contrasenyaHashejada = BCrypt.hashpw(contrasenyaPlana, BCrypt.gensalt());

        // 3. Creem i desem el nou usuari
        Usuari nouUsuari = new Usuari();
        nouUsuari.setNom(nom);
        nouUsuari.setCognoms(cognoms);
        nouUsuari.setEmail(email);
        nouUsuari.setTelefon(telefon);
        nouUsuari.setContrasenya(contrasenyaHashejada); // Guardem el hash
        
        usuariRepo.saveUsuari(nouUsuari);

        // 4. Redirigim al login amb un missatge d'èxit
        //    (Fem servir RedirectAttributes per passar missatges entre redireccions)
        //    Això és millor que el 'forward' que tenies al servlet original.
        redirectAttrs.addFlashAttribute("success", "Registre completat! Ara pots iniciar sessió.");
        return "redirect:/login"; // Redirigim a la URL del LoginServlet/Controller
    }
    
    // --- FI DEL NOU CODI CONVERTIT ---
}
