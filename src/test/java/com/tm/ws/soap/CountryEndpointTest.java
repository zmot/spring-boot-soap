package com.tm.ws.soap;

import com.tm.ws.model.country.Country;
import com.tm.ws.model.country.GetCountryRequest;
import com.tm.ws.model.country.GetCountryResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CountryEndpointTest {

    @LocalServerPort
    private int port;

    private WebServiceTemplate ws;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan(GetCountryRequest.class.getPackage().getName());
        marshaller.afterPropertiesSet();
        ws = new WebServiceTemplate(marshaller);
    }

    @Test
    public void shouldGetValidResponseForKnownCountry() {
        GetCountryRequest request = new GetCountryRequest();
        request.setAbbreviation("PL");

        Object result = ws.marshalSendAndReceive(localhostEndpoint(), request);

        assertThat(result, is(instanceOf(GetCountryResponse.class)));
        assertCountryDetails(((GetCountryResponse) result).getCountry(), "Poland", "Warsaw", "PLN");
    }

    @Test
    public void shouldGetExceptionInResponseForUnknownCountry() {
        GetCountryRequest request = new GetCountryRequest();
        request.setAbbreviation("UNKNOWN");

        thrown.expect(SoapFaultClientException.class);
        thrown.expectMessage("Unknown country");

        ws.marshalSendAndReceive(localhostEndpoint(), request);
    }

    private void assertCountryDetails(Country actual, String expectedName, String expectedCapital, String expectedCurrency) {
        assertThat(actual.getCapital(), is(expectedCapital));
        assertThat(actual.getName(), is(expectedName));
        assertThat(actual.getCurrency().value(), is("PLN"));
    }

    private String localhostEndpoint() {
        return "http://localhost:" + port + "/ws/soap";
    }
}