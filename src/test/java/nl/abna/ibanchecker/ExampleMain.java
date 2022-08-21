package nl.abna.ibanchecker;

import nl.abna.ibanchecker.domain.gateway.IbanCheckerGatewayInterface;

public class ExampleMain {
    public static void main( String[] args ) throws Exception {
        IbanCheckerGatewayInterface gateway = new IbanCheckerGatewayBuilder()
                .enableSandboxMode()
                .withApiKey("your-api-key")
                .withClientId("test_client")
                .withPkcs12("./var/keyStore.p12", "")
                .build();

        gateway.checkIban("NL58ABNA9999142181","Doortje Doorzon");
    }
}
