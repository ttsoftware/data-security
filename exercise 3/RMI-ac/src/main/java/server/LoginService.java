package server;

import javafx.util.Pair;
import shared.model.User;
import shared.service.DatabaseService;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;

public class LoginService {

    /**
     * Log user into system if they exist and given password is correct
     * @param user The user to be logged in
     * @return True if user exists and password is correct
     * @throws LoginException
     */
    public static boolean login(User user) throws LoginException {

        try {

            List<User> users = DatabaseService.getDao(User.class).queryForEq("name", user.getName());

            if (users.size() == 1) {
                User dbUser = users.get(0);

                String digest = dbUser.getPassword();
                String salt = dbUser.getSalt();

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
        } catch (SQLException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }

        throw new LoginException();
    }
}
