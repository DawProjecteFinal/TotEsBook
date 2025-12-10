package cat.totesbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;

/**
 * Classe que crea un SSLContext manual per controlar els certificats en els  
 * que confiem per no dependre de Glassfish ni del JDK. Amb això controlem el SSL.
 * 
 * @author Equip TotEsBook
 */
@Configuration
public class RestTemplateConfig {

    /**
     * Configuració de Rest Template.
     * 
     * @return rest template nou.
     * @throws Exception si hi ha un error.
     */
    @Bean
    public RestTemplate restTemplate() throws Exception {

        // TrustManager que accepta tots els certificats (EVITA PKIX)
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        //  SSLContext amb el TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        //  Crear SSLConnectionSocketFactory amb l’SSLContext
        SSLConnectionSocketFactory sslSocketFactory = SSLConnectionSocketFactoryBuilder.create()
                .setSslContext(sslContext)
                .build();

        // Crear ConnectionManager amb l’SSLSocketFactory
        PoolingHttpClientConnectionManager connectionManager =
                PoolingHttpClientConnectionManagerBuilder.create()
                        .setSSLSocketFactory(sslSocketFactory)
                        .build();

        // Crear HttpClient amb el ConnectionManager
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        // Assignar HttpClient al RestTemplate
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(requestFactory);
    }
}
