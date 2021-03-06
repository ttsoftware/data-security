package server;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PrintServer implements Runnable {

    public void run() {
        seed();

        try {
            Registry registry = LocateRegistry.createRegistry(30000);

            // create the service and perform the initial start-up
            registry.bind("printserver", new PrintServiceImpl("printserver", registry));
        }
        catch (ExportException ee) {
            System.out.println("Another server is already running on port 30000.");
        }
        catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new PrintServer().run();
    }

    /**
     * Seeds the database with initial data from the seed.sql file
     */
    private static void seed() {

        try {

            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:printservice.db");

            Statement stmt = c.createStatement();
            Stream<String> sqlStream = Files.lines(Paths.get("seed.sql"), StandardCharsets.UTF_8);
            String sql = sqlStream.collect(Collectors.joining());

            stmt.executeUpdate(sql);

            stmt.close();
            c.close();

        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
