import client.PrintClient;
import org.junit.Test;
import server.PrintServer;

public class IntegrationTest {

    @Test
    public void integration() {

        // run both server and client at the same time

        PrintServer server = new PrintServer();
        server.run();

        PrintClient client = new PrintClient();
        client.run();
    }
}
