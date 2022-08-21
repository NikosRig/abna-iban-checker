## ABNA Iban Checker (Under-construction)

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
> **_NOTE:_**  The library already contains a pkcs12 file with public ABNA's sandbox certificates.
&nbsp;

ExampleMain.java


```
package nl.abna.ibanchecker;

import nl.abna.ibanchecker.domain.gateway.IbanCheckerClientInterface;

public class ExampleMain {
    public static void main( String[] args ) throws Exception {
        IbanCheckerClientInterface client = new IbanCheckerClientBuilder()
                .enableSandboxMode()
                .withApiKey("2OCXj2iI98VeEtCefSw9rHZPRVapW5hw")
                .withClientId("test_client")
                .withPkcs12("./var/keyStore.p12", "")
                .build();

        client.checkIban("NL58ABNA9999142181","Doortje Doorzon");
    }
}

```
