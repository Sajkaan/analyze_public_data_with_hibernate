package com.teamtreehouse.publicdata.controller;

import com.teamtreehouse.publicdata.dao.CountryDao;
import com.teamtreehouse.publicdata.model.Country;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Prompter {
    private CountryDao countryDao;
    private List<Country> countries;
    private BufferedReader reader;
    private Map<Integer, String> menu;

    public Prompter(CountryDao countryDao) {
        this.countryDao = countryDao;
        countries = new ArrayList<>(countryDao.fetchAllCountries());
        menu = new HashMap<>();
        menu.put(1, "View country data");
        menu.put(2, "Edit country");
        menu.put(3, "Add country");
        menu.put(4, "Delete country");
        menu.put(5, "Indicator statistics");
        menu.put(6, "Exit");
    }




}
