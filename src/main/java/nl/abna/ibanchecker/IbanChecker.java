package nl.abna.ibanchecker;

import nl.abna.ibanchecker.domain.gateway.CheckIbanResponse;
import nl.abna.ibanchecker.infrastructure.gateway.AbnaCredentials;
import nl.abna.ibanchecker.infrastructure.gateway.AbnaIbanCheckerGateway;
import nl.abna.ibanchecker.infrastructure.gateway.AbnaMutualTlsUtil;
import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.http.HttpClient;

public class IbanChecker {
    private AbnaIbanCheckerGateway gateway;

    public IbanChecker(IbanCheckerConfig config) throws Exception {
        this.setup(config);
    }

    private void setup(IbanCheckerConfig config) throws Exception {
        AbnaCredentials credentials = new AbnaCredentials(
                config.isSandboxMode,
                config.clientId,
                config.apiKey
        );
        File pkcs12 = new File(config.pkcs12Path);
        InputStream pkcs12Stream = new FileInputStream(pkcs12);
        SSLContext sslContext = AbnaMutualTlsUtil.createSSLContext(pkcs12Stream, config.pkcs12Pass);
        HttpClient client = HttpClient.newBuilder().sslContext(sslContext).build();
        this.gateway = new AbnaIbanCheckerGateway(credentials, client);
    }

    public CheckIbanResponse check(String iban, String name) throws Exception {
       return this.gateway.checkIban(iban, name);
    }
}
