package nl.abna.ibanchecker;

import nl.abna.ibanchecker.domain.gateway.IbanCheckerClientInterface;
import nl.abna.ibanchecker.infrastructure.gateway.AbnaCredentials;
import nl.abna.ibanchecker.infrastructure.gateway.AbnaIbanCheckerClient;
import nl.abna.ibanchecker.infrastructure.gateway.AbnaMutualTlsUtil;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.http.HttpClient;

public class IbanCheckerClientBuilder {
    private Boolean isSandboxMode = false;
    private String clientId;
    private String apiKey;
    private String pkcs12FilePath;
    private String pkcs12Pass;

    public IbanCheckerClientBuilder enableSandboxMode() {
        this.isSandboxMode = true;
        return this;
    }

    public IbanCheckerClientBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public IbanCheckerClientBuilder withApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public IbanCheckerClientBuilder withPkcs12(String pkcs12FilePath, String pkcs12Pass) {
        this.pkcs12FilePath = pkcs12FilePath;
        this.pkcs12Pass = pkcs12Pass;
        return this;
    }

    public IbanCheckerClientInterface build() throws Exception {
        AbnaCredentials credentials = new AbnaCredentials(
                this.isSandboxMode,
                this.clientId,
                this.apiKey
        );
        File pkcs12 = new File(this.pkcs12FilePath);
        InputStream pkcs12Stream = new FileInputStream(pkcs12);
        SSLContext sslContext = AbnaMutualTlsUtil.createSSLContext(pkcs12Stream, this.pkcs12Pass);
        HttpClient client = HttpClient.newBuilder().sslContext(sslContext).build();
        return new AbnaIbanCheckerClient(credentials, client);
    }
}
