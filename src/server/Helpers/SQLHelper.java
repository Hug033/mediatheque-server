package server.Helpers;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

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
