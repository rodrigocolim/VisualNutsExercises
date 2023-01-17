package com.visual.nuts.exercises.ex2;

import java.util.LinkedHashSet;
import java.util.List;

public class CountryToLanguages {
    private String country;
    private LinkedHashSet<String> languages;

    public String getCountry(){
        return country;
    }

    public LinkedHashSet<String> getLanguages() {
        return languages;
    }

    public Integer getLanguagesTotal() {
        return languages.size();
    }
}
