/**
 *
 * @author Equip TotEsBook
 */

package cat.totesbook.config; // Corregim el paquet a 'config'

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

public class DBConnection {

    private static final String JNDI_NAME = "jdbc/TotEsBookDS";
    private static final boolean DEBUG = false; // variable per al registre de logs

    // Utilitzem el pool de connexions que tenim creat aa Glassfish
    public static Connection getConnection() throws Exception {
        InitialContext ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup(JNDI_NAME);
        Connection conn = ds.getConnection();

        if (conn != null) {
            System.out.println("DBConnection (JNDI): Connexió obtinguda del pool " + JNDI_NAME);
        } else {
            System.err.println("DBConnection (JNDI): ERROR - no s'ha pogut obtenir la connexió!");
        }
        
        if (DEBUG) {
            System.out.println("Connexió obtinguda: " + conn);
        }
        return conn;
    }
}
