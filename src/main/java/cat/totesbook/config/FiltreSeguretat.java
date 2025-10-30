/**
 *
 * @author edinsonioc
 */
package cat.totesbook.config;

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
import java.util.Set; // Importem Set

@WebFilter("/*")
public class FiltreSeguretat implements Filter {

    // Camins protegits i rols permesos
    private static final Map<String, List<Rol>> caminsProtegits = new HashMap<>();
    
    // Conjunt de camins públics per a una cerca més ràpida
    private static final Set<String> caminsPublics = Set.of(
        "/login.jsp", "/login", "/registre.jsp", "/registre", 
        "/paginaInici.jsp", "/fitxa_llibre.jsp", "/llibre", 
        "/", "/logout", "/recuperarPass.jsp",
        // Rutes públiques del catàleg
        "/cataleg", "/mostrarLlibres"
    );
    
    // Conjunt de camins només per a convidats (no loguejats)
    private static final Set<String> caminsConvidat = Set.of(
        "/login.jsp", "/login", "/registre.jsp", "/registre", "/recuperarPass.jsp"
    );

    @Override
    public void init(FilterConfig filterConfig) {
        // Definim permisos
        caminsProtegits.put("/dashboard_administrador.jsp", List.of(Rol.ADMIN));
        caminsProtegits.put("/canviRol", List.of(Rol.ADMIN)); // Protegim el servlet de canvi de rol

        caminsProtegits.put("/dashboard_bibliotecari.jsp", List.of(Rol.ADMIN, Rol.BIBLIOTECARI));

        caminsProtegits.put("/dashboard_usuari.jsp", List.of(Rol.ADMIN, Rol.BIBLIOTECARI, Rol.USUARI));
        // Afegeix aquí altres com /prestar, /reservar si només són per a loguejats
        // caminsProtegits.put("/prestar", List.of(Rol.ADMIN, Rol.BIBLIOTECARI, Rol.USUARI));
        // caminsProtegits.put("/reservar", List.of(Rol.ADMIN, Rol.BIBLIOTECARI, Rol.USUARI));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String path = req.getServletPath();
        String contextPath = req.getContextPath(); // Obtenim el context path

        // Obtenim la sessió (si existeix)
        SessioUsuari sessioUsuari = (session != null) ? (SessioUsuari) session.getAttribute("sessioUsuari") : null;

        // 1. Comprovem si és un recurs estàtic (CSS, JS, Imatges)
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/img/") || path.startsWith("/assets/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Si l'usuari està loguejat i intenta accedir a una pàgina de convidat, el redirigim
        if (sessioUsuari != null && caminsConvidat.contains(path)) {
            res.sendRedirect(contextPath + "/paginaInici.jsp");
            return;
        }

        // 3. Comprovem si el camí està a la llista de públics
        if (caminsPublics.contains(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // 4. El camí no és estàtic ni públic. Comprovem si està protegit.
        List<Rol> rolsPermesos = null;
        // Iterem per si hem definit camins amb wildcard (encara que no n'hem fet servir)
        for (Map.Entry<String, List<Rol>> entry : caminsProtegits.entrySet()) {
            if (path.equals(entry.getKey()) || path.startsWith(entry.getKey() + "/")) { // Comprova coincidència exacta o inici
                rolsPermesos = entry.getValue();
                break;
            }
        }

        // 5. Si no està a la llista de protegits, per defecte, el deixem passar? 
        //    O millor denegar? Per seguretat, millor denegar si no està explícitament permès.
        //    Però per simplicitat ara, si no està a caminsProtegits, el deixem passar.
        if (rolsPermesos == null) { 
             System.out.println("ADVERTÈNCIA FiltreSeguretat: Camí no protegit explícitament: " + path);
            chain.doFilter(request, response); 
            return;
        }

        // 6. El camí és protegit. Comprovem la sessió i el rol.
        if (sessioUsuari == null) {
            // No loguejat, redirigim al login
            res.sendRedirect(contextPath + "/login.jsp");
        } else {
            Rol rolUsuari = sessioUsuari.getRol(); 
            if (rolsPermesos.contains(rolUsuari)) {
                // Té permís
                chain.doFilter(request, response); 
            } else {
                // Loguejat, però rol incorrecte. Redirigim a l'inici.
                 System.out.println("Accés denegat per rol. Usuari: " + sessioUsuari.getEmail() + ", Rol: " + rolUsuari + ", Camí: " + path);
                res.sendRedirect(contextPath + "/paginaInici.jsp");
            }
        }
    }
    
    @Override
    public void destroy() { }
}
