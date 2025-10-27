/**
 *
 * @author edinsonioc
 */
package cat.totesbook.controller;

import cat.totesbook.domain.Llibre;
import cat.totesbook.repository.LlibreRepository;
import cat.totesbook.repository.impl.LlibreDAO; // Importem la implementació (el DAO)
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Aquest Servlet s'encarrega de recollir TOTS els llibres de la BBDD de TotEsBook
 * i passar-los a la vista 'mostrarLlibres.jsp' (el catàleg).
 */
@WebServlet("/cataleg") // Aquesta és la URL pública per veure el catàleg
public class MostrarLlibresServlet extends HttpServlet {

    // Instanciem la implementació real (el DAO)
    private final LlibreRepository llibreRepo = new LlibreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // 1. Cridem al repositori per obtenir els llibres
            //    (Aquest és el nom de mètode corregit que hi ha al DAO)
            List<Llibre> llistaLlibres = llibreRepo.getAllLlibres();

            // 2. Guardem la llista a la 'request' perquè el JSP la pugui llegir
            request.setAttribute("llibres", llistaLlibres);

            // 3. Enviem (fem un forward) a la petició al fitxer JSP segur
            request.getRequestDispatcher("/WEB-INF/views/mostrarLlibres.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            // En cas d'error, redirigim a l'inici
            response.sendRedirect("paginaInici.jsp");
        }
    }
}