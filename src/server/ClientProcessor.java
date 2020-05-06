package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static server.Helpers.SQLHelper.*;

public class ClientProcessor implements Runnable {

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private Connection conn;

    public ClientProcessor(Socket pSock, Connection conn) {
        this.conn = conn;
        sock = pSock;
    }

    // Le traitement lancé dans un thread séparé
    public void run() {
        System.out.println("Lancement du traitement de la connexion cliente");

        boolean closeConnexion = false;
        // Tant que la connexion est active, on traite les demandes
        while (!sock.isClosed()) {

            try {
                //Ici, nous n'utilisons pas les mêmes objets que précédemment
                reader = new BufferedInputStream(sock.getInputStream());

                //On attend la demande du client
                InputStream inputStream = sock.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                List<String> responses = (List<String>) objectInputStream.readObject();
                System.out.println(responses);
                InetSocketAddress remote = (InetSocketAddress) sock.getRemoteSocketAddress();

                //On affiche quelques infos, pour le débuggage
                String debug = "";
                debug = "Thread : " + Thread.currentThread().getName() + ". ";
                debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() + ".";
                debug += " Sur le port : " + remote.getPort() + ".\n";
                debug += "Received [" + responses.size() + "] messages from: " + sock;
                System.err.println("\n" + debug);

                //On traite la demande du client en fonction de la commande envoyée
                OutputStream outputStream = sock.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
                List<Serializable> toSend = new ArrayList<>();

                switch (responses.get(0).toUpperCase()) {
                    case "AUTH":
                        List<String> auth = authenticate(responses.get(1), responses.get(2));
                        toSend.add(auth.get(0));
                        toSend.add(auth.get(1));
                        toSend.add(auth.get(2));
                        break;
                    case "LIST":
                        toSend.add(genericList(responses.get(1)));
                        break;
                    case "USERS":
                        toSend.add(getUsers());
                        break;
                    case "CHANGE_PASSWORD":
                        toSend.add(changePassword(responses.get(1), responses.get(2)));
                        break;
                    case "CHANGE_STATUS":
                        toSend.add(changeStatus(responses.get(1), responses.get(2)));
                        break;
                    case "ADD_USER":
                        toSend.add(addUser(responses.get(1)
                                , responses.get(2)
                                , responses.get(3)
                                , responses.get(4)
                                , responses.get(5)
                                , responses.get(6)
                                , responses.get(7)
                                , responses.get(8)

                        ));
                        break;
                    default:
                        toSend.add("UNKNOWN_COMMAND");
                        break;
                }

                objectOutputStream.writeObject(toSend);
                objectOutputStream.flush();
                objectOutputStream.close();

                if (closeConnexion) {
                    System.err.println("Fermeture de la connexion");
                    reader = null;
                    sock.close();
                    break;
                }
            } catch (SocketException e) {
                System.err.println("Interruption de la connexion");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}