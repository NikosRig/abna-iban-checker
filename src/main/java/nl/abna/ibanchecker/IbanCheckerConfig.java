package nl.abna.ibanchecker;

public class IbanCheckerConfig {
    public final String pkcs12Path;
    public final Boolean isSandboxMode;
    public final String clientId;
    public final String apiKey;
    public final String pkcs12Pass;

    public IbanCheckerConfig(
            final String pkcs12Path,
            final Boolean isSandboxMode,
            final String clientId,
            final String apiKey,
            final String pkcs12Pass
    ) {
        this.pkcs12Path = pkcs12Path;
        this.isSandboxMode = isSandboxMode;
        this.clientId = clientId;
        this.apiKey = apiKey;
        this.pkcs12Pass = pkcs12Pass;
    }
}
