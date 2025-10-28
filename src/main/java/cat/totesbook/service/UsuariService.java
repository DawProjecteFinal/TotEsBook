
package cat.totesbook.service;

import cat.totesbook.domain.Usuari;
import java.util.List;

/**
 * Interfície del servei d'usuaris.
 * 
 * @author equip TotEsBook
 */
public interface UsuariService {
    /**
     * Mètode que retorna tots els usuaris.
     * 
     * @return tots els usuaris.
     */
    List<Usuari> getAllUsuaris();
}
