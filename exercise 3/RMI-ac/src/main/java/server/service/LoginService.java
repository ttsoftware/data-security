package server.service;

import javafx.util.Pair;
import shared.exception.UserAuthenticationException;
import server.model.User;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class LoginService {

    public static User login(User user) throws UserAuthenticationException {
        return login(user.getName(), user.getPassword());
    }

    /**
     * Log user into system if they exist and given password is correct
     * @param username
     * @param password
     * @return True if user exists and password is correct
     * @throws UserAuthenticationException
     */
    public static User login(String username, String password) throws UserAuthenticationException {

        try {

            List<User> users = DatabaseService.getDao(User.class).queryForEq("name", username);

            if (users.size() == 1) {
                User dbUser = users.get(0);

                String digest = dbUser.getPassword();
                String salt = dbUser.getSalt();

                // Compute the new digest
                Pair<String, String> hash = HashingService.hash(
                        password,
                        HashingService.base64ToByte(salt)
                );

                if (!hash.getKey().equals(digest)) {
                    throw new UserAuthenticationException();
                }
                return dbUser;
            }
        } catch (SQLException | NoSuchAlgorithmException | IOException e) {
            throw new UserAuthenticationException(e);
        }

        throw new UserAuthenticationException();
    }
}
