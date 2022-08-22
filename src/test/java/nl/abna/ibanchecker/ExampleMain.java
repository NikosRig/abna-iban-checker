package nl.abna.ibanchecker;

import nl.abna.ibanchecker.domain.gateway.IbanCheckResponse;
import nl.abna.ibanchecker.domain.gateway.IbanCheckerClientInterface;
import org.json.JSONObject;

public class ExampleMain {
    public static void main( String[] args ) throws Exception {
        IbanCheckerClientInterface client = new IbanCheckerClientBuilder()
                .enableSandboxMode()
                .withApiKey("api-key")
                .withClientId("test_client")
                .withPkcs12("./var/keyStore.p12", "")
                .build();

        IbanCheckResponse ibanCheckResponse = client.checkIban("NL62ABNA9999841479","test");
        JSONObject responsePayload = new JSONObject();
        responsePayload.put("iban_valid", ibanCheckResponse.isIbanValid);
        responsePayload.put("name_matched", ibanCheckResponse.isNameMatched);
        responsePayload.put("foreign_account", ibanCheckResponse.isForeignAccount);
        responsePayload.put("account_active", ibanCheckResponse.isAccountActive);
        responsePayload.put("account_country_code", ibanCheckResponse.accountCountryCode);
        responsePayload.put("total_account_holders", ibanCheckResponse.totalAccountHolders);

        System.out.println(responsePayload);
    }
}
