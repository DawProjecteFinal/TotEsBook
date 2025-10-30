package cat.totesbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(
        basePackages = "cat.totesbook",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
)

public class WebConfig implements WebMvcConfigurer {

    /**
     *
     * @return
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");  // carpeta correcta amb les vistes
        resolver.setSuffix(".jsp");
        return resolver;
    }

    /**
     * Mètode que li diu al DispatcherServlet que no intercepti les rutes dels
     * recursos estàtics (com /css/, /js/, /images/).
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configuració principal per /assets/** (css, images, etc.)
        registry.addResourceHandler("/assets/**")
                .addResourceLocations(
                    "/assets/",                    // Per recursos públics
                    "classpath:/static/assets/",   // Per recursos en el classpath
                    "file:src/main/webapp/assets/" // Per desenvolupament local
                )
                .setCachePeriod(0);  // Deshabilitar cache durant desenvolupament
    }

}
