/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.service;

import cat.totesbook.domain.Usuari;
import java.util.List;


public interface UsuariService {

    List<Usuari> getAllUsuaris();

    Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana);

    Usuari getUsuariByEmail(String email);

    void saveUsuari(Usuari usuari) throws Exception;

    void updateUsuari(Usuari usuari, String nouPassword) throws Exception;
    
    // Per crear lectors manualment des del Panell Bibliotecari
    void crearLectorManual(String nom, String cognoms, String telefon, String email, String password) throws Exception;
}
