package server;

import server.Helpers.SQLHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class Main  {

    private static Connection conn;

    public static void main(String[] args) {
        try {
            conn = SQLHelper.connect(); // Connexion à la base de données

            TimeServer ts = new TimeServer("127.0.0.1", 2345, conn);
            ts.open(); // Lancement du serveur

            conn.close();
        } catch (SQLException e) {
            try {
                throw e;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
