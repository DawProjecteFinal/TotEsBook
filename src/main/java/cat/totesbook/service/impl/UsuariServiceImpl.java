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

/**
 * Classe que implementa el servei dels usuaris.
 * 
 * @author equip TotEsBook
 */
@Service
public class UsuariServiceImpl implements UsuariService {

    /**
     * Repositori d'usuaris que serveix per accedir a les dades de la BD.
     */
    @Autowired
    private UsuariRepository usuariRepository;

    /**
     * Mètode que retorna tots els usuaris.
     * 
     * @return llista dels usuaris.
     */
    @Override
    public List<Usuari> getAllUsuaris() {
        return usuariRepository.getAllUsuaris();
    }

}
