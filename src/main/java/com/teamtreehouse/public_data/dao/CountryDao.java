package com.teamtreehouse.public_data.dao;


import com.teamtreehouse.public_data.model.Country;

import java.util.List;

public interface CountryDao {
    void updateCountry(Country country);
    void saveCountry(Country country);
    void deleteCountry(Country country);
    List<Country> fetchAllCountries();
    void updateCountries(List<Country> updatedCountries);
    Country findCountryByCode(List<Country> countries, String code);
    Country minimumInternetUsers();
    Country maximumInternetUsers();
    Country minimumAdultLiteracy();
    Country maximumAdultLiteracy();
    Double averageInternetUsers();
    Double averageAdultLiteracy();
}
