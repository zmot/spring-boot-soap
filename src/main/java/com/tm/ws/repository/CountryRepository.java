package com.tm.ws.repository;

import com.tm.ws.model.country.Country;
import com.tm.ws.model.country.Currency;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class CountryRepository {

    private static final Map<String, Country> COUNTRIES = new HashMap<>();

    @PostConstruct
    public void init() {
        Country poland = new Country();
        poland.setCapital("Warsaw");
        poland.setCurrency(Currency.PLN);
        poland.setName("Poland");
        poland.setPopulation(40);

        Country germany = new Country();
        germany.setCapital("Berlin");

        germany.setCurrency(Currency.EUR);
        germany.setName("Germany");
        germany.setPopulation(50);

        COUNTRIES.put("PL", poland);
        COUNTRIES.put("GER", germany);
    }

    public Country find(String abbreviation) {
        return Optional.ofNullable(COUNTRIES.get(abbreviation)).orElseThrow(() -> new IllegalArgumentException("Unknown country"));
    }
}
