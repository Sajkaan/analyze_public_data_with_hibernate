package com.teamtreehouse.publicdata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Country {
    @Id
    @Column(name = "CODE", columnDefinition = "VARCHAR(3)")
    private String code;

    @Column(name = "NAME", columnDefinition = "VARCHAR(32)")
    private String name;

    @Column(name = "INTERNETUSERS", columnDefinition = "DECIMAL(11,8)")
    private Double internetUsers;

    @Column(name = "ADULTLITERACYRATE", columnDefinition = "DECIMAL(11, 8)")
    private Double adultLiteracyRate;

    public Country(){}

    public Country(CountryBuilder builder) {
        this.code = builder.code;
        this.name = builder.name;
        this.internetUsers = builder.internetUsers;
        this.adultLiteracyRate = builder.adultLiteracyRate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getInternetUsers() {
        return internetUsers;
    }

    public void setInternetUsers(Double internetUsers) {
        this.internetUsers = internetUsers;
    }

    public Double getAdultLiteracyRate() {
        return adultLiteracyRate;
    }

    public void setAdultLiteracyRate(Double adultLiteracyRate) {
        this.adultLiteracyRate = adultLiteracyRate;
    }

    public static class CountryBuilder{
        private String code;
        private String name;
        private Double internetUsers;
        private Double adultLiteracyRate;

        public CountryBuilder(String code) {
            this.code = code;
        }

        public CountryBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CountryBuilder withInternetUsers(Double internetUsers) {
            this.internetUsers = internetUsers;
            return this;
        }

        public CountryBuilder withAdultLiteracyRate(Double adultLiteracyRate) {
            this.adultLiteracyRate = adultLiteracyRate;
            return this;
        }

        public Country build() {
            return new Country(this);
        }
    }
}
