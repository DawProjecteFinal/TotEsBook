package cat.totesbook.init;

import cat.totesbook.domain.*;
import cat.totesbook.service.BibliotecaLlibreService;
import cat.totesbook.service.BibliotecaService;
import cat.totesbook.service.GoogleBooksService;
import cat.totesbook.service.LlibreService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLSyntaxErrorException;
import java.util.*;

@Component
public class DataInitializer {

    // CONSTANTS GLOBALS
    private static final String ISBN_FILE = "isbns.txt";
    private static final int MAX_INTENTS = 6;
    private static final long ESPERA_ENTRE_INTENTS_MS = 5000;

    @Autowired
    private GoogleBooksService googleBooksService;
    @Autowired
    private LlibreService llibreService;
    @Autowired
    private BibliotecaService bibliotecaService;
    @Autowired
    private BibliotecaLlibreService bibliotecaLlibreService;

    // MÈTODE PRINCIPAL D’ARRENCADA 
    @PostConstruct
    public void initData() {
        System.out.println(">>> Iniciant DataInitializer...");
        try {
            inicialitzarDades(); // Fem la crida al mètode transaccional
        } catch (Exception e) {
            System.err.println(">>> Error durant la inicialització: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // MÈTODE TRANSACCIONAL PRINCIPAL 
    @Transactional
    public void inicialitzarDades() {
        boolean dadesInicialitzades = false;

        for (int intent = 1; intent <= MAX_INTENTS; intent++) {
            try {
                List<Llibre> llibresExistents = llibreService.getAllLlibres();
                System.out.println(">>> Connexió a BBDD correcta (Intent " + intent + ").");

                if (llibresExistents.isEmpty()) {
                    System.out.println(">>> La taula Llibres és buida. Important dades des de fitxer...");
                    importarLlibresDesDeFitxer();
                } else {
                    System.out.println(">>> Ja hi ha llibres a la BBDD. No s'importen dades.");
                }

                dadesInicialitzades = true;
                break;

            } catch (PersistenceException e) {
                if (esErrorTaulaNoExisteix(e)) {
                    System.out.println(">>> Intent " + intent + "/" + MAX_INTENTS
                            + " - La taula Llibres encara no existeix. Esperant "
                            + (ESPERA_ENTRE_INTENTS_MS / 1000) + " segons...");
                    esperar(ESPERA_ENTRE_INTENTS_MS);
                } else {
                    System.err.println(">>> Error de persistència inesperat:");
                    e.printStackTrace();
                    break;
                }
            } catch (Exception e) {
                System.err.println(">>> Error inesperat durant la inicialització:");
                e.printStackTrace();
                break;
            }
        }

        if (!dadesInicialitzades) {
            System.err.println(">>> ERROR FATAL - No s'ha pogut inicialitzar després de "
                    + MAX_INTENTS + " intents.");
        }
    }

// LLEGIR FITXER D'ISBNS I IMPORTAR 
    private void importarLlibresDesDeFitxer() {
        Map<String, List<String>> isbnsPerBiblioteca = new LinkedHashMap<>();
        String bibliotecaActual = null;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(ISBN_FILE).getInputStream()))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("#")) {
                    bibliotecaActual = line.replace("#", "").trim();
                    isbnsPerBiblioteca.put(bibliotecaActual, new ArrayList<>());
                } else if (bibliotecaActual != null) {
                    isbnsPerBiblioteca.get(bibliotecaActual).add(line);
                }

                System.out.println("DEBUG -> línia llegida: [" + line + "]");
            }

            // PROCESSAR CADA BIBLIOTECA
            for (Map.Entry<String, List<String>> entry : isbnsPerBiblioteca.entrySet()) {
                String nomBiblioteca = entry.getKey();
                List<String> isbns = entry.getValue();

                Biblioteca biblioteca = bibliotecaService.findByNom(nomBiblioteca)
                        .orElseThrow(() -> new RuntimeException("No s'ha trobat la biblioteca: " + nomBiblioteca));

                System.out.println(">>> Important " + isbns.size() + " llibres per a " + nomBiblioteca);

                for (String linia : isbns) {

                    String[] parts = linia.split(";");
                    String isbn = parts[0].trim();
                    int exemplars = (parts.length > 1) ? Integer.parseInt(parts[1].trim()) : 3;

                    try {
                        Optional<Llibre> optLlibre = googleBooksService.getLlibreByIsbn(isbn);

                        if (optLlibre.isPresent()) {

                            Llibre llibre = optLlibre.get();

                            // Assignar exemplars abans de guardar
                            llibre.setExemplars(exemplars);
                            llibre.setDisponibles(exemplars);

                            // Guardar llibre globalment
                            llibreService.guardarLlibre(llibre);

                            // Assignar-lo a la biblioteca concreta
                            bibliotecaLlibreService.afegirLlibre(biblioteca, llibre, exemplars, exemplars);

                            System.out.println(">>> [" + nomBiblioteca + "] Afegit llibre "
                                    + llibre.getIsbn() + " - " + llibre.getTitol()
                                    + " (" + exemplars + " exemplars)");

                        } else {
                            System.out.println(">>> [" + nomBiblioteca + "] ISBN " + isbn
                                    + " NO TROBAT a l'API Google Books.");
                        }

                    } catch (Exception ex) {
                        System.err.println(">>> Error amb ISBN " + isbn + ": " + ex.getMessage());
                    }
                }
            }

            System.out.println(">>> Importació completada per totes les biblioteques.");

        } catch (Exception e) {
            System.err.println(">>> Error crític llegint el fitxer '" + ISBN_FILE + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

    //  MÈTODES AUXILIARS 
    private boolean esErrorTaulaNoExisteix(PersistenceException e) {
        Throwable causaArrel = e;
        while (causaArrel.getCause() != null && causaArrel.getCause() != causaArrel) {
            causaArrel = causaArrel.getCause();
        }
        if (causaArrel instanceof SQLSyntaxErrorException sqlEx) {
            return sqlEx.getErrorCode() == 1146
                    || (sqlEx.getMessage() != null
                    && sqlEx.getMessage().toLowerCase().contains("table")
                    && sqlEx.getMessage().toLowerCase().contains("doesn't exist"));
        }
        return false;
    }

    private void esperar(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println(">>> Fil d'espera interromput.");
        }
    }
}
