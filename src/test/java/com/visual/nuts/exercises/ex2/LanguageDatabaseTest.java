package com.visual.nuts.exercises.ex2;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class LanguageDatabaseTest {

    @Test
    public void shouldReturnTheNumberOfCountries_EqualsTo5() throws Exception {
        String databaseAsJson = "[{\"country\":\"US\",\"languages\":[\"en\"]}," +
                "{\"country\":\"BE\",\"languages\":[\"nl\",\"fr\",\"de\"]}," +
                "{\"country\":\"NL\",\"languages\":[\"nl\",\"fy\"]}," +
                "{\"country\":\"DE\",\"languages\":[]}," +
                "{\"country\":\"ES\",\"languages\":[\"es\"]}]";

        Integer countriesCount = LanguageDataBase.loadFromJson(databaseAsJson)
                .getCountriesTotal();

        assertEquals(5, countriesCount.intValue());
    }

    @Test
    public void shouldReturnTheNumberOfCountries_EqualsTo0() throws Exception {
        String databaseAsJson = "[]";

        Integer countriesCount = LanguageDataBase.loadFromJson(databaseAsJson)
                .getCountriesTotal();

        assertEquals(0, countriesCount.intValue());
    }

    @Test
    public void shouldIgnoreWhenTheCountryIsMissing() throws Exception {
        String databaseAsJson = "[{\"languages\": [\"en\"]}," +
                "{\"country\":\"\", \"languages\": [\"en\"]}," +
                "{\"country\":\"ES\",\"languages\": [\"es\"]}]";

        Integer countriesCount = LanguageDataBase.loadFromJson(databaseAsJson)
                .getCountriesTotal();

        assertEquals(1, countriesCount.intValue());
    }

    @Test
    public void shouldIgnoreDuplicatedLanguages() throws Exception {
        String databaseAsJson = "[{\"country\":\"US\",\"languages\":[\"en\",\"nl\",\"fr\",\"es\"]}," +
                "{\"country\":\"DE\",\"languages\":[\"en\",\"en\",\"fr\",\"es\",\"es\"]}," +
                "{\"country\":\"BE\",\"languages\":[\"nl\",\"de\",\"fr\",\"de\"]}," +
                "{\"country\":\"NL\",\"languages\":[\"nl\",\"nl\",\"nl\",\"de\"]}," +
                "{\"country\":\"FR\",\"languages\":[\"fr\"]}]";

        LanguageDataBase languageDataBase = LanguageDataBase.loadFromJson(databaseAsJson);

        assertEquals(5, languageDataBase.countOfficialLanguagesOf(Arrays.asList("US", "BE", "BR")).intValue());
        assertEquals(4, languageDataBase.countOfficialLanguagesOf(Arrays.asList("US")).intValue());
        assertEquals(3, languageDataBase.countOfficialLanguagesOf(Arrays.asList("DE")).intValue());
        assertEquals(3, languageDataBase.countOfficialLanguagesOf(Arrays.asList("BE")).intValue());
        assertEquals(2, languageDataBase.countOfficialLanguagesOf(Arrays.asList("NL")).intValue());
        assertEquals(1, languageDataBase.countOfficialLanguagesOf(Arrays.asList("FR")).intValue());
    }

    @Test
    public void shouldGetCountriesWithMostOfficialLanguages() throws Exception {
        String databaseAsJson = "[{\"country\":\"UK\",\"languages\":[\"en\",\"nl\",\"fr\",\"es\"]}," +
                "{\"country\":\"BR\",\"languages\":[\"en\",\"pt\",\"es\"]}," +
                "{\"country\":\"NL\",\"languages\":[\"nl\",\"de\"]}," +
                "{\"country\":\"DE\",\"languages\":[\"en\",\"fr\",\"de\"]}]";

        List<String> countriesWithMostOfficialLanguages = LanguageDataBase.loadFromJson(databaseAsJson)
                .getCountriesWithMostOfficialLanguages();

        assertEquals(1, countriesWithMostOfficialLanguages.size());
        assertEquals("UK", countriesWithMostOfficialLanguages.get(0));

        List<String> germanSpeakingCountryWithMostOfficialLanguages = LanguageDataBase.loadFromJson(databaseAsJson)
                .getGermanSpeakingCountriesWithMostOfficialLanguages();

        assertEquals(1, germanSpeakingCountryWithMostOfficialLanguages.size());
        assertEquals("DE", germanSpeakingCountryWithMostOfficialLanguages.get(0));
    }

    @Test
    public void shouldGetTwoCountriesWithMostOfficialLanguages() throws Exception {
        String databaseAsJson = "[{\"country\":\"UK\",\"languages\":[\"en\",\"nl\",\"fr\",\"es\"]}," +
                "{\"country\":\"BR\",\"languages\":[\"en\",\"pt\",\"es\",\"nl\"]}," +
                "{\"country\":\"FR\",\"languages\":[\"en\",\"fr\",\"es\"]}," +
                "{\"country\":\"NL\",\"languages\":[\"nl\",\"de\"]}," +
                "{\"country\":\"DE\",\"languages\":[\"en\",\"fr\",\"de\"]}," +
                "{\"country\":\"CA\",\"languages\":[\"en\",\"fr\",\"de\"]}]";

        List<String> countriesWithMostOfficialLanguages = LanguageDataBase.loadFromJson(databaseAsJson)
                .getCountriesWithMostOfficialLanguages();

        assertEquals(2, countriesWithMostOfficialLanguages.size());
        assertTrue(countriesWithMostOfficialLanguages.contains("UK"));
        assertTrue(countriesWithMostOfficialLanguages.contains("BR"));

        List<String> germanSpeakingCountryWithMostOfficialLanguages = LanguageDataBase.loadFromJson(databaseAsJson)
                .getGermanSpeakingCountriesWithMostOfficialLanguages();

        assertEquals(2, germanSpeakingCountryWithMostOfficialLanguages.size());
        assertTrue(germanSpeakingCountryWithMostOfficialLanguages.contains("DE"));
        assertTrue(germanSpeakingCountryWithMostOfficialLanguages.contains("CA"));
    }

    @Test
    public void shouldGetMostCommonOfficialLanguage() throws Exception {
        String databaseAsJson = "[{\"country\":\"UK\",\"languages\":[\"en\",\"nl\",\"fr\",\"es\"]}," +
                "{\"country\":\"NL\",\"languages\":[\"nl\",\"de\"]}," +
                "{\"country\":\"DE\",\"languages\":[\"en\",\"fr\",\"nl\"]}]";

        List<String> mostCommonOfficialLanguages = LanguageDataBase.loadFromJson(databaseAsJson)
                .getMostCommonOfficialLanguages();

        assertEquals(1, mostCommonOfficialLanguages.size());
        assertEquals("nl", mostCommonOfficialLanguages.get(0));
    }

    @Test
    public void shouldGetTwoMostCommonOfficialLanguageS() throws Exception {
        String databaseAsJson = "[{\"country\":\"UK\",\"languages\":[\"en\",\"nl\",\"fr\",\"es\"]}," +
                "{\"country\":\"NL\",\"languages\":[\"nl\",\"de\",\"en\"]}," +
                "{\"country\":\"DE\",\"languages\":[\"en\",\"fr\",\"nl\"]}]";

        List<String> mostCommonOfficialLanguages = LanguageDataBase.loadFromJson(databaseAsJson)
                .getMostCommonOfficialLanguages();

        assertEquals(2, mostCommonOfficialLanguages.size());
        assertTrue(mostCommonOfficialLanguages.contains("nl"));
        assertTrue(mostCommonOfficialLanguages.contains("en"));
    }

    @Test
    public void shouldNotLoadDatabaseWithDuplicatedCountries() throws Exception {
        String databaseAsJson = "[{\"country\":\"NL\",\"languages\":[\"en\",\"nl\",\"fr\",\"es\"]}," +
                "{\"country\":\"NL\",\"languages\":[\"nl\",\"de\"]}," +
                "{\"country\":\"NL\",\"languages\":[]}]";

        assertThrows(Exception.class, () -> LanguageDataBase.loadFromJson(databaseAsJson));
    }

}
