## ABNA Iban Checker

<p float="left">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
</p>

------------
[An unofficial library that consumes Iban-check API by SurePay via ABNA and enables you to verify customer or supplier data.](https://developer.abnamro.com/api-products/iban-name-check)

#### What checks can you perform using this library? 

  * Check if an iban is active/expired
  * Get information about the account
  * Confirmation of the name of the payee

&nbsp;
### Prerequisites
------------
  * Java 11+
  * Maven
  * ABNA Credentials
  * PKCS12 with ABNA Certificates
  
    

### Usage example
------------
> **_Note:_**  The library already contains a pkcs12 file with public ABNA's sandbox certificates. It's password is an empty string.
&nbsp;

ExampleMain.java


```
package nl.abna.ibanchecker;

import nl.abna.ibanchecker.domain.gateway.IbanCheckerClientInterface;

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

```
