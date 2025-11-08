
package cat.totesbook.service.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.UsuariService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author jmiro
 */
@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    @Override
    public List<Usuari> getAllUsuaris() {
        return usuariRepository.getAllUsuaris();
    }

    @Override
    public Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana) {

        return usuariRepository.getUsuariByEmailAndContrasenya(email, contrasenyaPlana);
    }

    @Override
    public void saveUsuari(Usuari usuari) {
        usuariRepository.saveUsuari(usuari);
    }

    @Override
    public Usuari getUsuariByEmail(String email) {
        return usuariRepository.getUsuariByEmail(email);
    }
    /*
    @Override
    public void updatePerfil(Usuari usuari) {
        usuariRepository.updatePerfil(usuari);
    }
    */

    @Override
    public void updateUsuari(Usuari usuari) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
