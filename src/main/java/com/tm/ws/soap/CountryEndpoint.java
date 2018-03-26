package com.tm.ws.soap;

import com.tm.ws.repository.CountryRepository;
import com.tm.ws.model.country.GetCountryRequest;
import com.tm.ws.model.country.GetCountryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {
    private final CountryRepository repository;

    @Autowired
    public CountryEndpoint(CountryRepository repository) {
        this.repository = repository;
    }

    @PayloadRoot(namespace = "http://tm.com/ws/model/country", localPart = "getCountryRequest")
    @ResponsePayload
    public GetCountryResponse getCountry(@RequestPayload GetCountryRequest request) {
        GetCountryResponse response = new GetCountryResponse();
        response.setCountry(repository.find(request.getAbbreviation()));
        return response;
    }
}
