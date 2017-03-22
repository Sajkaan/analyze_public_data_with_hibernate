package com.teamtreehouse.public_data.controller;

import com.teamtreehouse.public_data.dao.CountryDao;
import com.teamtreehouse.public_data.model.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Prompter {
    private CountryDao countryDao;
    private List<Country> countries;
    private BufferedReader reader;
    private Map<Integer, String> menu;

    public Prompter(CountryDao countryDao) {
        this.countryDao = countryDao;
        countries = new ArrayList<>(countryDao.fetchAllCountries());
        reader = new BufferedReader(new InputStreamReader(System.in));
        menu = new HashMap<>();
        menu.put(1, "View country data");
        menu.put(2, "Edit country");
        menu.put(3, "Add country");
        menu.put(4, "Delete country");
        menu.put(5, "Indicator statistics");
        menu.put(6, "Exit");
    }

    public void startPrompter() {
        System.out.println("ANALYZE PUBLIC DATA WITH HIBERNATE");
        menuController();
    }

    private int promptForMenu() throws IOException {
        int choice = 0;
        System.out.println("--------------------------------------------");

        for (Map.Entry<Integer, String> option : menu.entrySet()) {
            System.out.printf("%n%d - %s%n",
                    option.getKey(),
                    option.getValue());
        }
        System.out.println("      ENTER A NUMBER FOR YOUR CHOICE        ");

        try {
            choice = Integer.parseInt(reader.readLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 6.");
        }

        return choice;
    }

    public void menuController() {
        Integer selectedOption = 0;
        do {
            try {
                selectedOption = promptForMenu();
                switch (selectedOption) {
                    case 1:
                        viewCountryData();
                        break;
                    case 2:
                        editCountryData();
                        break;
                    case 3:
                        addCountry();
                        break;
                    case 4:
                        deleteCountry();
                        break;
/*                    case 5:
                        viewStatistics();
                        break;*/
                    case 6:
                        System.out.println("Thank you. Please come again.");
                        System.exit(0);
                    default:
                        System.out.println("Wrong input.Please try again");
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } while (selectedOption != 6);
    }


    private void viewCountryData() {
        String leftAlignFormat = "| %-4s | %-30s | %-14s | %-8s |%n";

        System.out.format("+------+--------------------------------+----------------+----------+%n");
        System.out.format("| Code | Country                        | Internet Users | Literacy |%n");
        System.out.format("+------+--------------------------------+----------------+----------+%n");
        for (Country c : countries) {
            System.out.format(
                    leftAlignFormat,
                    c.getCode(),
                    c.getName(),
                    c.getInternetUsers() == null ? "--" : String.format("%.2f", c.getInternetUsers()),
                    c.getAdultLiteracyRate() == null ? "--" : String.format("%.2f", c.getAdultLiteracyRate())
            );
        }
        System.out.format("+------+--------------------------------+----------------+----------+%n");
    }

    private void editCountryData() throws IOException {
        Country country = promptForCountry(countries);
        if (country != null) {
            System.out.println("Type your new values.");
            country.setCode(promptForCode());
            country.setName(promptForCountryName());
            country.setInternetUsers(promptForInternetUsers());
            country.setAdultLiteracyRate(promptForAdultLiteracy());
            countryDao.updateCountry(country);
            countryDao.updateCountries(countries);
            System.out.println("Country successfully edited.");
        } else {
            System.out.println("No country found");
        }
    }

    private Country promptForCountry(List<Country> countries) throws IOException {
        String searchedCode = promptForCode();
        return countryDao.findCountryByCode(countries,searchedCode);
    }

    private String promptForCode() throws IOException {
        String code = "";
        System.out.println("Enter 3 letter code.");
        do {
            try {
                code = reader.readLine();
            } catch (IOException ioe) {
                System.out.println("Invalid input.");
            }
        } while (code.length() != 3 || code.equals(""));

        return code.toUpperCase();
    }

    private String promptForCountryName() throws IOException{
        String name = "";

        do {
            try {
                System.out.println("Enter the country name:");
                name = reader.readLine();
            } catch (IOException ioe) {
                System.out.println("Invalid input.");
            }
        } while (name.length() > 32 || name.equals(""));

        return name;
    }

    private Double promptForInternetUsers() throws IOException {
        Double internetUsers = -1.0;

        do {
            try {
                System.out.println("Type the percentage of internet users: ");
                internetUsers = Double.valueOf(reader.readLine());
            } catch (IOException ioe) {
                System.out.println("Invalid input. Try again.");
            } catch (NumberFormatException nfe) {
                System.out.println("Type a number.");
            }
        } while (internetUsers > 100 || internetUsers < 0);

        return internetUsers;
    }

    private Double promptForAdultLiteracy() throws IOException{
        Double adultLiteracy = -1.0;

        do {
            try {
                System.out.println("Type the percentage of adult literacy : ");
                adultLiteracy = Double.valueOf(reader.readLine());
            } catch (IOException ioe) {
                System.out.println("Invalid input. Try again.");
            } catch (NumberFormatException nfe) {
                System.out.println("Type a number.");
            }
        } while (adultLiteracy > 100 || adultLiteracy < 0);

        return adultLiteracy;
    }

    private void addCountry() throws IOException {
        Country country = new Country.CountryBuilder(promptForCode())
                .withName(promptForCountryName())
                .withInternetUsers(promptForInternetUsers())
                .withAdultLiteracy(promptForAdultLiteracy())
                .build();
        countryDao.saveCountry(country);
        countries.add(country);
        countryDao.updateCountries(countries);
        System.out.println("Country successfully added.");
    }

    private void deleteCountry() throws IOException {
        Country country = promptForCountry(countries);
        if (country != null) {
            countryDao.deleteCountry(country);
            countries.remove(country);
            countryDao.updateCountries(countries);

            System.out.println("Country deleted");
        } else {
            System.out.println("No country found to delete.");
        }
    }


}