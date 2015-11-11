import client.PrintClient;
import org.junit.Test;
import server.PrintServer;

import java.io.IOException;
import java.nio.file.*;

public class IntegrationTest {

    @Test
    public void integration() throws IOException {

        // remove the database file

        Path databasePath = FileSystems.getDefault().getPath("printservice-junit.db");

        try {
            Files.delete(databasePath);
        }
        catch (NoSuchFileException e) {
            // The database is going to be created
        }

        // run both server and client at the same time

        PrintServer server = new PrintServer();
        server.run();

        PrintClient client = new PrintClient();
        client.run();
    }
}
