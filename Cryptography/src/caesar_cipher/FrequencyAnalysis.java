package caesar_cipher;

import java.util.HashMap;
import java.util.Map;

public class FrequencyAnalysis {

    private String ALPHABET = "ABCDEFGHIJLMNOPQRSTUVWXYZ";

    private Map<Character, Integer> run(String text) {
        text = text.toUpperCase();
        Map<Character, Integer> frequencies = new HashMap<>();

        for (int i = 0; i < text.length(); i++) {
            Character character = text.charAt(i);
            if (ALPHABET.indexOf(character) >= 0) {
                frequencies.put(character, frequencies.getOrDefault(character, 0) + 1);
            }
        }

        return frequencies;
    }

    public void showFrequencies(String text) {
        Map<Character, Integer> frequencies = run(text);

        for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
            System.out.format("%s - %s\n", entry.getKey(), entry.getValue());
        }
    }

    public void crack(String cypherText)
    {
        Map<Character, Integer> frequencies = run(cypherText);

        Map.Entry<Character,Integer> maxEntry = null;

        for (Map.Entry<Character,Integer> entry: frequencies.entrySet()) {
            if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue())>0)
            {
                maxEntry= entry;
            }
        }
        char mostFrequentChar = maxEntry.getKey();

        int approximatedKey = ALPHABET.indexOf(mostFrequentChar)-ALPHABET.indexOf(' ');

        System.out.println("the key is:" +approximatedKey);
    }
}
