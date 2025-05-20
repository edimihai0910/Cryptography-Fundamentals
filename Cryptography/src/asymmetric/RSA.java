package asymmetric;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

    // this is the e paramater - for encryption
    private BigInteger publicKey;
    // this is the d paramater - for decryption
    private BigInteger privateKey;
    // n= p*q;
    private BigInteger n;
    private final SecureRandom random;

    public RSA() {
        this.random = new SecureRandom();
    }

    public void generateKeys(int keyDigits){
        // p is a large number
        BigInteger p = BigInteger.probablePrime(keyDigits, random);
        // q is a large number
        BigInteger q = BigInteger.probablePrime(keyDigits, random);

        BigInteger n = p.multiply(q);

        //Euler's totient phi function (p-1)(q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        //GCD - to find e and gcd(phi,e)=1 -- to be comprime with e
        BigInteger e = generatPublicKey(phi);

        //modular inverse of e - mod phi (EEA)
        BigInteger d = e.modInverse(phi);

        this.publicKey = e;
        this.privateKey = d;
        this.n = n;
    }

    private BigInteger generatPublicKey(BigInteger phi) {
        BigInteger e = new BigInteger(phi.bitLength(), random);
        while (!e.gcd(phi).equals(BigInteger.ONE)) {
            e = new BigInteger(phi.bitLength(), random);
        }

        return e;
    }


    public BigInteger encryptMessage(String message) {

        return encrypt(publicKey, n, message);
    }

    public String decryptMessage(BigInteger message) {
        return decrypt(privateKey, n, message);
    }

    private BigInteger encrypt(BigInteger publicKey, BigInteger n, String message) {
        byte[] messageBytes = message.getBytes();
        BigInteger encryptedMessage = new BigInteger(messageBytes);

        //we have to use modular exponantion
        //the ciper text = message ^e mod n
        return encryptedMessage.modPow(publicKey, n);
    }

    private String decrypt(BigInteger privateKey, BigInteger n, BigInteger message) {
        // we use modular exponantion for decryption
        // cipher^d mod n = plain text
        BigInteger decryptedMessage = message.modPow(privateKey, n);

        //end up with a string
        return new String(decryptedMessage.toByteArray());
    }
}
