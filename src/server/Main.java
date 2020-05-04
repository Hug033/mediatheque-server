package server;

import server.Helpers.SQLHelper;

import java.sql.Connection;
import java.sql.SQLException;

public class Main implements AutoCloseable {

    private static Connection conn;

    public static void main(String[] args) {
        conn = SQLHelper.connect(); // Connexion à la base de données

        TimeServer ts = new TimeServer("127.0.0.1", 2345, conn);
        ts.open(); // Lancement du serveur
    }

    @Override
    public void close() throws Exception {
        conn.close();
        System.out.println("Disconnected to the PostgreSQL server successfully");
    }
}
