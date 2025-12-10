package cat.totesbook.controller;

import cat.totesbook.domain.Agent; 
import cat.totesbook.domain.Rol; 
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.repository.AgentRepository; 
// Importa UsuariRepository si implementes l'ascens/descens
// import cat.totesbook.repository.UsuariRepository;

import jakarta.servlet.http.HttpSession; // Per a comprovació de seguretat
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Per passar dades (errors/feedback) a la vista
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador Spring MVC per gestionar el canvi de rols des del panell d'administració.
 * Substitueix l'antic CanviRolServlet.
 * 
 * @author Equip TotEsBook
 */
@Controller
public class CanviRolController {

    // 1. Injecció de dependències
    // Spring injectarà automàticament la implementació (AgentDAO)
    @Autowired
    private AgentRepository agentRepo;
    
    // @Autowired
    // private UsuariRepository usuariRepo; // Descomenta si implementes l'ascens

    /**
     * Processa la sol·licitud POST des del formulari de 'dashboard_admin.jsp'
     * per canviar el rol d'un compte.
     * @param idCompte L'ID de l'Usuari o Agent a modificar.
     * @param nouRolString El nou rol seleccionat (com a String: "ADMIN", "BIBLIOTECARI", "USUARI").
     * @param tipusCompte El tipus de compte actual ("AGENT" o "USUARI").
     * @param session La HttpSession, per verificar els permisos de l'administrador.
     * @param model El Model, per retornar missatges de feedback a la vista.
     * @return El nom de la vista JSP a la qual es farà "forward" (en aquest cas, 'dashboard_admin').
     */
    @PostMapping("/canviRol")
    public String processarCanviRol(
            @RequestParam("idCompte") int idCompte,
            @RequestParam("nouRol") String nouRolString,
            @RequestParam("tipusCompte") String tipusCompte,
            HttpSession session,
            Model model) {
        
        // --- Comprovació de Seguretat ---
        SessioUsuari sessioUsuari = (SessioUsuari) session.getAttribute("sessioUsuari");
        
        // Només l'ADMIN pot canviar rols
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
            // Si no és admin, el fem fora (retornem la vista d'inici)
            return "paginaInici"; // El ViewResolver ho traduirà a /WEB-INF/views/paginaInici.jsp
        }
        // --- Fi Comprovació Seguretat ---

        String feedbackMessage = null; // Missatge per mostrar a l'admin
        String messageType = "danger"; // Tipus de missatge (danger, success)

        try {
            Rol nouRol = Rol.valueOf(nouRolString); // Converteix String a Enum

            System.out.println("Canviant rol per a " + tipusCompte + " ID: " + idCompte + " al rol: " + nouRol);

            if ("AGENT".equals(tipusCompte)) {
                if (nouRol == Rol.ADMIN || nouRol == Rol.BIBLIOTECARI) {
                    // Cas Fàcil: Actualitzem el 'tipus' a la taula Agents
                    Agent.TipusAgent nouTipusAgent = (nouRol == Rol.ADMIN) ? Agent.TipusAgent.administrador : Agent.TipusAgent.bibliotecari;
                    agentRepo.updateAgentTipus(idCompte, nouTipusAgent);
                    
                    feedbackMessage = "Rol de l'agent ID " + idCompte + " actualitzat a " + nouRolString;
                    messageType = "success";
                } else { // Degradació a USUARI
                    feedbackMessage = "ERROR: La degradació d'Agent a Usuari no està implementada.";
                    System.err.println("LOG: Intent de degradació d'Agent ID " + idCompte + " a Usuari.");
                    // TODO: agentRepo.deleteById(idCompte); usuariRepo.save(nouUsuariCreatAPartirDeAgent);
                }
            } else if ("USUARI".equals(tipusCompte)) {
                if (nouRol == Rol.ADMIN || nouRol == Rol.BIBLIOTECARI) { // Ascens a AGENT
                    feedbackMessage = "ERROR: L'ascens d'Usuari a Agent no està implementat.";
                    System.err.println("LOG: Intent d'ascens d'Usuari ID " + idCompte + " a " + nouRolString);
                    // TODO: usuariRepo.deleteById(idCompte); agentRepo.save(nouAgentCreatAPartirDeUsuari);
                }
                // Si nouRol == Rol.USUARI, no fem res.
            }

        } catch (NumberFormatException e) {
             System.err.println("Error a CanviRolController: ID de compte invàlid.");
             feedbackMessage = "Error: L'ID del compte no és vàlid.";
        } catch (IllegalArgumentException e) {
             System.err.println("Error a CanviRolController: Rol o Tipus de compte invàlid/nul.");
             feedbackMessage = "Error: Rol o tipus de compte rebut no és vàlid.";
        } catch (Exception e) {
             System.err.println("Error inesperat a CanviRolController: " + e.getMessage());
             feedbackMessage = "Error inesperat processant la sol·licitud.";
             e.printStackTrace(); // Mostrem la traça completa en cas d'error inesperat
        }
        
        // 2. Passem el feedback al Model (equivalent a request.setAttribute)
        if (feedbackMessage != null) {
            model.addAttribute("feedbackMessage", feedbackMessage);
            model.addAttribute("messageType", messageType);
        }

        // 3. Retornem el nom de la vista (forward)
        // El ViewResolver (a WebConfig) s'encarregarà de trobar:
        // "/WEB-INF/views/dashboard_admin.jsp"
        // NOTA: El teu JSP 'dashboard_admin.jsp' HA de recarregar les llistes
        // (com ja fa amb els scriptlets) perquè els canvis es reflecteixin.
        return "dashboard_administrador";
    }
}
