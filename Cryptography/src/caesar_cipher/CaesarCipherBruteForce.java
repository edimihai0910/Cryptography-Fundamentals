package caesar_cipher;

import caesar_cipher.language_detector.LanguageDetector;

public class CaesarCipherBruteForce {

    private String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ ";

    public void crack(String cipherText) {
        for (int key = 0; key < ALPHABET.length(); key++) {
            String plainText = "";

            for (int i = 0; i < cipherText.length(); i++) {
                char character = cipherText.charAt(i);

                int charIndex = ALPHABET.indexOf(character);
                int decryptedIndex = Math.floorMod(charIndex - key, ALPHABET.length());

                plainText += ALPHABET.charAt(decryptedIndex);
            }

            //optional this to detect the language
            LanguageDetector languageDetector = new LanguageDetector();
            if(languageDetector.isEnglish(plainText))
            {
                System.out.format("Cracking Caeser Cipher with %s key and the result is %s\n", key, plainText);

            }
        }

    }

}
