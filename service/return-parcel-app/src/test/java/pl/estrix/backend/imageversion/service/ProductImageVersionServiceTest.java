package pl.estrix.backend.imageversion.service;

import org.junit.Before;
import org.junit.Test;
import pl.estrix.common.util.CustomStringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ProductImageVersionServiceTest {

    @Test
    public void markDifferencesInText() {
        String text1 = "Dziś jest piękna pogoda";
        String text2 = "Dziś jest brzydka pogoda";

        List<String> differences = CustomStringUtils.markDifferencesInText(text1, text2);
        System.out.println("Różnice między tekstami:");
        for (String diff : differences) {
            System.out.println(diff);
        }
    }

    @Test
    public void markDifferencesInHtml() {

        String result = CustomStringUtils.checkWordsInDictionary("dupianska raus", false);

        assertEquals("<b style='color: yellow;'>dupianska</b> raus", result);
    }
}
