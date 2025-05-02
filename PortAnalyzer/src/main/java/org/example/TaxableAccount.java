package org.example;

public class TaxableAccount extends Account implements TaxableIncomeCalculable {
    public TaxableAccount(String accountId, String ownerId) {
        super(accountId, ownerId);
    }

    @Override
    public double calculateTaxableIncome() {
        //todo
        return 0;
    }
}
