package nl.abna.ibanchecker.infrastructure.gateway;

public class AccessToken {
    public final String token;
    public final Integer expiresIn;

    public AccessToken(String token, Integer expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }
}
