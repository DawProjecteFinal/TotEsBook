package cat.totesbook.service;

import cat.totesbook.domain.Usuari;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interfície del servei d'usuaris.
 * 
 * @author equip TotEsBook
 */
public interface UsuariService {

    /**
     * Retorna una llista de tots els Usuaris (lectors) del sistema.
     * 
     * (Aquest era el mètode que faltava - Error 5)
     * @return Llista d'objectes Usuari.
     */
    List<Usuari> getAllUsuaris();

    /**
     * Comprova el login d'un Usuari (lector) verificant la seva contrasenya hashejada.
     * 
     * @param email L'email del formulari.
     * @param contrasenyaPlana La contrasenya en TEXT PLA del formulari.
     * @return L'objecte Usuari si és correcte, o null si no ho és.
     */
    Usuari getUsuariByEmailAndContrasenya(String email, String contrasenyaPlana);

    /**
     * Cerca un usuari només pel seu email (útil per comprovar si ja existeix).
     * 
     * @param email L'email a cercar.
     * @return L'objecte Usuari si es troba, o null.
     */
    Usuari getUsuariByEmail(String email);

    /**
     * Desa un nou usuari a la base de dades.
     * 
     * @param usuari L'objecte Usuari (amb la contrasenya ja hashejada).
     * @throws Exception En cas d'error.
     */
    void saveUsuari(Usuari usuari) throws Exception;

    /**
     * Actualitzar l'usuari.
     * 
     * @param usuari L'usuari.
     * @param nouPassword La contrasenya nova.
     * @throws Exception En cas d'error.
     */
    void updateUsuari(Usuari usuari, String nouPassword) throws Exception;

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
    void crearLectorManual(String nom, String cognoms, String telefon, String email, String password) throws Exception;

    /**
     * Aplicar una sanció a l'usuari.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @param dataFiSancio La data de la finalització.
     * @param motiuSancio El motiu de la sanció.
     */
    void aplicarSancio(Integer idUsuari, LocalDateTime dataFiSancio, String motiuSancio);

    /**
     * Retorna una llista d'usuaris amb la sanció activa.
     * 
     * @return Una llista d'usuaris amb la sanció activa.
     */
    List<Usuari> getUsuarisAmbSancioActiva();

    /**
     * Comprova si l'usuari té sanció o no.
     * 
     * @param idUsuari ID de l'usuari.
     * @return true si té sanció i false i no la té.
     */
    boolean teSancioActiva(int idUsuari);

    /**
     * Busca un usuari complet per la seva ID.
     * 
     * @param idUsuari L'ID de l'usuari.
     * @return L'objecte Usuari, o null si no es troba.
     */
    Usuari findUsuariById(int idUsuari);
}
