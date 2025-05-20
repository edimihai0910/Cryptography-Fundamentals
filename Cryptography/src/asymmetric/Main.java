package asymmetric;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import symmetric.AES.AES;

import javax.crypto.*;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.MGF1ParameterSpec;
import java.util.HexFormat;

public class Main {

    public static void main(String[] args) throws NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        DiffieHellman dh = new DiffieHellman();


        BigInteger n = new BigInteger(Integer.toString(37));
        BigInteger g = new BigInteger(Integer.toString(13));

        dh.generatePrivateKeys(n,g );

        ModularArithmetic arithmetic = new ModularArithmetic();
        System.out.println("Advanced Modular Arithmetic");
        System.out.println("===GCD recurison===");
        System.out.println(arithmetic.gcd(21,11));

        System.out.println("===Modular Inverse===");
        System.out.println(arithmetic.modularInverse(12,31)); // 12*13 %31 ==1

        System.out.println("== Euclidean Modular Inverse --> a*x+b*y=gcd(a,b) ");
        System.out.printf("gcd=%s, x=%s,y=%s%n",
                arithmetic.extendedEuclideanAlgorithm(12,31)[0],
                arithmetic.extendedEuclideanAlgorithm(12,31)[1],
                arithmetic.extendedEuclideanAlgorithm(12,31)[2]);


        System.out.println("==RSA==");

        String message = "This is a cryptography related message";
        RSA rsa = new RSA();
        rsa.generateKeys(1024);

        BigInteger cipherText =rsa.encryptMessage(message);
        System.out.println("Encrypted message: "+ cipherText);
        System.out.println("Decrypted message: "+ rsa.decryptMessage(cipherText));

        //we don't use RSA for encryption and decryption
        //reason: RSA is dealing with extremely large numbers : exponents
        // AES -session key - private key for aes
        // RSA (or ECC) to encrypt this session key

        Security.addProvider(new BouncyCastleProvider());

        //generate 2048 bits long RSA keys
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            KeyPair pair = generator.generateKeyPair();
            //decrypt the session key (
            PrivateKey privateKey = pair.getPrivate();
            //encrypt the session key (public key for aes)
            PublicKey publicKey = pair.getPublic();
            System.out.println("Private Key: "+ privateKey.getEncoded());
            System.out.println("Public Key: "+ publicKey);

            //Client Side
            // we encrypt the message with the session key
            // and send the session key in a encrypted format
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256); //private key is 256 bits long
            SecretKey secretKey = keyGenerator.generateKey(); //This key is used to encrypt the actual message
            System.out.println("AES Secret Key: "+ HexFormat.of().formatHex(secretKey.getEncoded()));

            //initialization vector - we use CBC -- we want split the plaintext in 16 bytes chunks
            // XOR operation for encryption
            SecureRandom secureRandom = new SecureRandom();
            byte[] iv = new byte[16];
            secureRandom.nextBytes(iv);

            AES aes = new AES(secretKey, iv);
            String ciperText = aes.encryption(message);
            System.out.println("Cipher Text: "+ ciperText);

            //use the RSA public key for encrypting the session key
            //OAEP -optical asymmetric encryption padding - most popular approach when dealing with RSA
            //MGF - mask generation function -
            Cipher encryptionCipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
            OAEPParameterSpec spec = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, publicKey, spec);
            byte [] encryptedSessionKey = encryptionCipher.doFinal(secretKey.getEncoded());

            System.out.println("Encrypted session key: "+ HexFormat.of().formatHex(encryptedSessionKey));

            //we send from client to server
            //cipherText, encrypted session key, RSA public key, iv

            //Server SIDE
            Cipher decryptionCipher = Cipher.getInstance("RSA/NONE/OAEPWithSHA256AndMGF1Padding");
            OAEPParameterSpec spec2 = new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
            decryptionCipher.init(Cipher.DECRYPT_MODE, privateKey, spec2);
            byte[] decryptedSessionKey = decryptionCipher.doFinal(encryptedSessionKey);
            System.out.println("Decrypted session key: "+ HexFormat.of().formatHex(decryptedSessionKey));

            SecretKey decryptedSecretKey = new SecretKeySpec(decryptedSessionKey, "AES");
            //AES with this session key to decrypt the cipher text
            AES decyptAES = new AES(decryptedSecretKey, iv);
            System.out.println("Decrypted message:"+decyptAES.decryption(ciperText));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
