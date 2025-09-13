package pl.estrix.common.util;

import com.google.common.io.Files;
import org.apache.commons.codec.Charsets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomStringUtils {

    private static Set<String> DICTIOANRY = new HashSet<>();


    public static String prepareStringToCompare(String input){
        String mod = input
                .replaceAll("<[^>]*>", "") // usuwanie znaczników html
                .replaceAll("\\r\\n|\\r|\\n", " ") // usuwanie znaku karetki i nowej linie
                .replaceAll("  ", " ") // usuwanie podwójnych spacji
                .trim();

        return mod;
    }

    public static List<String> markDifferencesInText(String oldText, String newText) {
        List<String> differences = new ArrayList<>();

        String[] words1Old = oldText.split(" ");
        String[] words2New = newText.split(" ");

        int maxLength = Math.max(words1Old.length, words2New.length);

        for (int i = 0; i < maxLength; i++) {
            String word1 = (i < words1Old.length) ? words1Old[i] : "";
            String word2 = (i < words2New.length) ? words2New[i] : "";

            if (!word1.equals(word2)) {
                differences.add("<b style='color: red;'>" + word2 + "</b>");
            } else {
                differences.add(word2);
            }
        }

        return differences;
    }

    public static void loadDictionary(){
        if (!DICTIOANRY.isEmpty()) return;

        try {
            File file = new File(
                    CustomStringUtils.class.getClassLoader().getResource("dictionary/de.dic").getFile()
            );
            DICTIOANRY =  new HashSet<>(Files.readLines(file, Charsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkWordsInDictionary(String word){
        return DICTIOANRY.contains(word.toLowerCase());
    }

    public static String checkWordsInDictionary(String words, boolean isMobileMedium) {
        loadDictionary();
        List<String> differences = new ArrayList<>();
        String[] wordsList = words.split(" ");

        for (int i = 0; i < wordsList.length; i++) {

            if (!DICTIOANRY.contains(wordsList[i])) {
//                if (isMobileMedium){
                    differences.add("<Text style={styles.dictionaryErrorText}>" + wordsList[i] + "</Text>");
//                } else {
//                    differences.add("<b style='color: yellow;'>" + wordsList[i] + "</b>");
//                }
            } else {
                differences.add(wordsList[i]);
            }
        }

        return String.join(" ", differences);
    }

    public static boolean isTextIsSame(String oldText, String newText){
        boolean isDifferent = false;
        boolean isDifferent2 = newText.equals(oldText);

        String[] words1Old = oldText.split(" ");
        String[] words2New = newText.split(" ");

        int maxLength = Math.max(words1Old.length, words2New.length);

        for (int i = 0; i < maxLength; i++) {
            String word1 = (i < words1Old.length) ? words1Old[i] : "";
            String word2 = (i < words2New.length) ? words2New[i] : "";

            if (!word1.equals(word2)) {
                isDifferent = true;
            }
        }
        return isDifferent;
    }

}
