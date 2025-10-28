/**
 *
 * @author edinsonioc
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.AgentRepository;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.repository.impl.AgentDAO;
import cat.totesbook.repository.impl.UsuariDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Servlet per carregar les llistes d'Usuaris i Agents
 * i mostrar-les a la pàgina 'mostrarUsuaris.jsp'.
 * Només accessible per a l'ADMIN.
 */
@WebServlet("/mostrarUsuaris")
public class MostrarUsuarisServlet extends HttpServlet {

    private final UsuariRepository usuariRepo = new UsuariDAO();
    private final AgentRepository agentRepo = new AgentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // --- Comprovació de Seguretat ---
        HttpSession session = request.getSession(false);
        SessioUsuari sessioUsuari = (session != null) ? (SessioUsuari) session.getAttribute("sessioUsuari") : null;
        
        // Si no està loguejat o no és ADMIN, redirigim a l'inici
        if (sessioUsuari == null || sessioUsuari.getRol() != Rol.ADMIN) {
            request.getRequestDispatcher("/WEB-INF/views/paginaInici.jsp").forward(request, response);
            return;
        }
        // --- Fi Comprovació Seguretat ---

        try {
            // 1. Obtenim les llistes des dels DAOs
            List<Usuari> llistaUsuaris = usuariRepo.getAllUsuaris();
            List<Agent> llistaAgents = agentRepo.getAllAgents();

            // 2. Desem les llistes a la request per al JSP
            request.setAttribute("llistaUsuaris", llistaUsuaris);
            request.setAttribute("llistaAgents", llistaAgents);

            // 3. Fem forward al JSP corresponent (dins de WEB-INF)
            request.getRequestDispatcher("/WEB-INF/views/mostrarUsuaris.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            System.err.println("Error a MostrarUsuarisServlet: " + e.getMessage());
            // e.printStackTrace(); // Considera logging formal
            // En cas d'error, podríem redirigir a una pàgina d'error o a l'inici
            request.getRequestDispatcher("/WEB-INF/views/paginaInici.jsp").forward(request, response);
            return;        
        }
    }
}

