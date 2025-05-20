package hashing;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    // MD5 uses 128 bits - 32 hexa caracters
    public String digest(String input) throws NoSuchAlgorithmException {
        MessageDigest md = null;
        byte[] mdByte = null;
        String hexaHash = "";

        // we are using MD5 algorithm
        md=MessageDigest.getInstance("MD5");
        //we need the byte array when dealing with cryptography
        mdByte = md.digest(input.getBytes());

        // 1 hexa character = 4 bits
        //we convert the message hast into hexadecimal (16 characters)
        //problem is that it may omit leading zeros
        hexaHash = new BigInteger(1, mdByte).toString(16);

        while (hexaHash.length() < 32) {
            hexaHash = "0" + hexaHash;
        }


        return hexaHash;
    }


}
