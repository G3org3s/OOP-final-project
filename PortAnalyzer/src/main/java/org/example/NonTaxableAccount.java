package org.example;

public class NonTaxableAccount extends Account implements TaxableIncomeCalculable {
    public NonTaxableAccount(String accountId, String ownerId) {
        super(accountId, ownerId);
    }

    @Override
    public double calculateTaxableIncome() {
        //todo
        return 0;
    }
}
