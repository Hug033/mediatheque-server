package server;

import server.Helpers.SQLHelper;

public class MainConnexion {

    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 2345;

        TimeServer ts = new TimeServer(host, port, SQLHelper.connect());
        ts.open();

        System.out.println("Serveur initialisé.");

        for(int i = 0; i < 5; i++){
            Thread t = new Thread(new ClientConnexion(host, port));
            t.start();
        }
    }
}