/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.repository;

import cat.totesbook.domain.Usuari;
import java.util.List;

/**
 * Interfície per a les operacions de dades de la taula Usuaris.
 */
public interface UsuariRepository {

    /**
     * Comprova el login d'un Usuari (lector) verificant la seva contrasenya hashejada.
     * @param email L'email del formulari.
     * @param passwordPla La contrasenya en TEXT PLA del formulari.
     * @return L'objecte Usuari si és correcte, o null si no ho és.
     */
    Usuari getUsuariByEmailAndContrasenya(String email, String passwordPla);

    /**
     * Desa un nou usuari a la base de dades.
     * @param usuari L'objecte Usuari (amb la contrasenya ja hashejada).
     */
    void saveUsuari(Usuari usuari);

    /**
     * Cerca un usuari només pel seu email (útil per comprovar si ja existeix).
     * @param email L'email a cercar.
     * @return L'objecte Usuari si es troba, o null.
     */
    Usuari getUsuariByEmail(String email);

    /**
     * Retorna una llista de tots els Usuaris (lectors) del sistema.
     * (Aquest era el mètode que faltava - Error 5)
     * @return Llista d'objectes Usuari.
     */
    List<Usuari> getAllUsuaris();
    

    
    /**
     * Busca un usuari complet per la seva ID.
     * @param id L'ID de l'usuari.
     * @return L'objecte Usuari, o null si no es troba.
     */
    Usuari findUsuariById(int id);
    
    /**
     * Actualitza les dades bàsiques del perfil d'un usuari (sense contrasenya).
     * @param usuari L'objecte usuari amb les dades actualitzades (necessita ID, nom, cognoms, email, telefon).
     */
    void updatePerfil(Usuari usuari);
    
    
    
   
}
