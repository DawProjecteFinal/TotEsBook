/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    @Override
    @Transactional
    public void updateUsuari(Usuari usuari) {
        usuariRepository.updateUsuari(usuari);
    }
}
