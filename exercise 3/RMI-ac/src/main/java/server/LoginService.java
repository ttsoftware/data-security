package server;

import javafx.util.Pair;
import shared.exception.UserAuthenticationException;
import shared.model.User;
import shared.service.DatabaseService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

public class LoginService {

    /**
     * Log user into system if they exist and given password is correct
     * @param user The user to be logged in
     * @return True if user exists and password is correct
     * @throws UserAuthenticationException
     */
    public static boolean login(User user) throws UserAuthenticationException {

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

                System.out.println(user.getPassword());
                System.out.println(digest);
                System.out.println(hash.getKey());
                System.out.println(salt);
                System.out.println(user.getSalt());

                if (!hash.getKey().equals(digest)) {
                    throw new UserAuthenticationException();
                }
                return true;
            }
        } catch (SQLException | NoSuchAlgorithmException | IOException e) {
            throw new UserAuthenticationException(e);
        }

        throw new UserAuthenticationException();
    }
}
