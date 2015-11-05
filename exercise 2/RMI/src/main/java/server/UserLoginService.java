package server;

import javafx.util.Pair;
import model.User;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserLoginService implements LoginService<User> {

    private static UserLoginService instance;

    public static synchronized UserLoginService getInstance() {
        if (instance == null) {
            instance = new UserLoginService();
        }
        return instance;
    }

    /**
     * Log user into system if they exist and given password is correct
     * @param user The user to be logged in
     * @return True if user exists and password is correct
     * @throws LoginException
     */
    public boolean login(User user) throws LoginException {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:printservice.db");

            ResultSet rs = c.createStatement().executeQuery(
                    "SELECT password, salt FROM Users WHERE username = '" + user.getUsername() + "';"
            );

            if (rs.next()) {
                String digest = rs.getString("password");
                String salt = rs.getString("salt");

                // Compute the new digest
                Pair<String, String> hash = HashingService.getInstance().hash(
                        user.getPassword(),
                        HashingService.getInstance().base64ToByte(salt)
                );

                return hash.getKey().equals(digest);
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        throw new LoginException();
    }
}
