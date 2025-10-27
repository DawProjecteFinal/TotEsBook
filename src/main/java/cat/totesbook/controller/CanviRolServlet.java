/**
 *
 * @author edinsonioc
 */

package cat.totesbook.controller;

import cat.totesbook.domain.Agent; // El teu model Agent
import cat.totesbook.domain.Rol; // El teu Enum de Rols
import cat.totesbook.repository.AgentRepository; // La interfície
import cat.totesbook.repository.impl.AgentDAO; // La implementació

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



/**
 * Aquest Servlet gestiona la petició del formulari de dashboard_admin.jsp
 * per canviar el rol d'un Usuari o un Agent.
 */
@WebServlet("/canviRol")
public class CanviRolServlet extends HttpServlet {

    // Necessitem el repositori per actualitzar la BBDD
    private final AgentRepository agentRepo = new AgentDAO();
    // TODO: També necessitaràs el UsuariRepository si vols "ascendir" un Usuari a Agent

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Llegim els paràmetres del formulari del dashboard_admin.jsp
            int idCompte = Integer.parseInt(request.getParameter("idCompte"));
            String nouRolString = request.getParameter("nouRol"); // "USUARI", "BIBLIOTECARI", "ADMIN"
            String tipusCompte = request.getParameter("tipusCompte"); // "AGENT" o "USUARI"

            // Convertim el String al nostre Enum 'Rol'
            Rol nouRol = Rol.valueOf(nouRolString);

            // ----- LÒGICA DE CANVI DE ROL -----
            
            if ("AGENT".equals(tipusCompte)) {
                // El compte és un Agent.
                
                if (nouRol == Rol.ADMIN || nouRol == Rol.BIBLIOTECARI) {
                    // Cas Fàcil: Actualitzem el 'tipus' a la taula Agents
                    
                    // Convertim el Rol del sistema al TipusAgent de la BBDD
                    Agent.TipusAgent nouTipusAgent;
                    if (nouRol == Rol.ADMIN) {
                        nouTipusAgent = Agent.TipusAgent.administrador;
                    } else {
                        nouTipusAgent = Agent.TipusAgent.bibliotecari;
                    }
                    
                    // Cridem al mètode del DAO (que crearem a sota)
                    agentRepo.updateAgentTipus(idCompte, nouTipusAgent);
                    
                } else {
                    // Cas Difícil: "Degradar" un Agent a Usuari
                    // Això implicaria esborrar l'Agent i crear un Usuari nou.
                    // TODO: Implementar la lògica de "degradació" (agentRepo.deleteById... usuariRepo.save...)
                    System.out.println("LOG: Degradació d'Agent a Usuari no implementada.");
                }
                
            } else if ("USUARI".equals(tipusCompte)) {
                // El compte és un Usuari (lector).
                
                if (nouRol == Rol.ADMIN || nouRol == Rol.BIBLIOTECARI) {
                    // Cas Difícil: "Ascendir" un Usuari a Agent
                    // Això implicaria esborrar l'Usuari i crear un Agent nou.
                    // TODO: Implementar la lògica d'"ascens" (usuariRepo.deleteById... agentRepo.save...)
                    System.out.println("LOG: Ascens d'Usuari a Agent no implementat.");
                }
                // Si el nou rol és USUARI, no fem res (ja ho és).
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Un cop processat, redirigim de tornada al panell d'admin
        response.sendRedirect("dashboard_admin.jsp");
    }
}