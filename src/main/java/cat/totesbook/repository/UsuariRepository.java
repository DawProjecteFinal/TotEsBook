
package cat.totesbook.repository;

import cat.totesbook.domain.Usuari;
import java.util.List;

/**
 *
 * @author jmiro
 */

// Interfaces de la capa repositori
public interface UsuariRepository {
    // Mètode per obtenir tots els usuaris
    List<Usuari> getAllUsuaris();
}
