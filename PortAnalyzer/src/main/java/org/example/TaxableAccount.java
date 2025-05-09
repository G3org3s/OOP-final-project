package org.example;

public class TaxableAccount extends Account implements TaxableIncomeCalculable {
    public TaxableAccount(String accountId, String ownerId) {
        super(accountId, ownerId);
    }

    @Override
    public double calculateTaxableIncome() {
        if (this.getTotalProfit() > 0) {
            return this.getTotalProfit() / 2;
        } else {
            return 0.0;
        }
    }
}
