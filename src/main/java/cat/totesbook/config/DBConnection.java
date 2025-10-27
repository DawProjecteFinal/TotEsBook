/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cat.totesbook.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author edinsonioc
 */
public class DBConnection {

    // Aquestes dades han de coincidir amb les del teu docker-compose.yml
    private static final String URL = "jdbc:mysql://totesbook-db:3306/totesbookdb";
    private static final String USER = "totesuser"; // L'usuari 'MYSQL_USER' del docker-compose
    private static final String PASSWORD = "totespass"; // La 'MYSQL_PASSWORD' del docker-compose

    // Bloc estàtic per carregar el driver de MySQL
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No s'ha pogut carregar el driver de MySQL", e);
        }
    }

    /**
     * Obté una connexió a la base de dades.
     * @return una connexió SQL
     * @throws SQLException si hi ha un error de connexió
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
