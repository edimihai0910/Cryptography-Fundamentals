package hashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256 {

    // 256 bits - 64 hexa characters (output hash)
    //that Bitcoin miners are relying highly in sha256

    public String digest(String input) {
        MessageDigest messageDigest;
        byte[] messageDigestBytes;
        String hexaHash="";

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigestBytes = messageDigest.digest(input.getBytes());

            hexaHash = new BigInteger(1, messageDigestBytes).toString(16);

            while (hexaHash.length() < 64) {
                hexaHash = "0" + hexaHash;
            }

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return hexaHash;
    }
}
