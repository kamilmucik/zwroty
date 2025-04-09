package pl.estrix.backend.imageversion.service;

import org.junit.Test;
import pl.estrix.common.util.CustomStringUtils;

import java.util.List;

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
}
