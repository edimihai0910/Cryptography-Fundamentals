import AES.AES;
import caesar_cipher.CaesarCipher;
import caesar_cipher.CaesarCipherBruteForce;
import caesar_cipher.FrequencyAnalysis;
import caesar_cipher.language_detector.LanguageDetector;
import des.DES;
import java.io.UnsupportedEncodingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import one_time_pad.OneTimePad;
import one_time_pad.RandomGenerator;
import vigenere_cipher.VigenereCipher;

public class Main {

    public static void caesarCipherMethod(String message, int key)
    {
        CaesarCipher caesarCipher = new CaesarCipher();

        String encrytedMessage = caesarCipher.encrypt(message,key);

        System.out.println("===Encrypted message with Caesar Cipher===");
        System.out.println(encrytedMessage);

        String decryptedMessage = caesarCipher.decrypt(encrytedMessage, key);

        System.out.println("===Decrypted message===");
        System.out.println(decryptedMessage);
    }

    public static void main(String[] args) throws IllegalBlockSizeException, UnsupportedEncodingException, BadPaddingException {
        String plainText2= "Mesaj cryptat ascuns pe care nu il stie nimeni";
        String plainText = "My name is Kevin and I like software engineering!";
        String plainText3 = "Cryptography is quite important";
        String cipherText = "PHVDNCFUASWDWCDVFXQVCSHCFDUHCQXCMOCVWMHCQMPHQM";
        String cipherText2 = "PACQDPHCLVCNHYLQCDQGCLCOLNHCVRIWZDUHCHQJLQHHULQJC";

        String vigenereKey = "table";

        caesarCipherMethod(plainText, 3);

        CaesarCipherBruteForce cracker = new CaesarCipherBruteForce();
        cracker.crack(cipherText2);

        System.out.println("=== ANALYSIS ===");
        FrequencyAnalysis analysis = new FrequencyAnalysis();
        analysis.showFrequencies(plainText2);
        analysis.crack(cipherText);

        System.out.println("=== Language Detector ===");

        //used in brute force
        LanguageDetector detector =  new LanguageDetector();
        System.out.println(detector.isEnglish(plainText));
        System.out.println(detector.isEnglish(plainText2));

        //VIGENERE CIPHER TEXT
        System.out.println("Vigenere cipher encryption:");
        VigenereCipher vigenereCipher = new VigenereCipher();
        String vigenereCipherText = vigenereCipher.encypt(plainText3,vigenereKey);
        System.out.println(vigenereCipherText);

        System.out.println("Vigenere cipher decryption:");
        String decryptedVigenereCipherText = vigenereCipher.decrypt(vigenereCipherText, vigenereKey);
        System.out.println(decryptedVigenereCipherText);


        //ONE TIME PAD
        String plainText4 = "Cryptography is important in bitcoin and other cryptocurrencies";

        RandomGenerator generator = new RandomGenerator();
        int[] key = generator.generate(plainText4.length());
        System.out.print("keys: ");
        for (int i = 0; i < key.length; i++) {
            System.out.print(String.format("%s ", key[i]));
        }

        OneTimePad oneTimePad = new OneTimePad();
        String otpCipherText = oneTimePad.encrypt(plainText4, key);
        System.out.println("\nOTP Encryption:" + otpCipherText);
        String otpPlainText = oneTimePad.decrypt(otpCipherText, key);
        System.out.println("OTP Decryption:" + otpPlainText);

        System.out.println("=== Data Encryption Standard === ");
        DES des = new DES();

        String desCypher = des.encryption(plainText4);
        System.out.println("DES Encryption: "+desCypher);
        System.out.println("DES Decryption: "+ des.decryption(desCypher));


        System.out.println("=== ADVANCED Encryption Standard === ");

        AES aes = new AES();
        String aesCypher = aes.encryption(plainText4);
        System.out.println("AES Encryption: "+aesCypher);
        System.out.println("AES Decryption: "+ aes.decryption(aesCypher));
    }
}