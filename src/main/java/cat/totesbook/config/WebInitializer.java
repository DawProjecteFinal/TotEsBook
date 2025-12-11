package cat.totesbook.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import cat.totesbook.config.JpaConfig;
import cat.totesbook.config.WebConfig;
import jakarta.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * Classe que inicialitza l'aplicació web sunstituint el web.xml.
 * 
 * @author Equip TotEsBook
 */
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * Configuració de backend (JPA, Services, Repositories).
     * 
     * @return array amb classes de configuració, JPAConfig i RestTemplateConfig.
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Configuració de backend (JPA, Services, Repositories)
        return new Class<?>[]{JpaConfig.class, RestTemplateConfig.class}; // Afegim RestTemplateConfig
    }

    /**
     * Configuració de Sping MVC (Controllers, ViewResolver).
     * 
     * @return array amb classes de configuració i WebConfig.
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Configuració de Spring MVC (Controllers, ViewResolver)
        return new Class<?>[]{WebConfig.class};
    }

    /**
     * Mapeig del DispatcherServlet.
     * 
     * @return array de rutes ("/").
     */
    @Override
    protected String[] getServletMappings() {
        // Mapeig del DispatcherServlet
        return new String[]{"/"};
    }

    /**
     * Aplicar filtre per forçar UTF-8.
     * 
     * @return filtre d'enconding.
     */
    @Override
    protected Filter[] getServletFilters() {

        // Filtres per forçar UTF-8
        CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(true);

        return new Filter[]{
            encodingFilter

        };
    }

}
