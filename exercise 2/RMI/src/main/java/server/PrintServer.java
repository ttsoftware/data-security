package server;

import javafx.util.Pair;
import model.User;
import shared.HashingService;

import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PrintServer {

    public static void main(String[] args) throws RemoteException, AlreadyBoundException, LoginException {
        initializeDatabase();

        Registry registry = LocateRegistry.createRegistry(30000);

        // create the service and perform the initial start-up
        registry.bind("printserver", new PrintServant("printserver", registry));
    }

    private static void initializeDatabase() {

        try {
            User user = new User("admin", "password");

            Pair<String, String> passwordHash = HashingService.getInstance().hash(user.getPassword());

            String password = passwordHash.getKey();
            String salt = passwordHash.getValue();

            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:printservice.db");

            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users " +
                    "(id INTEGER PRIMARY KEY," +
                    " username VARCHAR(50) UNIQUE NOT NULL, " +
                    " password VARCHAR(256) NOT NULL, " +
                    " salt VARCHAR(256) NOT NULL)";
            stmt.executeUpdate(sql);

            sql = "INSERT OR IGNORE INTO Users (username, password, salt) " +
                    "VALUES ('" + user.getUsername() + "', '" + password + "', '" + salt + "');";
            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
