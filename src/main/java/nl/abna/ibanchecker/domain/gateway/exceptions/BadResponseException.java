package nl.abna.ibanchecker.domain.gateway.exceptions;

public class BadResponseException extends Exception {
    public BadResponseException(String message) {
        super(message);
    }
}
