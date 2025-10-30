package cat.totesbook.repository;
import java.util.List;
import cat.totesbook.domain.Llibre;
import java.util.Optional;


/**
 * Interfície de la capa repositori que gestiona les operacions de l'accés de dades dels llibres.
 * 
 * @author equip TotEsBook
 */

public interface LlibreRepository {
    
    /**
     * Mètode que obté tots els llibres.
     * 
     * @return llista de llibres.
     */
    List<Llibre> getAllLlibres();
    
    // Afegeix o actualitza un llibre
    void addLlibre(Llibre llibre);

    // Obté un llibre per ISBN si existeix
    Optional<Llibre> getLlibreByIsbn(String isbn);

}