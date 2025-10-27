/**
 *
 * @author edinsonioc
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
// <-- CANVI: Importem la implementació (el DAO), no només la interfície
import cat.totesbook.repository.LlibreRepository;
import cat.totesbook.repository.impl.LlibreDAO; // Assegura't que aquesta és la ruta al teu DAO

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional; // <-- L'import d'Optional ja hi era, però ara l'utilitzarem bé

// URL dinàmica (ex: /llibre?isbn=...)
@WebServlet("/llibre")
public class LlibreServlet extends HttpServlet {
    
    private final LlibreRepository llibreRepository;

    public LlibreServlet() {
        // <-- CANVI: Instanciem la implementació REAL (el DAO), 
        // no una classe anònima falsa.
        this.llibreRepository = new LlibreDAO(); 
        
        // La teva implementació antiga (que llançava errors) s'ha eliminat.
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String isbn = request.getParameter("isbn");
            
            // 1. Obtenim l'Optional<Llibre> del repositori
            Optional<Llibre> optionalLlibre = llibreRepository.getLlibreByIsbn(isbn); 
            
            // <-- CANVI: Comprovem un Optional correctament
            if (optionalLlibre.isPresent()) {
                
                // 2. Extraiem el llibre de dins de l'Optional
                Llibre llibreTrobat = optionalLlibre.get();
                
                // 3. Passem l'objecte Llibre (NO l'Optional) al JSP
                request.setAttribute("llibre", llibreTrobat);
                
                // <-- CANVI: Fem el forward a la ruta segura dins de WEB-INF
                request.getRequestDispatcher("/WEB-INF/views/fitxa_llibre.jsp").forward(request, response);
            
            } else {
                // Llibre no trobat
                response.sendRedirect("paginaInici.jsp");
            }
        } catch (Exception e) {
            // En cas d'error (ex: ISBN invàlid), redirigim
            e.printStackTrace();
            response.sendRedirect("paginaInci.jsp");
        }
    }
}