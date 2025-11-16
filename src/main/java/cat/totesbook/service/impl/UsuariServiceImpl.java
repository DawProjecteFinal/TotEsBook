
package cat.totesbook.service.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.UsuariService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jmiro
 */
@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuari> getAllUsuaris() {
        return usuariRepository.getAllUsuaris();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana) {

        return usuariRepository.getUsuariByEmailAndContrasenya(email, contrasenyaPlana);
    }

    @Override
    @Transactional
    public void saveUsuari(Usuari usuari) {
        usuariRepository.saveUsuari(usuari);
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public void updateUsuari(Usuari usuari) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
