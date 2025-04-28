package caesar_cipher.language_detector;

import java.util.List;

public class LanguageDetector {
    private FileProcessor fileProcessor;
    List<String> englishWords;

    public LanguageDetector(){
        fileProcessor = new FileProcessor();
        englishWords = fileProcessor.getWords();
    }

    public int countEnglishWordsInText(String text){
        text = text.toUpperCase();

        String[] words = text.split(" ");

        int matches = 0;
        for(String s:words)
        {
            if (englishWords.contains(s)){
                matches++;
            }
        }
        return matches;
    }

    public boolean isEnglish(String text){
        int matches = countEnglishWordsInText(text);

        //assuption:60% of the words
        // in the thext are English words
        // than it is an Englist text
        if((float)matches/text.split(" ").length*100>=60)
            return true;

        return false;
    }
}
