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

        client.checkIban("NL58ABNA9999142181","Doortje Doorzon");
    }
}
