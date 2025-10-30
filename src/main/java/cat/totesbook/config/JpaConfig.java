package cat.totesbook.config;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.stereotype.Controller;
import org.springframework.jndi.JndiObjectFactoryBean;

@Configuration
@ComponentScan(
    basePackages = "cat.totesbook",
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)
)
@EnableTransactionManagement
public class JpaConfig {

    /**
     * DataSource obtingut del pool JNDI gestionat per GlassFish
     */
    @Bean
    public JndiObjectFactoryBean dataSource() {
        JndiObjectFactoryBean jndi = new JndiObjectFactoryBean();
        jndi.setJndiName("jdbc/TotEsBookDS");  // nom exacte del teu pool
        jndi.setResourceRef(true);
        jndi.setProxyInterface(DataSource.class);
        return jndi;
    }

    /**
     * EntityManagerFactory vinculat a la unitat de persistència del persistence.xml
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPersistenceUnitName("totesbookPersistenceUnit");
        emf.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter());
        return emf;
    }

    /**
     * Transaction manager basat en JTA (GlassFish)
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JtaTransactionManager();
    }

    /**
     * WebClient opcional per a crides HTTP externes
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
