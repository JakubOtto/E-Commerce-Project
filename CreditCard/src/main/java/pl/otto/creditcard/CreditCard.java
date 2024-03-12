package pl.otto.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal creditLimit;

    public void assignCreditLimit(BigDecimal creditLimit) {
        if (isCreditAlreadyAssigned()){
            throw new CreditCantBeReassignException();
        }

        if (isCreditBelowTreshold(creditLimit)) {
            throw new CreditBelowThresholdException();
        }
        this.creditLimit = creditLimit;
    }

    private boolean isCreditAlreadyAssigned() {
        return this.creditLimit !=null;
    }

    private boolean isCreditBelowTreshold(BigDecimal creditLimit) {
        return creditLimit.compareTo(BigDecimal.valueOf(100)) < 0;

    }

    public BigDecimal getBalance() {
        return  creditLimit;
    }

}
