package nl.abna.ibanchecker.infrastructure.gateway;

public class AbnaCredentials {
    public final Boolean isSandboxMode;
    public final String clientId;
    public final String apiKey;

    public AbnaCredentials(
            final Boolean isSandboxMode,
            final String clientId,
            final String apiKey
    ) {
        this.isSandboxMode = isSandboxMode;
        this.clientId = clientId;
        this.apiKey = apiKey;
    }
}
