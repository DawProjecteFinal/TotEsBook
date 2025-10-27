/**
 *
 * @author edinsonioc
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.repository.impl.UsuariDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt; // Importem BCrypt

import java.io.IOException;

@WebServlet("/registre")
public class RegistreServlet extends HttpServlet {
    private final UsuariRepository usuariRepo = new UsuariDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String nom = req.getParameter("nom");
        String cognoms = req.getParameter("cognoms");
        String email = req.getParameter("email");
        String contrasenyaPlana = req.getParameter("contrasenya"); // El teu JSP envia 'contrasenya'
        String telefon = req.getParameter("telefon");
        
        Usuari usuariExistent = usuariRepo.getUsuariByEmail(email);
        
        if (usuariExistent != null) {
            // Error: L'email ja existeix
            req.setAttribute("error", "Aquest correu electrònic ja està registrat.");
            req.getRequestDispatcher("/registre.jsp").forward(req, resp);
            return;
        }
        
        // Hashejem la contrasenya
        String contrasenyaHashejada = BCrypt.hashpw(contrasenyaPlana, BCrypt.gensalt());

        Usuari nouUsuari = new Usuari();
        nouUsuari.setNom(nom);
        nouUsuari.setCognoms(cognoms);
        nouUsuari.setEmail(email);
        nouUsuari.setTelefon(telefon);
        nouUsuari.setContrasenya(contrasenyaHashejada); // Guardem el hash
        
        usuariRepo.saveUsuari(nouUsuari);

        resp.sendRedirect("login.jsp");
    }
}

