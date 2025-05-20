package hashing;

import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        MD5 md5 = new MD5();
        String message = "Hashing algorithms are crucial in cryptography!";
        String message2 = "SARPILI";
        System.out.println(md5.digest(message2));


        SHA256 sha256 = new SHA256();
        System.out.println(sha256.digest(message));
        System.out.println(sha256.digest(message).length());
    }
}
