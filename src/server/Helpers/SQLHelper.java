package server.Helpers;

import server.Media;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import com.google.gson.*;
import java.util.Date;

public class SQLHelper {
    private static final String url = "jdbc:postgresql://localhost/bd_mediatheque";
    private static final String user = "user_media";
    private static final String password = "tux";
    private static Connection conn = null;
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static Connection connect() {

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Permet d'authentifier un utilisateur et retourne un token
    public static List<String> authenticate(String login, String password) {
        PreparedStatement requete = null;
        ResultSet resultat = null;
        List<String> responses = new ArrayList<String>();

        try {
            requete = conn.prepareStatement("SELECT * FROM users WHERE  login = ? AND password = ?");
            requete.setString(1, login);
            requete.setString(2, password);
            resultat = requete.executeQuery();

            if (resultat.next()) {
                String token = generateToken();
                LocalDate date = LocalDate.now();
                String userState = resultat.getString("state");
                System.out.println(userState);
                responses.add("OK");

                if (userState != null && userState != "0") {
                    requete = conn.prepareStatement("INSERT INTO token(id_user, token, validate) values (?, ?, ?)");
                    requete.setInt(1, Integer.parseInt(resultat.getString("person_id"))); // 1 correspond au 1er point d'interrogation
                    requete.setString(2, token); // 2 correspond au 2ème point d'interrogation
                    requete.setString(3, date.plusDays(3).toString()); // 3 correspond au 3ème point d'interrogation
                    requete.executeUpdate();
                    responses.add(token);
                } else
                    responses.add("--");

                responses.add(userState);

                return responses;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de l'authentification du client");
        }

        responses.clear();
        responses.add("AUTH_FAIL");
        responses.add("--");
        responses.add("--");

        return responses;
    }

    // Permte de faire une requete sur une liste de manière générique
    public static String getUsers() {
        PreparedStatement requete = null;
        ResultSet resultat = null;

        try {
            requete = conn.prepareStatement("SELECT * FROM users INNER JOIN person ON users.person_id = person.id ORDER BY state");
            resultat = requete.executeQuery();
            List<User> allUsers = new ArrayList<User>();
            while (resultat.next()) {
                User u = new User(new byte[5],
                        resultat.getString("firstname"),
                        resultat.getString("lastname"),
                        resultat.getString("birthday"),
                        resultat.getString("login"),
                        resultat.getString("password"),
                        resultat.getString("phone"),
                        resultat.getString("registration"),
                        Integer.parseInt(resultat.getString("state")),
                        Integer.parseInt(resultat.getString("id"))
                );
                allUsers.add(u);
            }
            return new Gson().toJson(allUsers);
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de l'authentification du client");
        }
        return "";
    }

    // Permte de faire une requete sur une liste de manière générique
    public static String addUser(String email, String password, String nom, String prenom, String telephone, String status, String photo, String date) {
        PreparedStatement requete = null;
        ResultSet resultat;

        try {
            requete = conn.prepareStatement("INSERT INTO person (firstname, lastname, birthday) VALUES (?, ?, ?)");
            requete.setString(1, prenom);
            requete.setString(2, nom);
            requete.setString(3, date);
            requete.executeUpdate();

            PreparedStatement requete2 = conn.prepareStatement("SELECT id FROM person ORDER BY id DESC LIMIT 1");
            resultat = requete2.executeQuery();
            resultat.next();

            requete = conn.prepareStatement("INSERT INTO users (person_id, profile, login, password, phone, registration, state) VALUES (?, ?, ?, ?, ?, ?, ?)");
            byte[] temp =  photo.getBytes(StandardCharsets.UTF_8);
            requete.setInt(1, Integer.parseInt(resultat.getString("id")));
            String datetemp = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
            requete.setBytes(2, temp);
            requete.setString(3, email);
            requete.setString(4, password);
            requete.setString(5, telephone);
            requete.setString(6, datetemp);
            requete.setInt(7, Integer.parseInt(status));
            requete.executeUpdate();

            return "OK";
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de l'authentification du client");
        }
        return "";
    }

    // Permte de faire une requete sur une liste de manière générique
    public static String addReserve(String dateDeb, String dateFin, String ref) {
        PreparedStatement requete = null;
        ResultSet resultat;

        try {
            requete = conn.prepareStatement("INSERT INTO borrowing (startDate, endDate, condition, borrower_id, media_ref) VALUES (?, ?, ?, ?, ?)");
            requete.setString(1, dateDeb);
            requete.setString(2, dateFin);
            requete.setInt(3, 1);
            requete.setInt(4, 1);
            requete.setString(5, ref);
            requete.executeUpdate();

            return "OK";
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de l'authentification du client");
        }
        return "";
    }

    public static String changePassword(String user, String hash) {
        PreparedStatement requete = null;
        int resultat;

        try {
            requete = conn.prepareStatement("UPDATE users SET password = ? WHERE person_id = ?");
            requete.setString(1, hash);
            requete.setInt(2, Integer.parseInt(user));
            resultat = requete.executeUpdate();
            System.out.println(resultat);
            if(resultat > -1)
                return "OK";
            else
                return "ERROR";
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de l'authentification du client");
        }
        return "";
    }

    public static String changeStatus(String user, String state) {
        PreparedStatement requete = null;
        int resultat;

        try {
            requete = conn.prepareStatement("UPDATE users SET state = ? WHERE person_id = ?");
            requete.setInt(1, Integer.parseInt(state));
            requete.setInt(2, Integer.parseInt(user));
            resultat = requete.executeUpdate();
            System.out.println(resultat);
            if(resultat > -1)
                return "OK";
            else
                return "ERROR";
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de l'authentification du client");
        }
        return "";
    }

    // Permet de faire une requete sur une liste de manière générique
    public static String genericList(String type) {
        PreparedStatement requete = null;
        ResultSet resultat = null;

        try {
            String req = "";
            System.out.println(type);
            if(type.equals("DVD"))
                req = "SELECT * FROM media INNER JOIN theme ON media.theme = theme.id INNER JOIN dvd ON media.ref = dvd.media_ref INNER JOIN person ON dvd.director = person.id";
            else if(type.equals("CD"))
                req = "SELECT * FROM media INNER JOIN theme ON media.theme = theme.id INNER JOIN cd ON media.ref = cd.media_ref INNER JOIN person ON cd.composer = person.id";
            else
                req = "SELECT * FROM media INNER JOIN theme ON media.theme = theme.id INNER JOIN book ON media.ref = book.media_ref INNER JOIN person ON book.author = person.id";

            requete = conn.prepareStatement(req);
            resultat = requete.executeQuery();
            List<Media> allMedia = new ArrayList<Media>();
            while (resultat.next()) {
                PreparedStatement requete2 = conn.prepareStatement("SELECT * FROM borrowing WHERE media_ref = ? ORDER BY id");
                requete2.setString(1, resultat.getString("ref"));

                ResultSet resultat2 = requete2.executeQuery();
                int condition = 0;
                if(resultat2.next()) {
                    condition = Integer.parseInt(resultat2.getString("condition"));
                }

                Media m = new Media(new byte[5],
                        resultat.getString("ref"),
                        resultat.getString("title"),
                        resultat.getString("firstname"),
                        resultat.getString("description"),
                        Integer.parseInt(resultat.getString("score")),
                        Integer.parseInt(resultat.getString("nb_rate")),
                        condition
                );
                allMedia.add(m);
            }
            return new Gson().toJson(allMedia);
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de l'authentification du client");
        }
        return "";
    }

    // Permet de vérifier si un token est valide
    public boolean checkToken(String token) {
        PreparedStatement requete = null;
        ResultSet resultat = null;
        try {
            requete = conn.prepareStatement("SELECT * FROM token WHERE token  = ? AND date >= ?");
            requete.setString(1, token);
            requete.setString(2, LocalDateTime.now().toString());
            resultat = requete.executeQuery();

            if (resultat.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace(); // affichage de la trace du programme (utile pour le débogage)
            System.err.println("Erreur lors de la vérification du token");
        }
        return false;
    }

    public static Connection close() {

        try {
            conn.close();
            System.out.println("Connection close.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Permet de créer un token de connexion
    private static String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}
