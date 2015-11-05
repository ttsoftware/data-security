package server;

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
    private final static int ITERATION_NUMBER = 10000;  // hashing rounds

    public static synchronized HashingService getInstance() {
        if (instance == null) {
            instance = new HashingService();
        }
        return instance;
    }

    public Pair<String, String> hash(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Salt generation 64 bits long
        byte[] salt = new byte[8];
        new Random().nextBytes(salt);
        return hash(password, salt);
    }

    public Pair<String, String> hash(String password, byte[] salt) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(salt);
        byte[] digestedPassword = digest.digest(password.getBytes("UTF-8"));

        for (int i = 0; i < ITERATION_NUMBER; i++) {
            digest.reset();
            digestedPassword = digest.digest(digestedPassword);
        }

        // Returns the hashed password and the salt
        return new Pair<String, String>(byteToBase64(digestedPassword), byteToBase64(salt));
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
