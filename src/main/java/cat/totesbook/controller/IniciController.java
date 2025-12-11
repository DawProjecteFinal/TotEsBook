package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import cat.totesbook.service.LlibreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador de Spring MVC per a la pàgina d'inici.
 * S'encarrega de carregar les dades necessàries per a la portada.
 * 
 * @author Equip TotEsBook
 */
@Controller
public class IniciController {

    @Autowired
    private LlibreService llibreService;

    /**
     * Gestiona les peticions a l'arrel de l'aplicació ("/" o "/inici").
     *
     * @param model El model de Spring per passar dades a la vista.
     * @return El nom de la vista JSP ("paginaInici").
     */
    @GetMapping({"", "/", "/inici", "/paginaInici"})
    public String mostrarPaginaInici(Model model) {
        
        try {
            // Obtenim tots els llibres del servei
            List<Llibre> totsElsLlibres = llibreService.getAllLlibres();

            // Agafem només els primers 8 per a la portada
            List<Llibre> llibresDestacats = totsElsLlibres.stream()
                                                         .limit(8)
                                                         .collect(Collectors.toList());

            model.addAttribute("llibres", llibresDestacats); 
            
        } catch (Exception e) {
            System.err.println("Error a IniciController en carregar llibres: " + e.getMessage());
            model.addAttribute("llibres", List.of()); // Passem una llista buida
            model.addAttribute("errorCarregantLlibres", "No s'han pogut carregar les novetats.");
        }
        return "paginaInici"; 
    }
}
