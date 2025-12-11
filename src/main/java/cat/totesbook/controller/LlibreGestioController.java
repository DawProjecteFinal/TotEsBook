package cat.totesbook.controller;

import cat.totesbook.domain.Biblioteca;
import cat.totesbook.domain.BibliotecaLlibre;
import cat.totesbook.domain.Llibre;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.service.BibliotecaLlibreService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.GoogleBooksService;
import cat.totesbook.service.LlibreService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controlador que s'encarrega de la gestió dels llibres (afegir, editar i 
 * eliminar).
 * 
 * @author EquipTotEsBook
 */
@Controller
@RequestMapping("/gestio/llibres")
public class LlibreGestioController {

    private final LlibreService llibreService;
    private final BibliotecaService bibliotecaService;
    private final BibliotecaLlibreService bibliotecaLlibreService;
    private final GoogleBooksService googleBooksService;

    /**
     * Constructor del controlador de gestió de llibres.
     * 
     * @param llibreService llibreService
     * @param bibliotecaService bibliotecaService
     * @param bibliotecaLlibreService bibliotecaLlibreService
     * @param googleBooksService googleBooksService
     */
    public LlibreGestioController(LlibreService llibreService,
            BibliotecaService bibliotecaService,
            BibliotecaLlibreService bibliotecaLlibreService,
            GoogleBooksService googleBooksService) {
        this.llibreService = llibreService;
        this.bibliotecaService = bibliotecaService;
        this.bibliotecaLlibreService = bibliotecaLlibreService;
        this.googleBooksService = googleBooksService;
    }

