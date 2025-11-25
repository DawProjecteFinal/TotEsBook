/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import cat.totesbook.config.JpaConfig;
import cat.totesbook.config.WebConfig;
import jakarta.servlet.Filter;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Configuració de backend (JPA, Services, Repositories)
        return new Class<?>[] { JpaConfig.class, RestTemplateConfig.class }; // Afegim RestTemplateConfig
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Configuració de Spring MVC (Controllers, ViewResolver)
        return new Class<?>[] { WebConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        // Mapeig del DispatcherServlet
        return new String[] { "/" };
    }

    // --- 2. AFEGIR AQUEST MÈTODE SENCER ---
    @Override
    protected Filter[] getServletFilters() {
        // Registrem el nostre filtre de seguretat
        // Com que l'hem anotat amb @Component, Spring ja el gestiona.
        // Però si no el gestionés, l'hauríem d'instanciar aquí:
        // return new Filter[] { new FiltreSeguretat() };
        
        // Encara que Spring el gestiona, registrar-lo aquí 
        // assegura que s'aplica correctament a la cadena de filtres del Servlet.
        //FiltreSeguretat filtreSeguretat = new FiltreSeguretat();
        //return new Filter[] { filtreSeguretat };
        return null;
    }
    // --- FI DEL NOU MÈTODE ---
}

