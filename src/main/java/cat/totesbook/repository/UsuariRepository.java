
package cat.totesbook.repository;

import cat.totesbook.domain.Usuari;
import java.util.List;

/**
 * Interfície de la capa repositori que gestiona les operacions de l'accés de 
 * dades dels usuaris.
 * 
 * @author equip TotEsBook
 */

public interface UsuariRepository {
    
    /**
     * Mètode que obté tots els usuaris.
     * 
     * @return 
     */
    List<Usuari> getAllUsuaris();
}
