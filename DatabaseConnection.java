

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.sql.SQLException;



public class DatabaseConnection {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/memoiredb";
    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "2003";
    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            } catch (SQLException e) {
                System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            }
        }
        return connection;
    }
}



