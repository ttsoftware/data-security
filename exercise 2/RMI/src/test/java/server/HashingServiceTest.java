package server;

import javafx.util.Pair;
import org.junit.Test;
import server.HashingService;

import static org.junit.Assert.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class HashingServiceTest {

    @Test
    public void hashTest() throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String password = "password";
        Pair<String, String> hash = HashingService.getInstance().hash(password);

        System.out.println(hash.getKey());
        System.out.println(hash.getValue());

        assertNotEquals(password, hash.getKey());
        assertTrue(hash.getKey().equalsIgnoreCase("\n"));
    }
}
