package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.PropostaAdquisicio;
import cat.totesbook.domain.PropostaAdquisicio.EstatProposta;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.GoogleBooksService;
import cat.totesbook.service.PropostaAdquisicioService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author equip totesbook
 */
@Controller
@RequestMapping("/propostes")
public class PropostesController {

    @Autowired
    GoogleBooksService googleBooksService;

    @Autowired
    PropostaAdquisicioService propostaAdquisicioService;

    /**
     * Retorna el formulari per a buscar el llibre de la proposta
     *
     * @return
     */
    @GetMapping("/formulari_proposta")

    public String mostrarFormulariCerca() {
        return "propostes/formulari_proposta";
    }

    /**
     * Recupera titol, autor i isbn del formulari i fa la consulta a la Api
     * retornant tota la llista de resultats
     *
     * @param titol
     * @param autor
     * @param isbn
     * @param model
     * @return
     */
    @GetMapping("/buscar_proposta")
    public String cercarLlibres(
            @RequestParam(required = false) String titol,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false) String isbn,
            Model model) {

        // Si no hi ha cap camp oplert, torna a la vista anterior
        if ((titol == null || titol.isBlank())
                && (autor == null || autor.isBlank())
                && (isbn == null || isbn.isBlank())) {

            model.addAttribute("error", "Has d'introduir algun criteri de cerca.");
            return "propostes/formulari_proposta";
        }

        List<Llibre> resultats = googleBooksService.cercarLlibres(titol, autor, isbn);
        // Agafem les dades que s'han introduït al formulari per a tenir-les guardades
        // per si desde la fitxa del llibre volem tornar enrera
        model.addAttribute("titol", titol);
        model.addAttribute("autor", autor);
        model.addAttribute("isbn", isbn);

        model.addAttribute("resultats", resultats);

        // Isbn exemple del Quijote: 978-8471664570
        return "propostes/proposta_resultats";
    }

    /**
     * Busca un llibre pel seu ISBN, però en aquest cas el busca a la API de
     * Google
     *
     * @param isbn
     * @param titol
     * @param autor
     * @param isbnQuery
     * @param mode
     * @param model
     * @param redirectAttrs
     * @return
     */
    @GetMapping("/llibre")
    public String mostrarFitxaLlibre(
            @RequestParam("isbn") String isbn,
            @RequestParam(value = "titol", required = false) String titol,
            @RequestParam(value = "autor", required = false) String autor,
            @RequestParam(value = "isbnQuery", required = false) String isbnQuery,
            @RequestParam(value = "mode", required = false, defaultValue = "proposta") String mode,
            Model model,
            RedirectAttributes redirectAttrs) {

        isbn = isbn == null ? "" : isbn.trim();
        Optional<Llibre> llibreOpt = googleBooksService.getLlibreByIsbn(isbn);

        if (llibreOpt.isPresent()) {
            model.addAttribute("llibre", llibreOpt.get());
            model.addAttribute("mode", mode);

            // Afegim també els criteris de cerca per a poder tornar als
            // resultats anteriors
            model.addAttribute("titol", titol);
            model.addAttribute("autor", autor);
            model.addAttribute("isbnQuery", isbnQuery);

            return "fitxa_llibre";
        }

        return "redirect:/propostes/formulari_proposta";
    }

    /**
     * Mètode que obre el formulari per afegir el motiu de la proposta
     *
     * @param isbn
     * @param titol
     * @param autor
     * @param isbnCerca
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/confirmar")
    public String confirmarProposta(@RequestParam String isbn,
            @RequestParam(required = false) String titol,
            @RequestParam(required = false) String autor,
            @RequestParam(required = false, name = "isbn_cerca") String isbnCerca,
            Model model, HttpSession session) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return "redirect:/login";
        }

        Optional<Llibre> llibreOpt = googleBooksService.getLlibreByIsbn(isbn);

        if (llibreOpt.isEmpty()) {
            model.addAttribute("error", "No s’ha pogut recuperar la informació del llibre.");
            return "redirect:/propostes/formulari_proposta";
        }

        model.addAttribute("llibre", llibreOpt.get());
        // Afegim els paràmetres per a poder recuperar-los en cas de tirar enrera
        model.addAttribute("titol", titol);
        model.addAttribute("autor", autor);
        model.addAttribute("isbn_cerca", isbnCerca);

        return "propostes/confirmar_proposta";
    }

    /**
     * Mètode que crida al repositori per a guardar la proposta a la taula de la
     * BD
     *
     * @param isbn
     * @param titol
     * @param autor
     * @param editorial
     * @param motiu
     * @param session
     * @return
     */
    @PostMapping("/guardar")
    public String guardarProposta(
            @RequestParam String isbn,
            @RequestParam String titol,
            @RequestParam String autor,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String motiu,
            HttpSession session) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return "redirect:/login";
        }

        PropostaAdquisicio p = new PropostaAdquisicio();
        p.setIdUsuari(sessio.getId());
        p.setNomUsuari(sessio.getNomComplet());
        p.setIsbn(isbn);
        p.setTitol(titol);
        p.setAutor(autor);
        p.setEditorial(editorial);
        p.setMotiu(motiu);
        p.setDataProposta(LocalDateTime.now());
        p.setEstat(EstatProposta.pendent);

        propostaAdquisicioService.guardarProposta(p);

        return "redirect:/propostes/llista_propostes";
    }

    /**
     * Mostra la llista de propostes que ha fet un usuari
     *
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/llista_propostes")
    public String llistaPropostes(HttpSession session, Model model) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");

        if (sessio == null) {
            return "redirect:/login";
        }

        List<PropostaAdquisicio> propostes = propostaAdquisicioService.findByUsuari(sessio.getId());

        model.addAttribute("propostes", propostes);

        return "propostes/llista_propostes";
    }

    
    @GetMapping("/eliminar")
    public String eliminarProposta(@RequestParam("id") int idProposta,
            HttpSession session,
            RedirectAttributes redirect) {

        // Comprovació de sessió
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return "redirect:/login";
        }

        // Recuperar la proposta
        PropostaAdquisicio proposta = propostaAdquisicioService.findByIdProposta(idProposta);

        if (proposta == null) {
            redirect.addFlashAttribute("error", "La proposta no existeix.");
            return "redirect:/propostes/llista_propostes";
        }

        // Comprovació: només el propietari pot eliminar
        if (proposta.getIdUsuari() != sessio.getId()) {
            redirect.addFlashAttribute("error", "No tens permís per eliminar aquesta proposta.");
            return "redirect:/propostes/llista_propostes";
        }

        // Només es pot eliminar si està pendent
        if (proposta.getEstat() != EstatProposta.pendent) {
            redirect.addFlashAttribute("error", "Només es pot eliminar una proposta pendent.");
            return "redirect:/propostes/llista_propostes";
        }

        // Eliminar
        propostaAdquisicioService.eliminarProposta(idProposta);

        redirect.addFlashAttribute("missatge", "Proposta eliminada correctament.");
        return "redirect:/propostes/llista_propostes";
    }
    /**
     * Mostra el detall de una proposta a l'administrador
     * @param idProposta
     * @param model
     * @return 
     */
    @GetMapping("/detall")
    public String veureDetall(@RequestParam("id") int idProposta, Model model) {

        PropostaAdquisicio proposta = propostaAdquisicioService.findByIdProposta(idProposta);

        if (proposta == null) {
            return "redirect:/dashboard_administrador";
        }

        model.addAttribute("proposta", proposta);
        model.addAttribute("estats", PropostaAdquisicio.EstatProposta.values());

        return "propostes/detall_proposta";
    }

    /**
     * Actualitzem l'estat de la proposta
     * @param idProposta
     * @param estat
     * @param resposta
     * @return 
     */
    @PostMapping("/actualitzar")
    public String actualitzarProposta(@RequestParam("idProposta") int idProposta,
                                      @RequestParam("estat") PropostaAdquisicio.EstatProposta estat,
                                      @RequestParam(value = "resposta", required = false) String resposta) {

        propostaAdquisicioService.actualitzarEstat(idProposta, estat, resposta);

        return "redirect:/dashboard_administrador";
    }


}
