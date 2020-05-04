package server;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ClientConnexion implements Runnable{

    private Socket connexion = null;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    //Notre liste de commandes. Le serveur nous répondra différemment selon la commande utilisée.
    private String[] listCommands = {"COUCOU", "NONE"};
    private static int count = 0;
    private String name = "Client-";

    public ClientConnexion(String host, int port){
        name += ++count;
        try {
            connexion = new Socket(host, port);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void run(){

        //nous n'allons faire que 10 demandes par thread...
        for(int i =0; i < 10; i++){
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {


                OutputStream outputStream = connexion.getOutputStream();
                reader = new BufferedInputStream(connexion.getInputStream());
                //On envoie la commande au serveur
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                // Liste des messages a envoyer
                List<String> messages = new ArrayList<>();
                messages.add("auth");
                messages.add("login");
                messages.add("test");
                messages.add("fsdf54s9");
                objectOutputStream.writeObject(messages);
                objectOutputStream.flush();

                //On attend la réponse
                String response = read();
                System.out.println("\t * " + name + " : Réponse reçue " + response);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Méthode qui permet d'envoyer des commandeS de façon aléatoire
    private String getCommand(){
        Random rand = new Random();
        return listCommands[rand.nextInt(listCommands.length)];
    }

    //Méthode pour lire les réponses du serveur
    private String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }
}