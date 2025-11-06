package cat.totesbook.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(
        basePackages = "cat.totesbook",
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                value = org.springframework.stereotype.Controller.class
        )
)
public class JpaConfig {

    @Bean
    public DataSource dataSource() throws NamingException {
        return (DataSource) new InitialContext().lookup("jdbc/TotEsBookDS");
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceUnitName("totesbookPersistenceUnit");

        EclipseLinkJpaVendorAdapter vendorAdapter = new EclipseLinkJpaVendorAdapter();
        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("eclipselink.target-server", "None"); // Evita que EclipseLink intente usar JTA de Glassfish
        em.setJpaProperties(jpaProperties);
        em.setJpaVendorAdapter(vendorAdapter);
        em.setPackagesToScan("cat.totesbook.domain");
        return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
