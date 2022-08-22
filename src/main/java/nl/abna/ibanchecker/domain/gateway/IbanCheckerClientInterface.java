package nl.abna.ibanchecker.domain.gateway;

import nl.abna.ibanchecker.domain.gateway.exceptions.BadResponseException;

public interface IbanCheckerClientInterface {
    IbanCheckResponse checkIban(final String iban, final String name) throws BadResponseException;
}
