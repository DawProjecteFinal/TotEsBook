package cat.totesbook.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Classe que inicialitza les dades de la BD quan es desplega l'aplicació.
 * 
 * @author equip TotEsBook
 */
@Component
public class DataInitializer {

    /**
     * Repositori de llibres que serveix per accedir a les dades de la API de
     * Google Books Service.
     */
    @Autowired
    private GoogleBooksService googleBooksService;

    /**
     * Repositori de llibres que serveix per accedir a les dades de la BD.
     */
    @Autowired
    private LlibreService llibreService;

    /**
     * Mètode que buscar les dades quan es desplega l'aplicació.
     * 
     */
    @PostConstruct
    @Transactional
    public void initData() {
        // Només inicialitzem si la taula és buida
        if (llibreService.getAllLlibres().isEmpty()) {
            System.out.println("La base de dades de llibres és buida. Important dades des de Google Books API...");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("isbns.txt").getInputStream()))) {
                reader.lines().forEach(isbn -> {
                    googleBooksService.getLlibreByIsbn(isbn).ifPresent(llibre -> {
                        System.out.println("Guardant llibre: " + llibre.getTitol());
                        // Cridem el servei, que és qui gestiona la transacció
                        llibreService.guardarLlibre(llibre);
                    });
                });
                System.out.println("Dades importades correctament.");
            } catch (Exception e) {
                System.err.println("Error llegint el fitxer d'ISBNs: " + e.getMessage());
            }
        }
    }
}