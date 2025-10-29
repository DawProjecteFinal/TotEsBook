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
import java.util.List;

@WebServlet("/MostrarLlibres") 
public class MostrarLlibresServlet extends HttpServlet {

    private final LlibreRepository llibreRepo = new LlibreDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            List<Llibre> llistaLlibres = llibreRepo.getAllLlibres(); // Mètode corregit
            request.setAttribute("llibres", llistaLlibres);
            // Forward a la ruta segura
            request.getRequestDispatcher("/WEB-INF/views/mostrarLlibres.jsp").forward(request, response);

        } catch (Exception e) {
            System.err.println("Error a MostrarLlibresServlet: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/paginaInici.jsp").forward(request, response);
            return;
        }
    }
}