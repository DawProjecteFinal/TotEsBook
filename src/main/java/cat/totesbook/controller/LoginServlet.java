/**
 *
 * @author edinsonioc
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Agent;
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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuariRepository usuariRepo = new UsuariDAO();
    private final AgentRepository agentRepo = new AgentDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String email = req.getParameter("email");
        String contrasenyaPlana = req.getParameter("contrasenya"); // El teu JSP envia 'contrasenya'

        // 1. Intentem loguejar com a Agent
        Agent agent = agentRepo.getAgentByEmailAndContrasenya(email, contrasenyaPlana);
        
        if (agent != null) {
            // ÈXIT COM A AGENT
            SessioUsuari sessio = new SessioUsuari(agent);
            
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("sessioUsuari", sessio);

            if (sessio.getRol() == cat.totesbook.domain.Rol.ADMIN) {
                req.getRequestDispatcher("/WEB-INF/views/dashboard_administrador.jsp").forward(req, resp); 
            } else {
                req.getRequestDispatcher("/WEB-INF/views/dashboard_bibliotecari.jsp").forward(req, resp);
            }
            return; 
        }
        
        // 2. Si no és Agent, intentem loguejar com a Usuari
        Usuari usuari = usuariRepo.getUsuariByEmailAndContrasenya(email, contrasenyaPlana);
        
        if (usuari != null) {
            // ÈXIT COM A USUARI
            SessioUsuari sessio = new SessioUsuari(usuari);
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("sessioUsuari", sessio);
            
            req.getRequestDispatcher("/WEB-INF/views/dashboard_usuari.jsp").forward(req, resp);
            return;
        }

        // 3. Error
        req.setAttribute("error", "Email o contrasenya incorrectes.");
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        return;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
