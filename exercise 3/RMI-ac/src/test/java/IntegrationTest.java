import client.PrintClient;
import org.junit.Test;
import server.PrintServer;

import java.io.IOException;
import java.nio.file.*;

public class IntegrationTest {

    @Test
    public void integration() throws IOException {

        // run both server and client at the same time

        PrintServer server = new PrintServer();
        server.run();

        PrintClient client = new PrintClient();
        client.run();
    }
}
