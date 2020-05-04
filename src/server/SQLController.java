package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLController {
    private static final String url = "jdbc:postgresql://localhost/bd_mediatheque";
    private static final String user = "user_media";
    private static final String password = "tux";
    private static Connection conn = null;

    public static Connection connect() {

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
