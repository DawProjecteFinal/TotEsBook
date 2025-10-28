/**
 *
 * @author edinsonioc
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Agent; 
import cat.totesbook.domain.Rol; 
import cat.totesbook.repository.AgentRepository; 
import cat.totesbook.repository.impl.AgentDAO; 
// Importa UsuariRepository i UsuariDAO si implementes l'ascens/descens
// import cat.totesbook.repository.UsuariRepository;
// import cat.totesbook.repository.impl.UsuariDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // Per a comprovació de seguretat

import java.io.IOException;

@WebServlet("/canviRol")
public class CanviRolServlet extends HttpServlet {

    private final AgentRepository agentRepo = new AgentDAO();
    // private final UsuariRepository usuariRepo = new UsuariDAO(); // Descomenta si cal

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // --- Comprovació de Seguretat Bàsica ---
        HttpSession session = request.getSession(false);
        cat.totesbook.domain.SessioUsuari sessioUsuari = (session != null) ? (cat.totesbook.domain.SessioUsuari) session.getAttribute("sessioUsuari") : null;
        
        // Només l'ADMIN pot canviar rols
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
             response.sendRedirect(request.getContextPath() + "/WEB-INF/views/paginaInici.jsp");
             return;
        }
        // --- Fi Comprovació Seguretat ---

        String feedbackMessage = null; // Missatge per mostrar a l'admin
        String messageType = "danger"; // Tipus de missatge (danger, success)

        try {
            int idCompte = Integer.parseInt(request.getParameter("idCompte"));
            String nouRolString = request.getParameter("nouRol"); 
            String tipusCompte = request.getParameter("tipusCompte"); 

            if (nouRolString == null || tipusCompte == null) {
                 throw new IllegalArgumentException("Paràmetres 'nouRol' o 'tipusCompte' no rebuts.");
            }

            Rol nouRol = Rol.valueOf(nouRolString);

            System.out.println("Canviant rol per a " + tipusCompte + " ID: " + idCompte + " al rol: " + nouRol);

            if ("AGENT".equals(tipusCompte)) {
                if (nouRol == Rol.ADMIN || nouRol == Rol.BIBLIOTECARI) {
                    Agent.TipusAgent nouTipusAgent = (nouRol == Rol.ADMIN) ? Agent.TipusAgent.administrador : Agent.TipusAgent.bibliotecari;
                    agentRepo.updateAgentTipus(idCompte, nouTipusAgent); // Mètode corregit
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
             System.err.println("Error a CanviRolServlet: ID de compte invàlid.");
             feedbackMessage = "Error: L'ID del compte no és vàlid.";
             // e.printStackTrace();
        } catch (IllegalArgumentException e) {
             System.err.println("Error a CanviRolServlet: Rol o Tipus de compte invàlid/nul.");
             feedbackMessage = "Error: Rol o tipus de compte rebut no és vàlid.";
             // e.printStackTrace();
        } catch (Exception e) {
             System.err.println("Error inesperat a CanviRolServlet: " + e.getMessage());
             feedbackMessage = "Error inesperat processant la sol·licitud.";
            // e.printStackTrace();
        }
        
        // Guardem el missatge a la sessió per mostrar-lo al dashboard
        if (feedbackMessage != null && session != null) {
             session.setAttribute("feedbackMessage", feedbackMessage);
             session.setAttribute("messageType", messageType);
        }

        // Redirigim sempre de tornada al panell d'admin
        response.sendRedirect(request.getContextPath() + "/dashboard_administrador.jsp");
    }
}