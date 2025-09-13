package pl.estrix.backend.dictionary;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pl.estrix.common.util.CustomStringUtils;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class DictionaryIntegrationTest {

    DictionaryQueries dictionaryQueries = new DictionaryQueries();

    private static final int PAGE_SIZE = 2;

    List<String> dbWordSet = new LinkedList<>();
    int recordsInDb = 0;
    int pages = 0;

    @Before
    public void setup(){
        recordsInDb = dictionaryQueries.countRecord();
        pages = recordsInDb / PAGE_SIZE;
        CustomStringUtils.loadDictionary();
    }

    @After
    public void tearDown(){
        dbWordSet.clear();
    }

    @Test
    public void listFreelancerDetails_positive() {
        for (int currentPage = 0; currentPage < pages; currentPage++) {
            int page = currentPage * PAGE_SIZE;
            dictionaryQueries.getDictionaryList(PAGE_SIZE, page).stream()
                    .forEach( record -> {
                        String currNoHTML = CustomStringUtils.prepareStringToCompare(record.getDescription());
                        System.out.println("currNoHTML:  " + currNoHTML);
                        String[] parts = currNoHTML
                                .replaceAll("//", " ")
                                .replaceAll("\n", " ")
                                .split(" ");
                        Arrays.asList(parts).forEach(dbWordSet::add);
                    });
        }
        long correctWords = dbWordSet.stream()
                .filter(dbWord -> CustomStringUtils.checkWordsInDictionary(dbWord))
                .count();
        long incorrectWords = dbWordSet.stream()
                .filter(dbWord -> !CustomStringUtils.checkWordsInDictionary(dbWord))
                .count()
        ;


        assertEquals(incorrectWords, 0);
    }

}
