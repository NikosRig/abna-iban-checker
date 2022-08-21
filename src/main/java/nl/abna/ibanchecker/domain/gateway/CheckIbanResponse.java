package nl.abna.ibanchecker.domain.gateway;

public class CheckIbanResponse {
    public final boolean isValid;

    public CheckIbanResponse(final boolean isValid ) {
        this.isValid = isValid;
    }
}
