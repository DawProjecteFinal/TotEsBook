/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import cat.totesbook.config.JpaConfig;
import cat.totesbook.config.WebConfig;
import jakarta.servlet.Filter;
import org.springframework.web.filter.CharacterEncodingFilter;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Configuració de backend (JPA, Services, Repositories)
        return new Class<?>[]{JpaConfig.class, RestTemplateConfig.class}; // Afegim RestTemplateConfig
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Configuració de Spring MVC (Controllers, ViewResolver)
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // Mapeig del DispatcherServlet
        return new String[]{"/"};
    }

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
