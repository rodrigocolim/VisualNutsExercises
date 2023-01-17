package com.visual.nuts.exercises.ex2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class LanguageDataBase {

    List<CountryToLanguages> countryToLanguagesList;

    public LanguageDataBase(List<CountryToLanguages> countryToLanguagesList) {
        this.countryToLanguagesList = countryToLanguagesList;
    }

    public static LanguageDataBase loadFromJson(String json) throws Exception {
        List<CountryToLanguages> countryToLanguagesList = convertFromJson(json);

        launchErrorIfThereAreDuplicatedCountries(countryToLanguagesList);

        countryToLanguagesList
                .removeIf(countryToLanguages -> countryToLanguages.getCountry() == null || countryToLanguages.getCountry().isEmpty());

        return new LanguageDataBase(countryToLanguagesList);
    }

    private static List<CountryToLanguages> convertFromJson(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        List<CountryToLanguages> countryToLanguagesList = mapper.readValue(json, new TypeReference<List<CountryToLanguages>>(){});
        return countryToLanguagesList;
    }

    private static void launchErrorIfThereAreDuplicatedCountries(List<CountryToLanguages> countryToLanguagesList) throws Exception {
        if (countryToLanguagesList.size() != countryToLanguagesList.stream().map(CountryToLanguages::getCountry).distinct().count()) {
            throw new Exception("There are duplicated countries");
        }
    }

    public Integer getCountriesTotal(){
        return countryToLanguagesList.size();
    }

    public List<String> getGermanSpeakingCountriesWithMostOfficialLanguages(){
        List<CountryToLanguages> germanSpeakingCountries = countryToLanguagesList.stream()
                .filter(countryToLanguages -> countryToLanguages.getLanguages().contains("de"))
                .collect(Collectors.toList());

        return getCountriesWithMostOfficialLanguages(germanSpeakingCountries);
    }

    private List<String> getCountriesWithMostOfficialLanguages(List<CountryToLanguages> countryToLanguagesList) {
        Integer mostOfficialLanguages = countryToLanguagesList.stream()
                .map(CountryToLanguages::getLanguagesTotal)
                .max(Integer::compareTo)
                .orElse(0);

        List<String> countries = countryToLanguagesList.stream()
                .filter(countryToLanguages -> countryToLanguages.getLanguagesTotal().equals(mostOfficialLanguages))
                .map(CountryToLanguages::getCountry)
                .collect(Collectors.toList());

        return countries;
    }

    public Long countOfficialLanguagesOf(List<String> countries){
        return countryToLanguagesList.stream()
                .filter(countryToLanguages -> countries.contains(countryToLanguages.getCountry()))
                .map(CountryToLanguages::getLanguages)
                .flatMap(Collection::stream)
                .distinct()
                .count();
    }

    public List<String> getCountriesWithMostOfficialLanguages(){
        return getCountriesWithMostOfficialLanguages(countryToLanguagesList);
    }

    public List<String> getMostCommonOfficialLanguages(){
        Map<String, Long> languageToCountMap = countryToLanguagesList.stream()
                .map(CountryToLanguages::getLanguages)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Long mostCommonLanguageCount = Collections.max(languageToCountMap.entrySet(), Map.Entry.comparingByValue()).getValue();

        List<String> mostCommonLanguages = languageToCountMap.entrySet()
                .stream()
                .filter(languageToCount -> languageToCount.getValue().equals(mostCommonLanguageCount))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        return mostCommonLanguages;
    }
}
