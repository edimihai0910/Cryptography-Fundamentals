package des;

import java.io.UnsupportedEncodingException;
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

public class DES {

    private SecretKey secretKey;
    private SecureRandom random; // to generate criptographily secure random numbers
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    private IvParameterSpec ivParams;

    public DES() {
        try {
            secretKey = KeyGenerator.getInstance("DES").generateKey();
            System.out.println("Secret key:" + Base64.getEncoder().encodeToString(secretKey.getEncoded()));

            random = new SecureRandom();
            encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

            byte[] ivVector = new byte[encryptCipher.getBlockSize()];
            random.nextBytes(ivVector);
            ivParams = new IvParameterSpec(ivVector);
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivParams);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public String encryption(String plainText) {
        byte[] bytes = plainText.getBytes();
        byte[] cipherText = null;

        try {
            cipherText = encryptCipher.doFinal(bytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(cipherText);
    }

    public String decryption(String cipherText) throws IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        byte[] bytes = Base64.getDecoder().decode(cipherText.getBytes());
        byte[] plainText = null;

        plainText = decryptCipher.doFinal(bytes);

        return new String(plainText, "UTF8");
    }

}
