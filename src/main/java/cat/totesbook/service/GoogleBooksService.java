/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.service;

import cat.totesbook.domain.Llibre;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GoogleBooksService {

    // CONSTANTS GLOBALS
    private static final String GOOGLE_BOOKS_API_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    private static final String TITOL_DEFECTE = "Títol desconegut";
    private static final String EDITORIAL_DEFECTE = "Editorial desconeguda";
    private static final String SINOPSIS_DEFECTE = "Sense sinopsis.";
    private static final String AUTOR_DEFECTE = "Autor desconegut";
    private static final String CATEGORIA_DEFECTE = "Sense categoria";
    private static final String IDIOMA_DEFECTE = "desconegut";

    private final RestTemplate restTemplate;
    private final LlibreService llibreService;

    public GoogleBooksService(RestTemplate restTemplate, LlibreService llibreService) {
        this.restTemplate = restTemplate;
        this.llibreService = llibreService;
    }

    /**
     * Consulta l'API de Google Books per ISBN i retorna un Llibre.
     */
    public Optional<Llibre> getLlibreByIsbn(String isbn) {
        try {
            String cleanIsbn = isbn.replace("-", "");
            String url = GOOGLE_BOOKS_API_BASE_URL + cleanIsbn;

            JsonNode bookData = restTemplate.getForObject(url, JsonNode.class);
            if (bookData != null && bookData.has("items") && bookData.get("items").isArray()
                    && !bookData.get("items").isEmpty()) {

                JsonNode volumeInfo = bookData.get("items").get(0).get("volumeInfo");

                String titol = volumeInfo.path("title").asText(TITOL_DEFECTE);
                String editorial = volumeInfo.path("publisher").asText(EDITORIAL_DEFECTE);
                String sinopsis = volumeInfo.path("description").asText(SINOPSIS_DEFECTE);
                String imatgeUrl = volumeInfo.path("imageLinks").path("thumbnail").asText(null);

                String autors = AUTOR_DEFECTE;
                if (volumeInfo.has("authors")) {
                    autors = StreamSupport.stream(volumeInfo.get("authors").spliterator(), false)
                            .map(JsonNode::asText)
                            .collect(Collectors.joining(", "));
                }

                String categoria = CATEGORIA_DEFECTE;
                if (volumeInfo.has("categories")) {
                    categoria = StreamSupport.stream(volumeInfo.get("categories").spliterator(), false)
                            .map(JsonNode::asText)
                            .findFirst().orElse(CATEGORIA_DEFECTE);
                }

                String idioma = volumeInfo.path("language").asText(IDIOMA_DEFECTE);

                return Optional.of(new Llibre(cleanIsbn, titol, autors, editorial, categoria, sinopsis, imatgeUrl, idioma));
            }

        } catch (Exception e) {
            System.err.println("Error consultant l'API de Google Books per ISBN " + isbn + ": " + e.getMessage());
        }

        return Optional.empty();
    }

    /**
     * Importa i guarda un llibre a la BD si no existeix.
     */
    public void importarILlibrePerIsbn(String isbn) {
        try {
            Optional<Llibre> optLlibre = getLlibreByIsbn(isbn);
            if (optLlibre.isPresent()) {
                Llibre llibre = optLlibre.get();

                Optional<Llibre> existent = llibreService.getLlibreByIsbn(isbn);
                if (existent.isPresent()) {
                    System.out.println("Llibre amb ISBN " + isbn + " ja existeix.");
                    return;
                }

                System.out.println("Guardant llibre: " + llibre.getTitol());
                llibreService.guardarLlibre(llibre);

            } else {
                System.out.println("No s'ha trobat llibre per ISBN " + isbn);
            }
        } catch (Exception e) {
            System.err.println("Error important llibre per ISBN " + isbn + ": " + e.getMessage());
        }
    }
}
