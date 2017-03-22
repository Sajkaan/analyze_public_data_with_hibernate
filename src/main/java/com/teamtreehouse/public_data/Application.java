package com.teamtreehouse.public_data;

import com.teamtreehouse.public_data.controller.Prompter;
import com.teamtreehouse.public_data.dao.CountryDao;
import com.teamtreehouse.public_data.dao.CountryDaoImpl;

public class Application {
    public static void main(String[] args) {
        CountryDao countryDao = new CountryDaoImpl();
        Prompter prompter = new Prompter(countryDao);
        prompter.startPrompter();
    }
}
