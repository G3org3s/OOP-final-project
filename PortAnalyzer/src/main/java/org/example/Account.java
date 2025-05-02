package org.example;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public abstract class Account implements Reportable {
    protected String accountId;
    protected String ownerId;
    protected Map<String, Security> holdings;

    public Account(String accountId, String ownerId) {
        this.accountId = accountId;
        this.ownerId = ownerId;
        this.holdings = new HashMap<>();
    }

    /**
     *
     * @param security
     */
    public void addSecurity(Security security) {
        //todo
    }

    /**
     * Calculates the total market value of all securities in this account.
     * @return the current market value of the portfolio.
     */
    public double getPortfolioMarketValue() {
        //todo
    }

    /**
     * Calculates the total absolute profit across all securities after a given number of years,
     * based on their individual growth assumptions.
     * @param years
     * @return
     */
    public double getTotalProfit(double years) {
        //todo
    }

    /**
     * Calculates the annualized return over a period of years.
     *
     * @param years number of years used in return calculation.
     * @return the compounded annual return as a percentage.
     */
    public double getAnnualizedReturn(double years) {
        //todo
    }

    /**
     * Writes a detailed report on the growth of the account (or loss)
     * @param outputFilePath file to output the report to
     * @throws IOException exception thrown
     */
    @Override
    public void writeReport(String outputFilePath) throws IOException {
        //todo
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Map<String, Security> getHoldings() {
        return holdings;
    }

    public void setHoldings(Map<String, Security> holdings) {
        this.holdings = holdings;
    }

    // Inner Comparator class for Account
    public static class AccountComparator implements Comparator<Account> {
        private final double years;

        public AccountComparator(double years) {
            this.years = years;
        }

        /**
         * Compares accounts based on the total profit earned
         * @param a1 the first object to be compared.
         * @param a2 the second object to be compared.
         * @return accounts in descending order
         */
        @Override
        public int compare(Account a1, Account a2) {
            return Double.compare(a2.getTotalProfit(years), a1.getTotalProfit(years)); // descending order
        }
    }
}

