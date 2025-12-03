/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.domain; 

/**
 * Enum que defineix els rols del sistema, independentment
 * de com s'emmagatzemen a la base de dades (taules Usuaris vs Agents).
 */
public enum Rol { 
    ADMIN, 
    BIBLIOTECARI, 
    USUARI 
}
