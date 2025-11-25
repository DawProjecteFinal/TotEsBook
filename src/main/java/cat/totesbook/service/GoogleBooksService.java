package cat.totesbook.service;

import cat.totesbook.domain.Llibre;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

/**
 *
 * @author Equip TotEsBook
 */

@Service
public class GoogleBooksService {

    // CONSTANTS GLOBALS
    private static final String GOOGLE_BOOKS_API_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:";
    private static final String GOOGLE_BOOKS_API_CONSULTA_AVANCADA = "https://www.googleapis.com/books/v1/volumes?q=";
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
    public List<Llibre> cercarLlibres(String titol, String autor, String isbn) {
        try {

            // Si hi ha ISBN → cerca directa
            if (isbn != null && !isbn.isBlank()) {
                Optional<Llibre> llibreOpt = getLlibreByIsbn(isbn.trim());
                return llibreOpt.map(List::of).orElse(List.of());
            }

            // Construir query literal (sense encode)
            StringBuilder query = new StringBuilder();

            if (titol != null && !titol.isBlank()) {
                query.append("intitle:").append(titol.trim());
            }
            if (autor != null && !autor.isBlank()) {
                if (query.length() > 0) {
                    query.append("+");
                }
                query.append("inauthor:").append(autor.trim());
            }

            if (query.length() == 0) {
                return List.of();
            }

            // Construir URI encodada CORRECTAMENT
            URI uri = UriComponentsBuilder
                    .fromHttpUrl("https://www.googleapis.com/books/v1/volumes")
                    .queryParam("q", query.toString())
                    .encode() // ← FA L'ENCODING AUTOMÀTIC DELS ACCENTS i ESPAIS
                    .build()
                    .toUri();

            JsonNode bookData = restTemplate.getForObject(uri, JsonNode.class);

            List<Llibre> resultats = new ArrayList<>();

            if (bookData != null && bookData.has("items")) {
                for (JsonNode item : bookData.get("items")) {

                    JsonNode info = item.get("volumeInfo");
                    if (info == null) {
                        continue;
                    }

                    // ISBN
                    String isbnFound = null;

                    if (info.has("industryIdentifiers")) {
                        for (JsonNode idNode : info.get("industryIdentifiers")) {
                            if ("ISBN_13".equals(idNode.path("type").asText())) {
                                isbnFound = idNode.path("identifier").asText();
                            }
                        }
                        if (isbnFound == null) {
                            for (JsonNode idNode : info.get("industryIdentifiers")) {
                                if ("ISBN_10".equals(idNode.path("type").asText())) {
                                    isbnFound = idNode.path("identifier").asText();
                                }
                            }
                        }
                    }

                    if (isbnFound == null || isbnFound.isBlank()) {
                        continue;
                    }

                    String titolRes = info.path("title").asText("Títol desconegut");
                    String editorial = info.path("publisher").asText("Editorial desconeguda");
                    String sinopsis = info.path("description").asText("Sense sinopsi");
                    String image = info.path("imageLinks").path("thumbnail").asText(null);

                    // Autors
                    String autors = "Desconegut";
                    if (info.has("authors") && info.get("authors").isArray()) {
                        List<String> list = new ArrayList<>();
                        for (JsonNode a : info.get("authors")) {
                            list.add(a.asText());
                        }
                        autors = String.join(", ", list);

                        // filtre autor
                        if (autor != null && !autor.isBlank()) {
                            if (!autors.toLowerCase().contains(autor.toLowerCase())) {
                                continue;
                            }
                        }
                    }

                    String categoria = info.path("categories").isArray()
                            ? info.get("categories").get(0).asText()
                            : "Sense categoria";

                    String idioma = info.path("language").asText("N/A");

                    resultats.add(new Llibre(
                            isbnFound, titolRes, autors, editorial,
                            categoria, sinopsis, image, idioma
                    ));
                }
            }

            return resultats;

        } catch (Exception e) {
            System.err.println("Error consultant Google Books: " + e.getMessage());
            return List.of();
        }
    }


}
