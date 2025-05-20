package asymmetric.ecdsa;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class CryptographyHelper {

    // public key (x,y) and the private key (256 bit)
    public KeyPair generateKeys() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            //random 160 bit numbers
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG"); // SHA1 PRNG - Pseudo Random Number Generator
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime256v1");
            keyGen.initialize(ecSpec, random); // is going to use sha1prng in order to generate random numbers

            return keyGen.generateKeyPair();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    //private key - signature generation
    //public key - verifying
    public byte[] sign(PrivateKey privateKey, String message) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException {
        byte[] messageBytes = message.getBytes();

        // Sign the message
        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(messageBytes);
        byte[] signature = ecdsaSign.sign();

        System.out.println("Signature (Base64): " + Base64.getEncoder().encodeToString(signature));

        return signature;
    }


    public boolean verifySignature(PublicKey publicKey, byte[] signature, String message) throws SignatureException, InvalidKeyException, NoSuchAlgorithmException {
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(message.getBytes());
        return ecdsaVerify.verify(signature);
    }

}
