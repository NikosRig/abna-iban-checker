package nl.abna.ibanchecker.infrastructure.gateway;

import nl.abna.ibanchecker.domain.gateway.IbanCheckResponse;
import nl.abna.ibanchecker.domain.gateway.exceptions.BadResponseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import static org.mockito.ArgumentMatchers.any;


public class AbnaIbanCheckerGatewayTest {
    private HttpClient client;
    private AbnaIbanCheckerClient gateway;

    @Before
    public void setUp() {
        AbnaCredentials credentials = new AbnaCredentials(true, "", "");
        this.client = Mockito.mock(HttpClient.class);
        this.gateway = new AbnaIbanCheckerClient(credentials, this.client);
    }

   @Test
    public void ibanShouldBeValidAndNameShouldBeMatched() throws Exception {
       String responseBody = "{\"check\":{\"iban\":{\"valid\":true,\"found\":true},\"name\":{\"validity\":\"VALID\"}},\"ibanNameMatching\":{\"type\":\"MATCHING\"},\"account\":{\"foreign\":false,\"countryCode\":\"NL\",\"status\":\"ACTIVE\"},\"accountHolder\":{\"type\":\"NP\",\"officialName\":\"\",\"numberOfAccountHolders\":1,\"jointAccount\":false}}";
       HttpResponse response = this.mockHttpResponse(responseBody);
       HttpResponse accessTokenCreatedResponse = this.mockAccessTokenCreatedResponse();
       Mockito.when(this.client.send(any(), any())).thenReturn(accessTokenCreatedResponse, response);
       IbanCheckResponse ibanCheckResponse = this.gateway.checkIban("NL12ABNA9999876523", "N.Rigas");

       Assert.assertTrue(ibanCheckResponse.isIbanValid);
       Assert.assertTrue(ibanCheckResponse.isAccountActive);
       Assert.assertTrue(ibanCheckResponse.isNameMatched);
       Assert.assertFalse(ibanCheckResponse.isForeignAccount);
       Assert.assertEquals("NL", ibanCheckResponse.accountCountryCode);
    }

    @Test
    public void ibanShouldBeValidNameShouldBeUnmatched() throws Exception {
        String responseBody = "{\"check\":{\"iban\":{\"valid\":true,\"found\":true},\"name\":{\"validity\":\"VALID\"}},\"ibanNameMatching\":{\"type\":\"NOT_MATCHING\"},\"account\":{\"foreign\":false,\"countryCode\":\"NL\",\"status\":\"ACTIVE\"},\"accountHolder\":{\"type\":\"NP\",\"officialName\":\"\",\"numberOfAccountHolders\":1,\"jointAccount\":false}}";
        HttpResponse response = this.mockHttpResponse(responseBody);
        HttpResponse accessTokenCreatedResponse = this.mockAccessTokenCreatedResponse();
        Mockito.when(this.client.send(any(), any())).thenReturn(accessTokenCreatedResponse, response);
        IbanCheckResponse ibanCheckResponse = this.gateway.checkIban("NL12ABNA9999876523", "N.Rigas");

        Assert.assertTrue(ibanCheckResponse.isIbanValid);
        Assert.assertTrue(ibanCheckResponse.isAccountActive);
        Assert.assertFalse(ibanCheckResponse.isNameMatched);
        Assert.assertFalse(ibanCheckResponse.isForeignAccount);
        Assert.assertEquals("NL", ibanCheckResponse.accountCountryCode);
    }

    @Test(expected = BadResponseException.class)
    public void expectExceptionWhenIbanIsInvalid() throws Exception {
        String responseBody = "{\"check\":{\"iban\":{\"valid\":false,\"found\":false},\"name\":{}}}";
        HttpResponse response = this.mockHttpResponse(responseBody);
        HttpResponse accessTokenCreatedResponse = this.mockAccessTokenCreatedResponse();
        Mockito.when(this.client.send(any(), any())).thenReturn(accessTokenCreatedResponse, response);

        IbanCheckResponse ibanCheckResponse = this.gateway.checkIban("NL12ABNA9999876523", "N.Rigas");
    }

    @Test
    public void accountShouldBeInactiveAndNameShouldBeMatched() throws Exception {
        String responseBody = "{\"check\":{\"iban\":{\"valid\":true,\"found\":true},\"name\":{\"validity\":\"VALID\"}},\"ibanNameMatching\":{\"type\":\"MATCHING\"},\"account\":{\"foreign\":false,\"countryCode\":\"NL\",\"status\":\"INACTIVE\"},\"accountHolder\":{\"type\":\"NP\",\"officialName\":\"\",\"numberOfAccountHolders\":1,\"jointAccount\":false}}";
        HttpResponse response = this.mockHttpResponse(responseBody);
        HttpResponse accessTokenCreatedResponse = this.mockAccessTokenCreatedResponse();
        Mockito.when(this.client.send(any(), any())).thenReturn(accessTokenCreatedResponse, response);
        IbanCheckResponse ibanCheckResponse = this.gateway.checkIban("NL12ABNA9999876523", "N.Rigas");

        Assert.assertTrue(ibanCheckResponse.isIbanValid);
        Assert.assertFalse(ibanCheckResponse.isAccountActive);
        Assert.assertTrue(ibanCheckResponse.isNameMatched);
        Assert.assertFalse(ibanCheckResponse.isForeignAccount);
        Assert.assertEquals("NL", ibanCheckResponse.accountCountryCode);
    }

    @Test(expected = BadResponseException.class)
    public void expectExceptionWhenNameIsInvalid() throws Exception {
        String responseBody = "{\"check\":{\"iban\":{\"valid\":true,\"found\":true},\"name\":{\"validity\":\"NAME_TOO_SHORT\"}}}";
        HttpResponse response = this.mockHttpResponse(responseBody);
        HttpResponse accessTokenCreatedResponse = this.mockAccessTokenCreatedResponse();
        Mockito.when(this.client.send(any(), any())).thenReturn(accessTokenCreatedResponse, response);

        IbanCheckResponse ibanCheckResponse = this.gateway.checkIban("NL12ABNA9999876523", "N.Rigas");
    }

    private HttpResponse mockAccessTokenCreatedResponse() throws IOException, InterruptedException {
        String responseBody = "{\"access_token\":\"0003HU61IUXiq4wdd2O6x8Eyz7Ip\",\"token_type\":\"Bearer\",\"expires_in\":7200}";
        return this.mockHttpResponse(responseBody);
    }

    private HttpResponse mockHttpResponse(String responseBody) {
        HttpResponse<String> httpResponse = Mockito.mock(HttpResponse.class);
        Mockito.when(httpResponse.statusCode()).thenReturn(200);
        Mockito.when(httpResponse.body()).thenReturn(responseBody);

        return httpResponse;
    }
}
