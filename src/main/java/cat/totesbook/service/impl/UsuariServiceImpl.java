package cat.totesbook.service.impl;

import cat.totesbook.domain.Usuari;
import cat.totesbook.repository.UsuariRepository;
import cat.totesbook.service.UsuariService;
import cat.totesbook.domain.Rol;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe que implementa el servei de l'usuari.
 * 
 * @author Equip TotEsBook
 */
@Service
public class UsuariServiceImpl implements UsuariService {

    @Autowired
    private UsuariRepository usuariRepository;

    /**
     * Retorna una llista de tots els Usuaris (lectors) del sistema.
     * 
     * (Aquest era el mètode que faltava - Error 5)
     * @return Llista d'objectes Usuari.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Usuari> getAllUsuaris() {
        return usuariRepository.getAllUsuaris();
    }

    /**
     * Comprova el login d'un Usuari (lector) verificant la seva contrasenya hashejada.
     * 
     * @param email L'email del formulari.
     * @param contrasenyaPlana La contrasenya en TEXT PLA del formulari.
     * @return L'objecte Usuari si és correcte, o null si no ho és.
     */
    @Override
    @Transactional(readOnly = true)
    public Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana) {

        return usuariRepository.getUsuariByEmailAndContrasenya(email, contrasenyaPlana);
    }

     /**
     * Desa un nou usuari a la base de dades.
     * 
     * @param usuari L'objecte Usuari (amb la contrasenya ja hashejada).
     * @throws Exception En cas d'error.
     */
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

        usuariRepository.saveUsuari(usuari);
    }

    /**
     * Cerca un usuari només pel seu email (útil per comprovar si ja existeix).
     * 
     * @param email L'email a cercar.
     * @return L'objecte Usuari si es troba, o null.
     */
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
    
    /**
     * Actualitzar l'usuari.
     * 
     * @param usuariModificat usuariModificat.
     * @param nouPassword La contrasenya nova.
     * @throws Exception En cas d'error.
     */
    @Override
    @Transactional
    public void updateUsuari(Usuari usuariModificat, String nouPassword) throws Exception {
        Usuari usuariBBDD = usuariRepository.findUsuariById(usuariModificat.getId());
        if (usuariBBDD == null) {
            throw new Exception("L'usuari no existeix.");
        }

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
    
    /**
     * Per crear lectors manualment des del Panell Bibliotecari
     * 
     * @param nom Nom d'un nou lector. 
     * @param cognoms Cognoms d'un nou lector.
     * @param telefon Teléfon d'un nou lector.
     * @param email Correu d'un nou lector.
     * @param password Contrasenya d'un nou lector.
     * @throws Exception En cas d'error.
     */
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

    /**
     * Aplicar una sanció a l'usuari.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @param dataFiSancio La data de la finalització.
     * @param motiuSancio El motiu de la sanció.
     */
    @Override
    @Transactional
    public void aplicarSancio(Integer idUsuari, LocalDateTime dataFiSancio, String motiuSancio) {

        Usuari usuari = usuariRepository.findUsuariById(idUsuari);
        if (usuari == null) {
            throw new IllegalArgumentException("Usuari no trobat");
        }

        usuari.setDataFiSancio(dataFiSancio);
        usuari.setMotiuSancio(motiuSancio);

        usuariRepository.updateSancioUsuari(idUsuari, dataFiSancio, motiuSancio);
    }

    /**
     * Retorna una llista d'usuaris amb la sanció activa.
     * 
     * @return Una llista d'usuaris amb la sanció activa.
     */
    @Override 
    @Transactional(readOnly = true)
    public List<Usuari> getUsuarisAmbSancioActiva() {
        return usuariRepository.getUsuarisAmbSancioActiva();
    }

    /**
     * Comprova si l'usuari té sanció o no.
     * 
     * @param idUsuari ID de l'usuari.
     * @return true si té sanció i false i no la té.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean teSancioActiva(int idUsuari) {
        Usuari u = usuariRepository.findUsuariById(idUsuari);
        return u != null && u.teSancioActiva();
    }

    /**
     * Busca un usuari complet per la seva ID.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return L'objecte Usuari, o null si no es troba.
     */
    @Override
    @Transactional(readOnly = true)
    public Usuari findUsuariById(int idUsuari) {
        return usuariRepository.findUsuariById(idUsuari);
    }
}
