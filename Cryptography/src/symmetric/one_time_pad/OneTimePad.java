package symmetric.one_time_pad;

public class OneTimePad {

    public static String ALPHABET = " ABCDEFGHIJLMNOPQRSTUVWXYZ";

    public String encrypt (String plainText, int[] key) {
        plainText = plainText.toUpperCase();

        String cipherText = "";

        for(int i =0; i<plainText.length();i++){
            int keyIndex= key[i];
            int characterIndex = ALPHABET.indexOf(plainText.charAt(i));

            //encrypted letter = (characterIndex+randomShiftIndex) mod 27

            cipherText += ALPHABET.charAt(Math.floorMod((characterIndex+keyIndex),ALPHABET.length()));

        }

        return cipherText;
    }

    public String decrypt (String cipherText, int[] key){
        String plainText="";
        cipherText = cipherText.toUpperCase();

        for(int i =0; i<cipherText.length();++i){
            int keyIndex= key[i];
            int characterIndex = ALPHABET.indexOf(cipherText.charAt(i));

            //decrypt letter = (characterIndex+randomShiftIndex) mod 27

            plainText += ALPHABET.charAt(Math.floorMod((characterIndex-keyIndex),ALPHABET.length()));

        }

        return plainText;
    }

//    public String crack(String cipherText)
//    {
////it s almost impossible to break it - but can be sometimes .. for exeample for englsih words can be use a freq
//    }
}
