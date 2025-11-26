/**
 *
 * @author Equip TotEsBook
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Prestec;
import cat.totesbook.domain.Reserva;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.LlibreService;
import cat.totesbook.service.ReservaService;
import cat.totesbook.service.PrestecService;
import cat.totesbook.service.UsuariService;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.service.BibliotecaLlibreService;
import java.util.Map;
import java.util.HashMap;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controlador Spring MVC per a les funcions de gestió d'usuaris i el procés de
 * registre. Només accessible per a l'ADMIN (excepte el registre).
 */
@Controller
public class UsuariController {

    private static final int NUMEROLLIBRESMOSTRAR = 8;

    // Spring injectarà automàticament les implementacions (DAO)
    @Autowired
    private UsuariRepository usuariRepo;

    @Autowired
    private AgentRepository agentRepo;

    // Per englobar les Transaccions les fiquem directament a la capa de servei
    @Autowired
    private PrestecService prestecService;

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private LlibreService llibreService;

    @Autowired
    private UsuariService usuariService;

    @Autowired
    private BibliotecaLlibreService bibliotecaLlibreService;

    /**
     * Gestiona les peticions GET a /mostrarUsuaris. Carrega les llistes
     * d'usuaris i agents i les passa a la vista.
     *
     * @param model L'objecte Model de Spring per passar atributs a la vista.
     * @param session La sessió HTTP per comprovar l'autorització.
     * @return El nom de la vista JSP ("mostrarUsuaris") o una redirecció.
     */
    @GetMapping("/mostrarUsuaris")
    public String mostrarLlistatUsuaris(Model model, HttpSession session) {

        // --- Comprovació de Seguretat ---
        SessioUsuari sessioUsuari = (session != null) ? (SessioUsuari) session.getAttribute("sessioUsuari") : null;

        // Si no està loguejat o no és ADMIN, redirigim a l'inici
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
            return "redirect:/"; // Redirigim a l'IniciController
        }
        // --- Fi Comprovació Seguretat ---

        try {
            // 1. Obtenim les llistes des dels repositoris injectats
            List<Usuari> llistaUsuaris = usuariRepo.getAllUsuaris();
            List<Agent> llistaAgents = agentRepo.getAllAgents();

            // 2. Desem les llistes al Model
            model.addAttribute("llistaUsuaris", llistaUsuaris);
            model.addAttribute("llistaAgents", llistaAgents);

            // 3. Retornem el NOM de la vista.
            // El ViewResolver buscarà: /WEB-INF/views/mostrarUsuaris.jsp
            return "mostrarUsuaris";

        } catch (Exception e) {
            System.err.println("Error a UsuariController.mostrarLlistatUsuaris: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "No s'han pogut carregar les dades d'usuaris.");
            // I redirigim a l'inici
            return "redirect:/";
        }
    }

    /**
     * Gestiona les peticions GET a /dashboard_usuari. Comprova la sessió i
     * mostra el panell de l'usuari.
     */
    @GetMapping("/dashboard_usuari")
    public String mostrarDashboardUsuari(Model model, HttpSession session) {
        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        // Comprovació de seguretat
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.USUARI) {
            return "redirect:/login";
        }

        int idUsuari = sessioUsuari.getId();

        // Carreguem els llibres destacats primer, fora del try-catch principal.
        // Així, encara que falli la càrrega de préstecs/reserves, els llibres es mostraran.
        try {
            List<Llibre> novetats = llibreService.findRandom(NUMEROLLIBRESMOSTRAR);
            model.addAttribute("llibres", novetats);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorCarregantLlibres", "No s'han pogut carregar els llibres destacats.");
        }

