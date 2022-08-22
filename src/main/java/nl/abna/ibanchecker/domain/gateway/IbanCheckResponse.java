package nl.abna.ibanchecker.domain.gateway;

public class IbanCheckResponse {
    public final Boolean isIbanValid;
    public final Boolean isNameMatched;
    public final Boolean isForeignAccount;
    public final Boolean isAccountActive;
    public final String accountCountryCode;
    public final Integer totalAccountHolders;

    public IbanCheckResponse(
            final Boolean isIbanValid,
            final Boolean isNameMatched,
            final Boolean isForeignAccount,
            final Boolean isAccountActive,
            final String accountCountryCode,
            final Integer totalAccountHolders
    ) {
        this.isIbanValid = isIbanValid;
        this.isNameMatched = isNameMatched;
        this.isForeignAccount = isForeignAccount;
        this.isAccountActive = isAccountActive;
        this.accountCountryCode = accountCountryCode;
        this.totalAccountHolders = totalAccountHolders;
    }
}
