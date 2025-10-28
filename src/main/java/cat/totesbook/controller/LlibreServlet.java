/**
 *
 * @author edinsonioc
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.LlibreRepository;
import cat.totesbook.repository.impl.LlibreDAO; 

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/llibre")
public class LlibreServlet extends HttpServlet {
    
    // Instanciem el DAO directament
    private final LlibreRepository llibreRepository = new LlibreDAO();

    public LlibreServlet() {
        // Constructor buit o inicialització si no fas servir DAO directe
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String isbn = request.getParameter("isbn");
        if (isbn == null || isbn.trim().isEmpty()) {
             request.getRequestDispatcher("/WEB-INF/views/paginaInici.jsp").forward(request, response); // Redirigim si no hi ha ISBN
             return;
        }
        
        try {
            Optional<Llibre> optionalLlibre = llibreRepository.getLlibreByIsbn(isbn); // Mètode corregit
            
            if (optionalLlibre.isPresent()) {
                Llibre llibreTrobat = optionalLlibre.get();
                request.setAttribute("llibre", llibreTrobat);
                // Forward a la ruta segura dins de WEB-INF
                request.getRequestDispatcher("/WEB-INF/views/fitxa_llibre.jsp").forward(request, response);
            
            } else {
                // Llibre no trobat
                 request.setAttribute("errorLlibre", "No s'ha trobat cap llibre amb l'ISBN " + isbn);
                 // Podem mostrar un error a la pàgina d'inici o a una pàgina d'error específica
                 request.getRequestDispatcher("/WEB-INF/views/paginaInici.jsp").forward(request, response); 
                 // O redirigir: response.sendRedirect(request.getContextPath() + "/paginaInici.jsp");
            }
        } catch (Exception e) {
             System.err.println("Error a LlibreServlet: " + e.getMessage());
            // e.printStackTrace();
             request.getRequestDispatcher("/WEB-INF/views/paginaInici.jsp").forward(request, response);// Redirigim a l'inici en cas d'error
        }
    }
}