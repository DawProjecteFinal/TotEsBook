/**
 *
 * @author edinsonioc
 */
package cat.totesbook.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String DB_HOST = System.getenv("DB_HOST") != null ? System.getenv("DB_HOST") : "localhost";
    private static final String DB_PORT = System.getenv("DB_PORT") != null ? System.getenv("DB_PORT") : "3306";
    private static final String DB_NAME = System.getenv("DB_NAME") != null ? System.getenv("DB_NAME") : "totesbook";
    private static final String USER = System.getenv("DB_USER") != null ? System.getenv("DB_USER") : "totesuser";
    private static final String PASSWORD = System.getenv("DB_PASS") != null ? System.getenv("DB_PASS") : "totespass";

    private static final String URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No s'ha pogut carregar el driver de MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        System.out.println("Connectant a: " + URL + " amb usuari: " + USER); 
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}