/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.repository.UsuariRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RecuperarPassController {
    
    @Autowired
    private UsuariRepository usuariRepository;

    @Autowired
    private AgentRepository agentRepository;
    
    @GetMapping("/recuperarPass")
    public String mostrarFormulariRecuperacio(){
        return "recuperarPass";
    }
    
    @PostMapping("/recuperarPass")
    public String processarRecuperacio(@RequestParam("email") String email, Model model){
        Usuari usuari = usuariRepository.getUsuariByEmail(email);
        Agent agent = agentRepository.getAgentByEmail(email);
        
        if (usuari == null && agent == null){
            model.addAttribute("error", "Aquest correu no existeix a TotEsBook. "+"Si us plau, registra't o comprova que l'has escrit correctament.");
        } else {
            model.addAttribute("missatge", "Hem registrat la teva sol·licitud de recuperació. " + "En un entorn real t'enviaríem un correu amb les instruccions. " + 
                    "Com que és un entorn educatiu, contacta amb la biblioteca o l'administrador perquè t'ajudin a restablir la contrasenya.");
        }
        
        return "recuperarPass";
    }
    
    
}