        try {
            // Carregar prestecs actius
            List<Prestec> meusPrestecs = prestecService.findPrestecsActiusByUsuari(idUsuari);
            model.addAttribute("meusPrestecs", meusPrestecs);
            // Carregar historial de prèstecs
            List<Prestec> historial = prestecService.findPrestecsRetornatsByUsuari(idUsuari);
            model.addAttribute("historialPrestecs", historial);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorCarregantPrestecs", "No s'han pogut carregar els teus préstecs.");
        }
        try {
            // Carregar reserves
            List<Reserva> mevesReserves = reservaService.findReservaByUsuari(idUsuari);
            model.addAttribute("mevesReserves", mevesReserves);

            // Mapa ISBN -> biblioteques on és el llibre reservat
            Map<String, List<BibliotecaLlibre>> bibliosPerIsbn = new HashMap<>();

            for (Reserva r : mevesReserves) {
                Llibre llibre = r.getLlibre();
                if (llibre != null && !bibliosPerIsbn.containsKey(llibre.getIsbn())) {
                    List<BibliotecaLlibre> relacions = bibliotecaLlibreService.findByLlibre(llibre);
                    bibliosPerIsbn.put(llibre.getIsbn(), relacions);
                }
            }

            model.addAttribute("bibliosPerIsbn", bibliosPerIsbn);

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorCarregantReserves", "No s'han pogut carregar les teves reserves.");
        }
        return "dashboard_usuari";
    }

    /**
     * Gestiona les peticions GET a /dashboard_bibliotecari. Comprova la sessió
     * i mostra el panell del bibliotecari.
     */
    /*
    @GetMapping("/dashboard_bibliotecari")
    public String mostrarDashboardBibliotecari(HttpSession session) {
        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");
        
        if (sessioUsuari == null || (sessioUsuari.getRol() != Rol.BIBLIOTECARI && sessioUsuari.getRol() != Rol.ADMIN)) {
            return "redirect:/login";
        }
        
        // El ViewResolver buscarà: /WEB-INF/views/dashboard_bibliotecario.jsp
        // TODO: Carregar dades necessàries per a aquest panell (ex: reserves pendents)
        return "dashboard_bibliotecario";
    }
     */
    /**
     * Gestiona les peticions GET a /dashboard_admin. Comprova la sessió i
     * mostra el panell de l'administrador.
     */
    /*
    @GetMapping("/dashboard_admin")
    public String mostrarDashboardAdmin(HttpSession session) {
        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");
        
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
            return "redirect:/login";
        }
        
        // El ViewResolver buscarà: /WEB-INF/views/dashboard_admin.jsp
        // NOTA: El teu 'dashboard_admin.jsp' actualment carrega les dades
        // amb scriptlets (<% ... %>) al propi JSP. Això funcionarà.
        // (En una futura refactorització, aquesta lògica hauria de moure's aquí).
        return "dashboard_admin";
    }
     */
    // --- Lògica de Registre ---
    /**
     * Gestiona les peticions GET a /registre. Mostra la pàgina (formulari) de
     * registre.
     *
     * @param session La sessió HTTP per comprovar si l'usuari ja està loguejat.
     * @return El nom de la vista JSP ("registre") o una redirecció.
     */
    @GetMapping("/registre")
    public String mostrarFormulariRegistre(HttpSession session) {
        // Comprovem si l'usuari ja està loguejat
        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessioUsuari != null) {
            // Si ja està loguejat, el redirigim a l'inici
            return "redirect:/";
        }

        // Retornem el NOM de la vista.
        // El ViewResolver buscarà: /WEB-INF/views/registre.jsp
        return "registre";
    }

    /**
     * Processa l'enviament del formulari de registre (POST a /registre).
     *
     * @param nom Nom de l'usuari.
     * @param cognoms Cognoms de l'usuari.
     * @param email Email de l'usuari.
     * @param contrasenyaPlana Contrasenya en text pla (name="contrasenya").
     * @param telefon Telèfon (opcional).
     * @param model Model per afegir errors (si fem forward).
     * @param redirectAttrs Atributs per afegir missatges (si fem redirect).
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

        // 1. Comprovar si l'usuari ja existeix
        Usuari usuariExistent = usuariRepo.getUsuariByEmail(email);

        if (usuariExistent != null) {
            // Error: L'email ja existeix
            model.addAttribute("error", "Aquest correu electrònic ja està registrat.");
            // Retornem (forward) al NOM de la vista de registre
            return "registre"; // Mostra /WEB-INF/views/registre.jsp amb el missatge d'error
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
        redirectAttrs.addFlashAttribute("success", "Registre completat! Ara pots iniciar sessió.");
        return "redirect:/login"; // Redirigim a la URL del LoginController
    }

    // --- INICI NOU CODI PER "EDITAR PERFIL" ---
    /**
     * Gestiona les peticions GET a /perfil. Mostra la pàgina (formulari)
     * d'edició de perfil de l'usuari loguejat.
     *
     * @param session La sessió HTTP per obtenir l'ID de l'usuari.
     * @param model El model de Spring per passar les dades de l'usuari a la
     * vista.
     * @return El nom de la vista JSP ("editarPerfil") o una redirecció.
     */
    @GetMapping("/perfil")
    public String mostrarFormulariPerfil(HttpSession session, Model model) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        // Comprovació de seguretat: si no està loguejat, fora.
        if (sessioUsuari == null) {
            return "redirect:/login";
        }

        // Només els Usuaris (lectors) poden editar el seu perfil aquí.
        if (sessioUsuari.getRol() != Rol.USUARI) {
            model.addAttribute("error", "Els administradors o bibliotecaris no poden editar el seu perfil aquí.");
            // Redirigim al dashboard corresponent
            if (sessioUsuari.getRol() == Rol.ADMIN) {
                return "redirect:/dashboard_admin";
            }
            if (sessioUsuari.getRol() == Rol.BIBLIOTECARI) {
                return "redirect:/dashboard_bibliotecari";
            }
            return "redirect:/";
        }

        try {
            // Busquem les dades completes de l'usuari a la BBDD
            Usuari usuariComplet = usuariRepo.findUsuariById(sessioUsuari.getId());
            if (usuariComplet == null) {
                // Error estrany (usuari a la sessió però no a la BBDD)
                session.invalidate();
                return "redirect:/login?error=SessioInvalida";
            }

            // Passem l'objecte Usuari a la vista
            model.addAttribute("usuari", usuariComplet);
            return "editarPerfil"; // Busca /WEB-INF/views/editarPerfil.jsp

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error en carregar les dades del perfil.");
            return "dashboard_usuari";
        }
    }

    /**
     * Processa l'enviament del formulari d'edició de perfil (POST a /perfil).
     *
     * @param nom El nou nom del formulari.
     * @param cognoms Els nous cognoms del formulari.
     * @param email El nou email del formulari.
     * @param telefon El nou telèfon del formulari.
     * @param session La sessió HTTP per actualitzar les dades de sessió.
     * @param redirectAttrs Atributs per enviar missatges de feedback.
     * @return Redirecció al dashboard de l'usuari.
     */
    @PostMapping("/perfil")
    public String processarPerfil(
            @RequestParam("nom") String nom,
            @RequestParam("cognoms") String cognoms,
            @RequestParam("email") String email,
            @RequestParam(value = "telefon", required = false) String telefon,
            HttpSession session,
            RedirectAttributes redirectAttrs) {

        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.USUARI) {
            return "redirect:/login"; // Comprovació de seguretat
        }

        try {
            // Validació d'Email: Comprovem si el NOU email ja està en ús PER UNA ALTRA PERSONA
            Usuari usuariEmailExistent = usuariRepo.getUsuariByEmail(email);
            if (usuariEmailExistent != null && usuariEmailExistent.getId() != sessioUsuari.getId()) {
                // Error: L'email ja pertany a un altre usuari
                redirectAttrs.addFlashAttribute("error", "Error: Aquest correu electrònic ja està registrat per un altre usuari.");
                return "redirect:/perfil"; // Tornem al formulari d'edició
            }

            // --- INICI DE LA CORRECCIÓ DE LÒGICA ---
            // 1. Obtenim l'usuari complet de la BBDD (per conservar dades que no són al formulari, com la contrasenya o els favorits)
            Usuari usuariPerActualitzar = usuariRepo.findUsuariById(sessioUsuari.getId());

            // 2. Actualitzem només els camps que venen del formulari
            usuariPerActualitzar.setNom(nom);
            usuariPerActualitzar.setCognoms(cognoms);
            usuariPerActualitzar.setEmail(email);
            usuariPerActualitzar.setTelefon(telefon);

            // 3. Cridem al mètode updatePerfil (que ara sí que desa els favorits)
            usuariRepo.updatePerfil(usuariPerActualitzar);

            // 4. Creem un NOU objecte SessioUsuari amb les dades refrescades
            SessioUsuari novaSessioUsuari = new SessioUsuari(usuariPerActualitzar);

            // 5. Substituïm l'objecte antic a la sessió
            session.setAttribute("sessioUsuari", novaSessioUsuari);

            // --- FI DE LA CORRECCIÓ DE LÒGICA ---
            redirectAttrs.addFlashAttribute("success", "Perfil actualitzat correctament!");
            return "redirect:/dashboard_usuari";

        } catch (Exception e) {
            System.err.println("Error processant l'actualització del perfil: " + e.getMessage());
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("error", "Error inesperat en desar el perfil.");
            return "redirect:/perfil";
        }
    }
    // --- FI NOU CODI PER "EDITAR PERFIL" ---

    // --- MÈTODES PER A CREAR USUARIS DES DEL PANELL BIBLIOTECARI ---
    // 1. Mostrar el formulari (GET)
    @GetMapping("/bibliotecari/nou-usuari")
    public String vistaCrearUsuari(HttpSession session, Model model) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");

        // Seguretat: Només bibliotecari
        if (sessio == null || sessio.getRol() != Rol.BIBLIOTECARI) {
            return "redirect:/login";
        }
        return "nou_usuari"; // Nom del fitxer JSP 
    }

    // 2. Processar la creació (POST)
    @PostMapping("/bibliotecari/crear-usuari")
    public String crearUsuari(@RequestParam String nom,
            @RequestParam String cognoms,
            @RequestParam String telefon,
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null || sessio.getRol() != Rol.BIBLIOTECARI) {
            return "redirect:/login";
        }

        try {
            // Cridem al servei
            usuariService.crearLectorManual(nom, cognoms, telefon, email, password);

            redirectAttributes.addFlashAttribute("missatge", "Nou usuari lector registrat correctament.");
        } catch (Exception e) {
            // Si falla (ex: email duplicat), tornem al formulari amb l'error
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/bibliotecari/nou-usuari";
        }

        // Si tot va bé, tornem al dashboard
        return "redirect:/dashboard_bibliotecari";
    }
}
