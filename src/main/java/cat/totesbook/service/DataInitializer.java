package cat.totesbook.service;

import cat.totesbook.domain.Llibre; // Importa Llibre
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// Imports necessaris per a la gestió d'errors i espera
import jakarta.persistence.PersistenceException; 
import org.eclipse.persistence.exceptions.DatabaseException; 
import java.sql.SQLSyntaxErrorException; 

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

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

    // Constants per a la lògica d'espera/reintent
    private static final int MAX_INTENTS = 6; // Intentarem 6 cops
    private static final long ESPERA_ENTRE_INTENTS_MS = 5000; // Esperem 5 segons
    @PostConstruct
    @Transactional // Mantenim la transacció per si s'insereixen dades
    public void initData() {
        System.out.println(">>> Iniciant DataInitializer...");

        boolean dadesInicialitzades = false;
        
        // Bucle per intentar accedir a la BBDD diverses vegades
        for (int intent = 1; intent <= MAX_INTENTS; intent++) {
            try {
                // Intentem obtenir els llibres per veure si la taula existeix
                List<Llibre> llibresExistents = llibreService.getAllLlibres();
                
                System.out.println(">>> DataInitializer: Connexió a BBDD reeixida (Intent " + intent + ").");

                // Si la taula existeix i està buida, procedim a inicialitzar
                if (llibresExistents.isEmpty()) {
                    System.out.println(">>> DataInitializer: La taula Llibres és buida. Important dades des de Google Books API...");
                    importarLlibresDesDeFitxer(); // Cridem a un mètode separat per claredat
                } else {
                    System.out.println(">>> DataInitializer: Ja hi ha llibres a la BBDD. No cal inicialitzar.");
                }
                
                dadesInicialitzades = true; // Marquem que hem acabat
                break; // Sortim del bucle for si tot ha anat bé

            } catch (PersistenceException e) {
                // Comprovem si l'error és perquè la taula 'Llibres' no existeix
                if (esErrorTaulaNoExisteix(e)) {
                    System.out.println(">>> DataInitializer: Intent " + intent + "/" + MAX_INTENTS + 
                                       " - La taula Llibres encara no existeix. Esperant " + 
                                       (ESPERA_ENTRE_INTENTS_MS / 1000) + " segons...");
                    if (intent < MAX_INTENTS) {
                        esperar(ESPERA_ENTRE_INTENTS_MS); // Esperem abans del següent intent
                    }
                } else {
                    // És un altre error de persistència, el registrem i sortim
                    System.err.println(">>> DataInitializer: Error de persistència inesperat:");
                    e.printStackTrace();
                    break; // Sortim del bucle en cas d'error no recuperable
                }
            } catch (Exception e) {
                // Qualsevol altre error inesperat
                System.err.println(">>> DataInitializer: Error general inesperat durant la inicialització:");
                e.printStackTrace();
                break; // Sortim del bucle
            }
        } // Fi del bucle for

        // Comprovem si hem aconseguit inicialitzar les dades
        if (!dadesInicialitzades) {
            System.err.println(">>> DataInitializer: ERROR FATAL - No s'ha pogut accedir a la taula Llibres després de " + 
                               MAX_INTENTS + " intents. Comprova l'script init.sql, la configuració de la BBDD i els logs de MySQL.");
        }
    }

    /**
     * Mètode auxiliar per importar els ISBNs des del fitxer.
     */
    private void importarLlibresDesDeFitxer() {
         try (BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("isbns.txt").getInputStream()))) {
             reader.lines().forEach(isbn -> {
                 // Usem try-catch per a cada llibre per si un falla
                 try {
                     googleBooksService.getLlibreByIsbn(isbn).ifPresent(llibre -> {
                         System.out.println(">>> DataInitializer: Guardant llibre: " + llibre.getTitol());
                         llibreService.guardarLlibre(llibre); // El servei gestiona la transacció
                     });
                 } catch (Exception ex) {
                     System.err.println(">>> DataInitializer: Error processant ISBN '" + isbn + "': " + ex.getMessage());
                 }
             });
             System.out.println(">>> DataInitializer: Dades importades des de fitxer.");
         } catch (Exception e) {
             System.err.println(">>> DataInitializer: Error crític llegint el fitxer d'ISBNs 'isbns.txt': " + e.getMessage());
             e.printStackTrace();
         }
    }

    /**
     * Mètode auxiliar per comprovar si l'excepció indica que la taula no existeix.
     * @param e L'excepció de persistència.
     * @return true si l'error és per taula inexistent, false altrament.
     */
    private boolean esErrorTaulaNoExisteix(PersistenceException e) {
        Throwable causaArrel = e;
        // Naveguem per les causes anidades fins a trobar l'excepció SQL original
        while (causaArrel.getCause() != null && causaArrel.getCause() != causaArrel) {
            causaArrel = causaArrel.getCause();
        }
        
        // Comprovem si l'excepció arrel és la que indica que la taula no existeix
        if (causaArrel instanceof SQLSyntaxErrorException) {
            SQLSyntaxErrorException sqlEx = (SQLSyntaxErrorException) causaArrel;
            // Comprovem el missatge o el codi d'error (1146 per a MySQL)
            return sqlEx.getErrorCode() == 1146 || 
                   (sqlEx.getMessage() != null && 
                    sqlEx.getMessage().toLowerCase().contains("table") && 
                    sqlEx.getMessage().toLowerCase().contains("doesn't exist"));
        }
        return false;
    }

    /**
     * Mètode auxiliar per fer una pausa.
     * @param ms Milisegons a esperar.
     */
    private void esperar(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            System.err.println(">>> DataInitializer: Fil d'espera interromput.");
        }
    }
}
