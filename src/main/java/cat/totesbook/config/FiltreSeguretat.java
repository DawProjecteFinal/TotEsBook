/**
 *
 * @author edinsonioc
 */

package cat.totesbook.config; // <-- Paquet actualitzat

import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


/**
 * Filtre de Seguretat (Punts 6 i 7)
 * Controla l'accés a les rutes protegides basant-se en el rol
 * de l'usuari guardat a la sessió.
 */
@WebFilter("/*")
public class FiltreSeguretat implements Filter { // <-- Nom de la classe actualitzat

    // Mapa per definir rutes protegides i rols permesos
    private static final Map<String, List<Rol>> caminsProtegits = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) {
        // Definim els permisos per a cada ROL del nostre enum
        
        // Només ADMIN pot veure això
        caminsProtegits.put("/dashboard_admin.jsp", List.of(Rol.ADMIN));

        // ADMIN i BIBLIOTECARI poden veure això
        caminsProtegits.put("/dashboard_bibliotecario.jsp", List.of(Rol.ADMIN, Rol.BIBLIOTECARI));

        // TOTS els rols loguejats poden veure això
        caminsProtegits.put("/dashboard_usuario.jsp", List.of(Rol.ADMIN, Rol.BIBLIOTECARI, Rol.USUARI));
        
        // (Afegeix aquí altres pàgines o servlets a protegir)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String path = req.getServletPath();

        // Llista de rutes públiques (tothom hi pot accedir)
        boolean esCamiPublic = path.startsWith("/css/") || path.startsWith("/js/") ||
                               path.startsWith("/img/") || path.equals("/login.jsp") ||
                               path.equals("/login") || path.equals("/registre.jsp") ||
                               path.equals("/registre") || path.equals("/paginaInici.jsp") ||
                               path.equals("/fitxa_llibre.jsp") || path.equals("/llibre") ||
                               path.equals("/") || path.equals("/logout") || 
                               path.equals("/recuperarPass.jsp");

        if (esCamiPublic) {
            chain.doFilter(request, response); // És públic, deixem passar
            return;
        }

        List<Rol> rolsPermesos = caminsProtegits.get(path);
        
        if (rolsPermesos == null) {
            chain.doFilter(request, response); // No està a la llista de protegits, deixem passar
            return;
        }

        // El camí és protegit. Comprovem la sessió.
        SessioUsuari sessio = (session != null) ? (SessioUsuari) session.getAttribute("sessioUsuari") : null;

        if (sessio == null) {
            // Punt 7: Redirigir si no té permisos (no loguejat)
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            Rol rolUsuari = sessio.getRol(); // Obtenim el Rol del nostre objecte de sessió
            
            if (rolsPermesos.contains(rolUsuari)) {
                chain.doFilter(request, response); // Permís concedit
            } else {
                // Punt 7: Loguejat, però rol incorrecte. Redirigim a l'inici.
                res.sendRedirect(req.getContextPath() + "/paginaInici.jsp");
            }
        }
    }
    
    @Override
    public void destroy() {
        // Mètode de destrucció del filtre
    }
}