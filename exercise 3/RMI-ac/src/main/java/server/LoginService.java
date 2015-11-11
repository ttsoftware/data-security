package server;

import javafx.util.Pair;
import shared.model.User;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class LoginService {

    /**
     * Log user into system if they exist and given password is correct
     * @param user The user to be logged in
     * @return True if user exists and password is correct
     * @throws LoginException
     */
    public static boolean login(User user) throws LoginException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:printservice.db");

            ResultSet rs = c.createStatement().executeQuery(
                    "SELECT password, salt FROM Users WHERE username = '" + user.getName() + "';"
            );

            if (rs.next()) {
                String digest = rs.getString("password");
                String salt = rs.getString("salt");

                // Compute the new digest
                Pair<String, String> hash = HashingService.hash(
                        user.getPassword(),
                        HashingService.base64ToByte(salt)
                );

                if (!hash.getKey().equals(digest)) {
                    throw new LoginException();
                }
                return true;
            }

        } catch (ClassNotFoundException | SQLException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        throw new LoginException();
    }
}
