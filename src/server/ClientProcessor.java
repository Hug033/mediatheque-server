package server;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;
import java.util.List;

import static server.Helpers.SQLHelper.authenticate;

public class ClientProcessor implements Runnable{

    private Socket sock;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private Connection conn;

    public ClientProcessor(Socket pSock, Connection conn){
        this.conn = conn;
        sock = pSock;
    }

    // Le traitement lancé dans un thread séparé
    public void run(){
        System.out.println("Lancement du traitement de la connexion cliente");

        boolean closeConnexion = false;
        // Tant que la connexion est active, on traite les demandes
        while(!sock.isClosed()){

            try {
                //Ici, nous n'utilisons pas les mêmes objets que précédemment
                writer = new PrintWriter(sock.getOutputStream());
                reader = new BufferedInputStream(sock.getInputStream());

                //On attend la demande du client
                InputStream inputStream = sock.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                List<String> responses = (List<String>) objectInputStream.readObject();

                InetSocketAddress remote = (InetSocketAddress)sock.getRemoteSocketAddress();

                //On affiche quelques infos, pour le débuggage
                String debug = "";
                debug = "Thread : " + Thread.currentThread().getName() + ". ";
                debug += "Demande de l'adresse : " + remote.getAddress().getHostAddress() +".";
                debug += " Sur le port : " + remote.getPort() + ".\n";
                debug += "Received [" + responses.size() + "] messages from: " + sock;
                System.err.println("\n" + debug);

                //On traite la demande du client en fonction de la commande envoyée
                String toSend = "";

                switch(responses.get(0).toUpperCase()){
                    case "AUTH":
                        toSend = authenticate(responses.get(1), responses.get(2));
                        break;
                    case "LIST":
                        toSend = "NO DATA";
                        //toSend = authenticate(responses.get(1), responses.get(2));
                        break;
                    default :
                        toSend = "UNKNOWN_COMMAND";
                        break;
                }

                //On envoie la réponse au client
                writer.write(toSend);
                writer.flush();

                if(closeConnexion){
                    System.err.println("Fermeture de la connexion");
                    writer = null;
                    reader = null;
                    sock.close();
                    break;
                }
            }catch(SocketException e){
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