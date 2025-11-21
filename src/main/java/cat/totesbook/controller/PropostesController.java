package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import cat.totesbook.service.GoogleBooksService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author equip totesbook
 */
@Controller
@RequestMapping("/propostes")
public class PropostesController {
    @Autowired
    GoogleBooksService googleBooksService;

    /**
     * Retorna el formulari per a buscar el llibre de la proposta
     * @return 
     */
    @GetMapping("/formulari_proposta")
    public String mostrarFormulariCerca() {
        return "propostes/formulari_proposta";
    }

    /**
     * Recupera titol, autor i isbn del formulari i fa la consulata a la Api retornant
     * tota la llista de resultats
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
            return "formulari_proposta";
        }

        List<Llibre> resultats = googleBooksService.cercarLlibres(titol, autor, isbn);

        model.addAttribute("resultats", resultats);
        
        // Isbn exemple del Quijote: 978-8471664570
        return "propostes/proposta_resultats";  
    }

}