    /**
     * Eliminar el llibre indicat amb l'ISBN.
     * 
     * @param isbn L'ISBN del llibre.
     * @param session La sessió HTTP per comprovar l'autorització.
     * @return Un ModelAndView que redirigeix al dashboard de l'administrador.
     */
    @PostMapping("/{isbn}/eliminar")
    public ModelAndView eliminarLlibre(@PathVariable("isbn") String isbn,
            HttpSession session) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/dashboard_administrador")
                    .addObject("error", "No tens permisos per eliminar llibres.");
        }

        llibreService.deleteLlibre(isbn);

        return new ModelAndView("redirect:/dashboard_administrador")
                .addObject("success", "Llibre eliminat correctament.");
    }

    /**
     * Editar la informació del llibre.
     * 
     * @param isbn L'ISBN del llibre.
     * @param session La sessió HTTP per comprovar l'autorització.
     * @return Un ModelAndView que mostra la vista "llibres/editar_llibre".
     */
    @GetMapping("/{isbn}/editar")
    public ModelAndView mostrarFormEditar(@PathVariable("isbn") String isbn,
            HttpSession session) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/dashboard_administrador");
        }

        Llibre llibre = llibreService.getLlibreByIsbn(isbn).orElse(null);
        if (llibre == null) {
            return new ModelAndView("redirect:/dashboard_administrador");
        }

        List<Biblioteca> biblioteques = bibliotecaService.getAllBiblioteques();

        List<BibliotecaLlibre> relacions = bibliotecaLlibreService.findByLlibre(llibre);
        BibliotecaLlibre relacio = relacions.isEmpty() ? null : relacions.get(0);

        ModelAndView mv = new ModelAndView("llibres/editar_llibre");
        mv.addObject("llibre", llibre);
        mv.addObject("biblioteques", biblioteques);
        mv.addObject("relacio", relacio);
        return mv;
    }

    /**
     * Guarda les modificaions de la biblioteca i els exemplars dels llibres.
     * 
     * @param isbn L'ISBN del llibre.
     * @param session La sessió HTTP per comprovar l'autorització.
     * @param idBiblioteca L'ID de la biblioteca.
     * @param exemplars Nombre d'exemplars.
     * @return Un ModelAndView que redirigeix al dashboard de l'administrador.
     */
    @PostMapping("/{isbn}/editar")
    public ModelAndView guardarEdicioLlibre(@PathVariable("isbn") String isbn,
            HttpSession session,
            int idBiblioteca,
            int exemplars) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }

        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/dashboard_administrador");
        }

        Llibre llibre = llibreService.getLlibreByIsbn(isbn).orElse(null);
        if (llibre == null) {
            return new ModelAndView("redirect:/dashboard_administrador");
        }

        Biblioteca biblioteca = bibliotecaService.findById(idBiblioteca).orElse(null);
        if (biblioteca == null) {
            return new ModelAndView("redirect:/dashboard_administrador");
        }

        List<BibliotecaLlibre> relacions = bibliotecaLlibreService.findByLlibre(llibre);

        if (relacions.isEmpty()) {
            bibliotecaLlibreService.afegirLlibre(biblioteca, llibre, exemplars, exemplars);
        } else {
            BibliotecaLlibre relacio = relacions.get(0);
            bibliotecaLlibreService.actualitzarRelacio(relacio, biblioteca, exemplars);
        }

        return new ModelAndView("redirect:/dashboard_administrador");
    }

    /**
     * Afegir llibre a biblioteca.
     * 
     * @param session La sessió HTTP per comprovar l'autorització.
     * @return Un ModelAndView que mostra la vista "llibres/afegir_llibre".
     */
    @GetMapping("/afegir")
    public ModelAndView mostrarFormAfegir(HttpSession session) {
        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }
        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/dashboard_administrador");
        }

        List<Biblioteca> biblioteques = bibliotecaService.getAllBiblioteques();

        ModelAndView mv = new ModelAndView("llibres/afegir_llibre");
        mv.addObject("biblioteques", biblioteques);
        return mv;
    }

    /**
     * Processa les modificacions que s'han fet al formulari. 
     * 
     * @param session La sessió HTTP per comprovar l'autorització.
     * @param isbn L'ISBN del llibre.
     * @param idBiblioteca L'ID de la biblioteca.
     * @param exemplars Nombre d'exemplars.
     * @return Un ModelAndView que mostra la vista "llibres/afegir_llibre" o 
     *         una redirecció si l'usuari no ha iniciat la sessió.
     */
    @PostMapping("/afegir")
    public ModelAndView processarAfegirLlibre(HttpSession session,
            String isbn,
            int idBiblioteca,
            int exemplars) {

        SessioUsuari sessio = (SessioUsuari) session.getAttribute("sessioUsuari");
        if (sessio == null) {
            return new ModelAndView("redirect:/login");
        }
        if (sessio.getRol() != Rol.ADMIN) {
            return new ModelAndView("redirect:/dashboard_administrador");
        }

        ModelAndView mv = new ModelAndView("llibres/afegir_llibre");

        List<Biblioteca> biblioteques = bibliotecaService.getAllBiblioteques();
        mv.addObject("biblioteques", biblioteques);

        if (isbn == null || isbn.isBlank()) {
            mv.addObject("error", "Has d'indicar un ISBN.");
            return mv;
        }

        String cleanIsbn = isbn.trim();

        Llibre llibre = llibreService.getLlibreByIsbn(cleanIsbn).orElse(null);

        // 2. Si no existeix, el demanem a l'API de Google Books
        if (llibre == null) {
            Optional<Llibre> optApi = googleBooksService.getLlibreByIsbn(cleanIsbn);

            if (optApi.isEmpty()) {
                mv.addObject("error", "No s'ha trobat cap llibre amb aquest ISBN a Google Books.");
                return mv;
            }

            llibre = optApi.get();
            llibreService.guardarLlibre(llibre);
        }

        Biblioteca biblioteca = bibliotecaService.findById(idBiblioteca).orElse(null);
        if (biblioteca == null) {
            mv.addObject("error", "La biblioteca seleccionada no existeix.");
            return mv;
        }

        if (exemplars <= 0) {
            mv.addObject("error", "El nombre d'exemplars ha de ser major que 0.");
            return mv;
        }

        bibliotecaLlibreService.afegirLlibre(biblioteca, llibre, exemplars, exemplars);

        mv.addObject("llibreAfegit", llibre);
        mv.addObject("success", "Llibre afegit correctament a la biblioteca.");
        return mv;
    }
}
