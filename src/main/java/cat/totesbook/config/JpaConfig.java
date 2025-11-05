package cat.totesbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(
    basePackages = "cat.totesbook",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
)
public class JpaConfig {

    /**
     * Registra un WebClient reutilitzable per crides HTTP (ex. Google Books API)
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    // No definim DataSource, EntityManagerFactory ni TransactionManager ja que
    // Això ja ho gestiona GlassFish via JNDI i el persistence.xml.
}
