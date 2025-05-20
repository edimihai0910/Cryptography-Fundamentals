package symmetric.caesar_cipher.language_detector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileProcessor {

    private List<String> words;


    public FileProcessor(){
        this.words= new ArrayList<>();
        getWordsFromFile();
    }

    private void getWordsFromFile() {

        try {
            BufferedReader bufferReader = new BufferedReader(new FileReader(new File("src/english_words.txt")));

            String line;
            while ((line = bufferReader.readLine()) != null) {
                words.add(line.toUpperCase());
            }

            bufferReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getWords()
    {
        return this.words;
    }
}
