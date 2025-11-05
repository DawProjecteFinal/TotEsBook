/*package cat.totesbook.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient; // Aquest bean estava aquí, el mantenim
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuració del context Arrel de Spring (Backend). S'encarrega d'escanejar
 * Serveis, Repositoris i altres Components. S'ha eliminat la configuració de
 * JPA/EntityManager per evitar conflictes amb la connexió JDBC manual
 * (DBConnection.java).
 */
 /*@Configuration
@ComponentScan(
    basePackages = "cat.totesbook", // Escaneja tot el projecte
    // Exclou els Controllers (perquè els gestiona WebConfig)
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class) 
)
//@EnableTransactionManagement // Encara que fem servir JDBC manual, podem gestionar transaccions
public class JpaConfig {


    // Encara que RestTemplateConfig.java crea un RestTemplate, no fan mal.
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    
}*/
package cat.totesbook.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@EnableTransactionManagement
@ComponentScan(
        basePackages = "cat.totesbook",
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = org.springframework.stereotype.Controller.class)
)
public class JpaConfig {

    @Bean
    public DataSource dataSource() throws NamingException {
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        lookup.setResourceRef(false);  // important
        return lookup.getDataSource("jdbc/TotEsBookDS"); // nom del recurs JNDI"
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPersistenceUnitName("totesbookPersistenceUnit");
        em.setDataSource(dataSource);
        em.setPackagesToScan("cat.totesbook.domain");
        em.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
        return em;
    }

    /**
     * Gestiona les transaccions JPA
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public org.springframework.web.reactive.function.client.WebClient.Builder webClientBuilder() {
        return org.springframework.web.reactive.function.client.WebClient.builder();
    }
}
