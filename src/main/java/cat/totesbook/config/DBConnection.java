/**
 *
 * @author edinsonioc
 */
package cat.totesbook.config; // Corregim el paquet a 'config'

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Llegim les variables d'entorn
    private static final String DB_HOST = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
    private static final String DB_PORT = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
    private static final String DB_NAME = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "totesbook";
    private static final String USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "totesuser";
    private static final String PASSWORD = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "totespass";

    // URL de connexió amb paràmetres recomanats
    private static final String URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No s'ha pogut carregar el driver de MySQL", e);
        }
    }

    /**
     * Obté una connexió a la base de dades.
     */
    public static Connection getConnection() throws SQLException {
        // Log per depuració
        System.out.println("DBConnection: Intentant connectar a " + URL + " amb usuari " + USER); 
        Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
        if (conn != null) {
             System.out.println("DBConnection: Connexió reeixida!");
        } else {
             System.err.println("DBConnection: ERROR - No s'ha pogut obtenir la connexió.");
        }
        return conn;
    }
}
