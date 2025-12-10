package cat.totesbook.domain; 

/**
 * Enum que defineix els rols del sistema, independentment
 * de com s'emmagatzemen a la base de dades (taules Usuaris vs Agents).
 * 
 * @author Equip TotEsBook
 */

/**
 * 
 * Enum que defineix els rols del sistema.
 */
public enum Rol {
    /** Rol de l'admin.*/
    ADMIN,
    
    /** Rol del bibliotecari.*/
    BIBLIOTECARI, 
    
    /** Rol de l'usuari.*/
    USUARI 
}
