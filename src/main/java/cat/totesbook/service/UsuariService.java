
package cat.totesbook.service;

import cat.totesbook.domain.Usuari;
import java.util.List;

/**
 *
 * @author jmiro
 */
public interface UsuariService {

    List<Usuari> getAllUsuaris();

    Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana);

    Usuari getUsuariByEmail(String email);

    void saveUsuari(Usuari usuari);

    void updateUsuari(Usuari usuari);
}
