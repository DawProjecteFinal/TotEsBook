package cat.totesbook.controller;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller per al registre d'usuaris.
 */
@Controller
public class RegistreController {

    @Autowired
    private UsuariRepository usuariRepo;

    @GetMapping("/registre")
    public ModelAndView showForm() {
        return new ModelAndView("registre");
    }

    @PostMapping("/registre")
    public ModelAndView doRegister(@RequestParam String nom,
                                   @RequestParam String cognoms,
                                   @RequestParam String email,
                                   @RequestParam("contrasenya") String contrasenya,
                                   @RequestParam(required = false) String telefon) {

        ModelAndView mv = new ModelAndView();

        // Comprova si ja existeix
        if (usuariRepo.getUsuariByEmail(email) != null) {
            mv.setViewName("registre");
            mv.addObject("error", "Aquest correu electrònic ja està registrat.");
            return mv;
        }

        // Hasheja la contrasenya
        String hashed = BCrypt.hashpw(contrasenya, BCrypt.gensalt());

        Usuari nou = new Usuari();
        nou.setNom(nom);
        nou.setCognoms(cognoms);
        nou.setEmail(email);
        nou.setTelefon(telefon);
        nou.setContrasenya(hashed);

        usuariRepo.saveUsuari(nou);

        mv.setViewName("login");
        mv.addObject("info", "Registre correcte. Pots iniciar sessió.");
        return mv;
    }
}
