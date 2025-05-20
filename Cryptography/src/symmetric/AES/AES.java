package symmetric.AES;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AES {
    private SecretKey secretKey;
    private SecureRandom random;
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private IvParameterSpec ivParams;


    //we have use the same vector IV both
    //for encryption and for decryption
    public AES(SecretKey secretKey, byte[] ivVector){
        random = new SecureRandom();
        try {
            this.secretKey = secretKey;
            System.out.println("Secret key:" + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            ivParams = new IvParameterSpec(ivVector);
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
    public AES()
    {
        random = new SecureRandom();
        try {
            secretKey = KeyGenerator.getInstance("AES").generateKey();
            System.out.println("Secret key:" + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            encryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            decryptCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            byte[] ivVector = new byte[encryptCipher.getBlockSize()];
            random.nextBytes(ivVector);
            ivParams = new IvParameterSpec(ivVector);
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    public String encryption(String plainText) {
        try {
            byte[] plainBytes = plainText.getBytes(StandardCharsets.UTF_8);
            byte[] cipherBytes = encryptCipher.doFinal(plainBytes);
            return Base64.getEncoder().encodeToString(cipherBytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    public String decryption(String base64CipherText) {
        try {
            byte[] cipherBytes = Base64.getDecoder().decode(base64CipherText);
            byte[] plainBytes = decryptCipher.doFinal(cipherBytes);
            return new String(plainBytes, StandardCharsets.UTF_8);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
