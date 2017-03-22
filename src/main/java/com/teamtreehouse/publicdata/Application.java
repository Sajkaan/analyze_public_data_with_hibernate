package com.teamtreehouse.publicdata;

import com.teamtreehouse.publicdata.controller.Prompter;
import com.teamtreehouse.publicdata.dao.CountryDao;
import com.teamtreehouse.publicdata.dao.CountryDaoImpl;

public class Application {
    public static void main(String[] args) {
        CountryDao countryDao = new CountryDaoImpl();
        Prompter prompter = new Prompter(countryDao);
        prompter.startPrompter();
    }
}
