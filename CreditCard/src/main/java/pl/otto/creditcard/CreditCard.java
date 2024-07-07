package pl.otto.creditcard;

import java.math.BigDecimal;

public class CreditCard {
    private BigDecimal creditLimit;
    private BigDecimal balance;

    public void assignCreditLimit(BigDecimal creditLimit) {
        if (isCreditAlreadyAssigned()){
            throw new CreditCantBeReassignException();
        }

        if (isCreditBelowTreshold(creditLimit)) {
            throw new CreditBelowThresholdException();
        }
        this.creditLimit = creditLimit;
        this.balance = creditLimit;
    }

    private boolean isCreditAlreadyAssigned() {
        return this.creditLimit !=null;
    }

    private boolean isCreditBelowTreshold(BigDecimal creditLimit) {
        return creditLimit.compareTo(BigDecimal.valueOf(100)) < 0;

    }

    public BigDecimal getBalance() {
        return  balance;
    }

    public void pay(BigDecimal money) {
        if (!canAfford(money)){
            throw new InsufficientFundsException();
        }
        this.balance = this.balance.subtract(money);
    }

    private boolean canAfford(BigDecimal money) {
        return this.balance.subtract(money).compareTo(BigDecimal.ZERO)>0;
    }
}
