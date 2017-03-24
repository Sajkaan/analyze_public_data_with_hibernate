package com.teamtreehouse.public_data.dao;

import com.teamtreehouse.public_data.model.Country;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CountryDaoImpl implements CountryDao {

    private List<Country> countries;

    public CountryDaoImpl(){
        countries = new ArrayList<>();
        countries = fetchAllCountries();
    }

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }


    @Override
    public void updateCountry(Country country) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.update(country);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void saveCountry(Country country) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.save(country);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public void deleteCountry(Country country) {
        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.delete(country);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public List<Country> fetchAllCountries() {
        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<Country> criteria = builder.createQuery(Country.class);

        criteria.from(Country.class);

        List<Country> countries = session.createQuery(criteria).getResultList();

        session.close();

        return countries;
    }

    @Override
    public void updateCountries(List<Country> updatedCountries) {
        countries = updatedCountries;
    }

    @Override
    public Country findCountryByCode(List<Country> countries, String code) {
        return countries.stream()
                .filter(country -> country.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Country minimumInternetUsers() {
        return countries.stream()
                .filter(country -> country.getInternetUsers() != null)
                .min(Comparator.comparing(Country::getInternetUsers))
                .get();
    }

    @Override
    public Country maximumInternetUsers() {
        return countries.stream()
                .filter(country -> country.getInternetUsers() != null)
                .max(Comparator.comparing(Country::getInternetUsers))
                .get();
    }

    @Override
    public Country minimumAdultLiteracy() {
        return countries.stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .min(Comparator.comparing(Country::getAdultLiteracyRate))
                .get();
    }

    @Override
    public Country maximumAdultLiteracy() {
        return countries.stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .max(Comparator.comparing(Country::getAdultLiteracyRate))
                .get();
    }

    @Override
    public Double averageInternetUsers() {
        return countries.stream()
                .filter(country -> country.getInternetUsers() != null)
                .mapToDouble(Country::getInternetUsers)
                .average()
                .getAsDouble();
    }

    @Override
    public Double averageAdultLiteracy() {
        return countries.stream()
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getAdultLiteracyRate)
                .average()
                .getAsDouble();
    }

    @Override
    public Double getCorrelationCoefficient() {
        Double correlation;

        double[] internetUsers = countries
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getInternetUsers)
                .toArray();

        double[] adultLiteracy = countries
                .stream()
                .filter(country -> country.getInternetUsers() != null)
                .filter(country -> country.getAdultLiteracyRate() != null)
                .mapToDouble(Country::getAdultLiteracyRate)
                .toArray();

        try {
            correlation = new PearsonsCorrelation()
                    .correlation(internetUsers, adultLiteracy);
        } catch (MathIllegalArgumentException mae) {
            System.out.println("Insufficient data");
            return null;
        }


        return correlation;
    }
}
