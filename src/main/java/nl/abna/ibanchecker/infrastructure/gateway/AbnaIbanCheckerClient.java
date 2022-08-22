package nl.abna.ibanchecker.infrastructure.gateway;

import nl.abna.ibanchecker.domain.gateway.IbanCheckResponse;
import nl.abna.ibanchecker.domain.gateway.IbanCheckerClientInterface;
import nl.abna.ibanchecker.domain.gateway.exceptions.BadResponseException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class AbnaIbanCheckerClient implements IbanCheckerClientInterface {
    private final AbnaCredentials credentials;
    private final HttpClient client;
    private String authUri;
    private String ibanCheckUrl;

    public AbnaIbanCheckerClient(AbnaCredentials credentials, HttpClient client) {
        this.credentials = credentials;
        this.client = client;
        this.setupGatewayUrls(credentials);
    }

    public void setupGatewayUrls(AbnaCredentials credentials) {
        String sandboxAuthUrl = "https://auth-mtls-sandbox.abnamro.com/as/token.oauth2";
        String productionAuthUrl = "https://auth.connect.abnamro.com:8443/as/token.oauth2";
        this.authUri = credentials.isSandboxMode ? sandboxAuthUrl : productionAuthUrl;

        String productionUrl = "https://api.abnamro.com/third-party-api/surepay/iban-name-check/v2";
        String sandboxUrl = "https://api-sandbox.abnamro.com/third-party-api/surepay/iban-name-check/v2";
        this.ibanCheckUrl = credentials.isSandboxMode ? sandboxUrl : productionUrl;
    }

    public AccessToken createAccessToken() throws BadResponseException {
        String body = String.format(
                "grant_type=client_credentials&client_id=%s&scope=surepay:ibannamecheck:read",
                this.credentials.clientId
        );

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.authUri))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Access token creation failed.");
            }
            JSONObject jsonResponsePayload = new JSONObject(response.body());

            return new AccessToken(
                    jsonResponsePayload.getString("access_token"),
                    jsonResponsePayload.getInt("expires_in")
            );
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
    }

    @Override
    public IbanCheckResponse checkIban(String iban, String name) throws BadResponseException {
        try {
            AccessToken accessToken = this.createAccessToken();
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("iban", iban);
            jsonBody.put("name", name);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(this.ibanCheckUrl))
                    .header("Content-Type", "application/json")
                    .header("X-Request-ID", String.valueOf(UUID.randomUUID()))
                    .header("API-Key", this.credentials.apiKey)
                    .header("Authorization", "Bearer " + accessToken.token)
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody.toString()))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Iban check failed with code " + response.statusCode());
            }
            JSONObject jsonResponsePayload = new JSONObject(response.body());
            JSONObject check = jsonResponsePayload.getJSONObject("check");
            JSONObject account = jsonResponsePayload.getJSONObject("account");
            JSONObject ibanNameMatching = jsonResponsePayload.getJSONObject("ibanNameMatching");
            JSONObject accountHolder = jsonResponsePayload.getJSONObject("accountHolder");

             return new IbanCheckResponse(
                     check.getJSONObject("iban").getBoolean("valid"),
                     ibanNameMatching.getString("type").equals("MATCHING"),
                     account.getBoolean("foreign"),
                     account.getString("status").equals("ACTIVE"),
                     account.getString("countryCode"),
                     accountHolder.getInt("numberOfAccountHolders")

             );
        } catch (Exception e) {
            throw new BadResponseException(e.getMessage());
        }
    }
}
