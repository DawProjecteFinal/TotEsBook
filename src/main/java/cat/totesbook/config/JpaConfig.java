package cat.totesbook.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.jndi.JndiObjectFactoryBean;
import java.util.Properties;

/**
 * 
 * @author equip TotEsBook
 */
@Configuration
@ComponentScan(
    basePackages = "cat.totesbook",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
)
/**
 * 
 */
@EnableTransactionManagement
public class JpaConfig {

    /**
     * 
     * @return 
     */
    @Bean
    public JndiObjectFactoryBean dataSource() {
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setJndiName("jdbc/TotEsBookDS");
        jndiObjectFactoryBean.setResourceRef(true);
        jndiObjectFactoryBean.setProxyInterface(DataSource.class);
        return jndiObjectFactoryBean;
    }

    /**
     * 
     * @param dataSource
     * @return 
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        // Nom de la persistence-unit del persistence.xml
        emf.setPersistenceUnitName("totesbookPersistenceUnit");

        // Propietats JPA per assegurar que Spring té el control
        Properties jpaProperties = new Properties();
        jpaProperties.put("jakarta.persistence.transactionType", "RESOURCE_LOCAL");
        emf.setJpaProperties(jpaProperties);

        // especifiquem el provider JPA (EclipseLink) per evitar ambigüitats.
        emf.setPersistenceProviderClass(org.eclipse.persistence.jpa.PersistenceProvider.class);

        return emf;
    }
    
    /**
     * 
     * @param emf
     * @return 
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        // Transaccions JPA gestionades per Spring
        return new JpaTransactionManager(emf);
    }

    /**
     * 
     * @return 
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
