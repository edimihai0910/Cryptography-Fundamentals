package symmetric.vigenere_cipher;

public class VigenereCipher {

    private static final String ALPHABET = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public String encypt(String plainText, String key) {
        plainText = plainText.toUpperCase();
        key = key.toUpperCase();

        String cipherText = "";
        int keyIndex = 0;

        for (int i = 0; i < plainText.length(); i++) {
            char c = plainText.charAt(i);

            //encryption
            int index = Math.floorMod(ALPHABET.indexOf(c) + ALPHABET.indexOf(key.charAt(keyIndex)), ALPHABET.length());

            cipherText += ALPHABET.charAt(index);
            keyIndex++;

            if (keyIndex == key.length()) {
                keyIndex = 0;
            }
        }

        return cipherText;
    }

    public String decrypt(String cipherText, String key) {
        cipherText = cipherText.toUpperCase();
        key = key.toUpperCase();

        String plainText = "";
        int keyIndex = 0;

        for (int i = 0; i < cipherText.length(); i++) {
            char c = cipherText.charAt(i);

            int index = Math.floorMod(ALPHABET.indexOf(c)-ALPHABET.indexOf(key.charAt(keyIndex)), ALPHABET.length());

            plainText+=ALPHABET.charAt(index);
            keyIndex++;

            if (keyIndex == key.length()) {
                keyIndex = 0;
            }
        }
        return plainText;
    }

//
}
