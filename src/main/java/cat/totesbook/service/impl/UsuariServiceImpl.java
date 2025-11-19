
package cat.totesbook.service.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.UsuariService;
import cat.totesbook.domain.Rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
    public void saveUsuari(Usuari usuari) throws Exception {
        if (usuariRepository.getUsuariByEmail(usuari.getEmail()) != null) {
            throw new Exception("L'email ja està en ús.");
        }
        
        if (usuari.getContrasenya() != null && !usuari.getContrasenya().startsWith("$2a$")) {
             String hashedPassword = BCrypt.hashpw(usuari.getContrasenya(), BCrypt.gensalt(12));
             usuari.setContrasenya(hashedPassword);
        }
        
        // ELIMINAT: u.setRol(...) perquè ja no existeix a l'entitat ni a la BD.
        // L'entitat Usuari retorna sempre Rol.USUARI pel seu mètode getRol().

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
    public void updateUsuari(Usuari usuariModificat, String nouPassword) throws Exception {
        Usuari usuariBBDD = usuariRepository.findUsuariById(usuariModificat.getId());
        if (usuariBBDD == null) throw new Exception("L'usuari no existeix.");
        
        usuariBBDD.setNom(usuariModificat.getNom());
        usuariBBDD.setCognoms(usuariModificat.getCognoms());
        usuariBBDD.setEmail(usuariModificat.getEmail());

        if (nouPassword != null && !nouPassword.trim().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(nouPassword, BCrypt.gensalt(12));
            usuariBBDD.setContrasenya(hashedPassword);
        }

        usuariRepository.updatePerfil(usuariBBDD); 
    }
    
    // Implementació per afegir usuari lector manual des del Panell Bibliotecari
    @Override
    @Transactional // Aquí sí volem transacció per guardar
    public void crearLectorManual(String nom, String cognoms, String telefon, String email, String password) throws Exception {
        
        // 1. Validar email (que no existeixi ja)
        if (usuariRepository.getUsuariByEmail(email) != null) {
            throw new Exception("Aquest email ja està registrat al sistema.");
        }

        Usuari u = new Usuari();
        u.setNom(nom);
        u.setCognoms(cognoms);
        u.setTelefon(telefon);
        u.setEmail(email);
        
        // 2. Hashejar password (Seguretat)
        // Si encara no uses BCrypt, posa: u.setContrasenya(password);
        String hash = BCrypt.hashpw(password, BCrypt.gensalt(12));
        u.setContrasenya(hash); 

        usuariRepository.saveUsuari(u);
    }
}
