package nl.abna.ibanchecker;

import nl.abna.ibanchecker.domain.gateway.CheckIbanResponse;

public class ExampleMain {
    public static void main( String[] args ) throws Exception {
        //2OCXj2iI98VeEtCefSw9rHZPRVapW5hw
        IbanCheckerConfig config = new IbanCheckerConfig(
                "./var/keyStore.p12",
                true,
                "test_client",
                "", //set your api key here
                ""
        );
        IbanChecker ibanChecker = new IbanChecker(config);
        CheckIbanResponse checkIbanResponse = ibanChecker.check("NL58ABNA9999142181","Doortje Doorzon");
    }
}
