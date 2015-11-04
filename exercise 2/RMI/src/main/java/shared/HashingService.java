package shared;

import javafx.util.Pair;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class HashingService {

    private static HashingService instance;
    private final static int ITERATION_NUMBER = 1000;

    public static synchronized HashingService getInstance() {
        if (instance == null) {
            instance = new HashingService();
        }
        return instance;
    }

    public Pair<String, String> hash(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] salt = new byte[8];
        new Random().nextBytes(salt);
        return hash(password, salt);
    }

    public Pair<String, String> hash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Salt generation 64 bits long
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));

        for (int i = 0; i < ITERATION_NUMBER; i++) {
            digest.reset();
            input = digest.digest(input);
        }

        // Returns the hashed password and the salt
        return new Pair<String, String>(byteToBase64(input), byteToBase64(salt));
    }

    /**
     * From a base 64 representation, returns the corresponding byte[]
     *
     * @param data String The base64 representation
     * @return byte[]
     * @throws IOException
     */
    public byte[] base64ToByte(String data) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(data);
    }

    /**
     * From a byte[] returns a base 64 representation
     *
     * @param data byte[]
     * @return String
     */
    public String byteToBase64(byte[] data) {
        BASE64Encoder endecoder = new BASE64Encoder();
        return endecoder.encode(data);
    }
}
