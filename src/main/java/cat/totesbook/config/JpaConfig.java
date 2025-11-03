package cat.totesbook.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient; // Aquest bean estava aquí, el mantenim
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuració del context Arrel de Spring (Backend).
 * S'encarrega d'escanejar Serveis, Repositoris i altres Components.
 * S'ha eliminat la configuració de JPA/EntityManager per evitar conflictes
 * amb la connexió JDBC manual (DBConnection.java).
 */
@Configuration
@ComponentScan(
    basePackages = "cat.totesbook", // Escaneja tot el projecte
    // Exclou els Controllers (perquè els gestiona WebConfig)
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class) 
)
//@EnableTransactionManagement // Encara que fem servir JDBC manual, podem gestionar transaccions
public class JpaConfig {

    // Aquest Bean de WebClient el tenies aquí, el respectem.
    // Encara que RestTemplateConfig.java crea un RestTemplate, no fan mal.
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    
    // NOTA: Tota la configuració de JndiObjectFactoryBean,
    // LocalContainerEntityManagerFactoryBean i JpaTransactionManager
    // s'ha eliminat per estandarditzar la connexió via DBConnection.java.
}
