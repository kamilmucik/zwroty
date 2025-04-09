package pl.estrix.common.util;

import java.util.ArrayList;
import java.util.List;

public class CustomStringUtils {


    public static String prepareStringToCompare(String input){
        String mod = input
                .replaceAll("<[^>]*>", "") // usuwanie znaczników html
                .replaceAll("  ", " ") // usuwanie podwójnych spacji
                .replaceAll("\r", "") // usuwanie znaku karetki
                .replaceAll("\n", "") // usuwanie znaku nowej linii
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
