package asymmetric.ecdsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.util.Base64;

public class Main
{
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Security.addProvider(new BouncyCastleProvider());

        CryptographyHelper helper = new CryptographyHelper();
        KeyPair keyPair = helper.generateKeys();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("Private key: " + Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        System.out.println("Public key: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));

        // Message to sign
        String message = "Hello, ECC!";
       byte[] signature =  helper.sign(privateKey, message);

       System.out.println("Verify Signature:" + helper.verifySignature(publicKey, signature, message));
    }
}
