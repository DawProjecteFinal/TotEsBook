/**
 *
 * @author edinsonioc
 */
package cat.totesbook.config;

import cat.totesbook.domain.Rol;
import cat.totesbook.domain.SessioUsuari;
import jakarta.servlet.*;
// import jakarta.servlet.annotation.WebFilter; // <-- JA NO ÉS NECESSARI
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component; // <-- 1. AFEGIR AQUEST IMPORT

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Filtre de Seguretat (Punts 6 i 7)
 * Controla l'accés a les rutes protegides basant-se en el rol
 * de l'usuari guardat a la sessió.
 * Adaptat per a Spring MVC (protegeix URLs de Controladors, no JSPs).
 * Ara és un Bean de Spring (@Component).
 */
// @WebFilter("/*") // <-- 2. ELIMINAR O COMENTAR AQUESTA LÍNIA
@Component // <-- 3. AFEGIR AQUESTA ANOTACIÓ
public class FiltreSeguretat implements Filter {

    // Camins protegits (URLs de Controladors) i rols permesos
    private static final Map<String, List<Rol>> caminsProtegits = new HashMap<>();
    
    // Conjunt de camins públics (URLs de Controladors)
    private static final Set<String> caminsPublics = Set.of(
        "/",          // Arrel (IniciController)
        "/paginaInici", // Alias d'IniciController
        "/llibre",    // LlibreController (fitxa de detall)
        "/cataleg",   // LlibreController (llistat)
        "/logout"     // LogoutServlet (o controlador)
        // Cal afegir aquí el servlet de cerca si n'hi ha un
    );
    
    // Conjunt de camins només per a convidats (no loguejats)
    private static final Set<String> caminsConvidat = Set.of(
        "/login",     // LoginController (GET i POST)
        "/registre"   // RegistreController (GET i POST)
        // Cal afegir aquí el controlador de recuperar contrasenya
        // "/recuperarPass" 
    );

    @Override
    public void init(FilterConfig filterConfig) {
        // Definim permisos per als controladors protegits
        
        // Només ADMIN
        caminsProtegits.put("/dashboard_administrador", List.of(Rol.ADMIN)); // URL del DashboardAdminController
        caminsProtegits.put("/canviRol", List.of(Rol.ADMIN));         // URL del CanviRolController/Servlet
        caminsProtegits.put("/mostrarUsuaris", List.of(Rol.ADMIN));   // URL del UsuariController (GET)

        // ADMIN i BIBLIOTECARI
        caminsProtegits.put("/dashboard_bibliotecari", List.of(Rol.ADMIN, Rol.BIBLIOTECARI));

        // TOTS els rols loguejats
        caminsProtegits.put("/dashboard_usuari", List.of(Rol.ADMIN, Rol.BIBLIOTECARI, Rol.USUARI));
        
        // Afegeix aquí altres URLs protegides (ex: /ferPrestec, /ferReserva)
        // caminsProtegits.put("/ferPrestec", List.of(Rol.ADMIN, Rol.BIBLIOTECARI, Rol.USUARI));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        String path = req.getServletPath();
        String contextPath = req.getContextPath();

        // Obtenim la sessió (si existeix)
        SessioUsuari sessioUsuari = (session != null) ? (SessioUsuari) session.getAttribute("sessioUsuari") : null;

        // 1. Comprovem si és un recurs estàtic (CSS, JS, Imatges)
        if (path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/img/") || path.startsWith("/assets/")) {
            chain.doFilter(request, response);
            return;
        }

        // 2. Comprovem si és un camí només per a convidats
        if (caminsConvidat.contains(path)) {
            if (sessioUsuari != null) {
                // L'usuari JA està loguejat i intenta anar al login/registre
                res.sendRedirect(contextPath + "/"); // Redirigim a l'inici
                return;
            } else {
                // No està loguejat, el deixem passar al login/registre
                chain.doFilter(request, response);
                return;
            }
        }

        // 3. Comprovem si el camí està a la llista de públics
        if (caminsPublics.contains(path)) {
            chain.doFilter(request, response);
            return;
        }
        
        // 4. El camí no és estàtic, ni de convidat, ni públic. Per tant, és PROTEGIT.
        // Comprovem si l'usuari està loguejat.
        if (sessioUsuari == null) {
            // No està loguejat i intenta accedir a un lloc protegit
            res.sendRedirect(contextPath + "/login"); // Redirigim al controlador de login
            return;
        }

        // 5. L'usuari està loguejat. Comprovem si té el ROL adequat.
        List<Rol> rolsPermesos = null;
        for (Map.Entry<String, List<Rol>> entry : caminsProtegits.entrySet()) {
            if (path.equals(entry.getKey())) {
                rolsPermesos = entry.getValue();
                break;
            }
        }

        if (rolsPermesos == null) {
            // El camí no està a la llista de protegits. (Ex: un 404 o un camí oblidat)
            // Per seguretat, el redirigim a l'inici.
            System.out.println("ADVERTÈNCIA FiltreSeguretat: Camí no definit '" + path + "' accedit per usuari loguejat. Redirigint a l'inici.");
            res.sendRedirect(contextPath + "/");
            return;
        }

        // 6. Comprovem el rol
        Rol rolUsuari = sessioUsuari.getRol();
        if (rolsPermesos.contains(rolUsuari)) {
            // Té permís
            chain.doFilter(request, response);
        } else {
            // Loguejat, però rol incorrecte. Redirigim a l'inici.
            System.out.println("Accés denegat per rol. Usuari: " + sessioUsuari.getEmail() + ", Rol: " + rolUsuari + ", Camí: " + path);
            res.sendRedirect(contextPath + "/");
        }
    }
    
    @Override
    public void destroy() {
        // Mètode de destrucció del filtre
    }
}