package com.tm.ws.repository;

import com.tm.ws.model.country.Country;
import com.tm.ws.repository.CountryRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CountryRepositoryTest {
    private final CountryRepository repository = new CountryRepository();

    @Before
    public void setUp() {
        repository.init();
    }

    @Test
    public void shouldReturnKnownCountry() {
        Country country = repository.find("PL");
        assertNotNull(country);
        assertEquals("Poland", country.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForUnknownCountry() {
        repository.find("UK");
    }
}