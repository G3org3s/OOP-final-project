package org.example;

import com.sun.source.tree.Tree;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public abstract class DividendSecurity extends Security implements DividendPaying {
    protected double dividendYield; //input is in percentage
    protected TreeMap<LocalDate, Double> dividendPayouts;

    public DividendSecurity(String ticker, int shareCount, double originalPrice,
                            double currentPrice, double assumedAnnualReturn, double dividendYield) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
        this.dividendPayouts = new TreeMap<>();
        this.dividendYield = dividendYield;
    }

    /**
     * projects the amount of money earned from dividends based on:
     * 1. a stocks assumed return
     * 2. a stocks current dividend rate
     * @param years number of years to project
     * @return the dollar amount earned from dividends
     */
    @Override
    public double projectDividend(int years) {
        double totalDividend = 0.0;
        double futurePrice = currentPrice;

        for (int i = 1; i <= years; i++) {
            // Grow the price based on assumedAnnualReturn
            futurePrice *= (1 + (assumedAnnualReturn / 100));
            // Annual dividend payout for all shares
            double annualDividend = futurePrice * (dividendYield / 100) * shareCount;
            totalDividend += annualDividend;
        }

        return totalDividend;
    }

    /**
     * calculates the dollar amount earned from dividends
     * Assumes dividend percentage didn't change
     * Assumes change in price was gradual, so the price used for calculation is an average of
     * current and original price
     * @return the dollar amount earned from dividends
     */
    public double totalDividends() {
        double totalDividends = 0.0;

        LocalDate currentDate = LocalDate.now();

        long yearsOwned = ChronoUnit.YEARS.between(buyDate, currentDate);

        // If the stock has been owned for less than a year, adjust accordingly
        if (yearsOwned < 1) {
            return 0;
        }
        double averagePrice = (currentPrice + originalPrice) / 2;
        double annualDividend = averagePrice * (dividendYield / 100);
        totalDividends = annualDividend * yearsOwned * shareCount;

        return totalDividends;
    }

    /**
     * compares dividend yielding securities based on the percentage earned in dividends and
     * the percentage the stock grew
     * @param o the object to be compared.
     * @return comparison of the 2 securities in descending order
     */
    @Override
    public int compareTo(Security o) {
        // Make sure to handle the case where other is not an instance of DividendSecurity
        if (!(o instanceof DividendSecurity)) {
            throw new IllegalArgumentException("Cannot compare a DividendSecurity with a non-DividendSecurity");
        }

        double thisGrowthPercentage = ((currentPrice - originalPrice) / originalPrice) * 100;
        double thisDividendEarningsPercentage = (this.totalDividends()) /
                (this.shareCount * this.currentPrice);

        double otherGrowthPercentage = ((o.getCurrentPrice() - o.getOriginalPrice()) / o.getOriginalPrice()) * 100;
        double otherDividendEarningsPercentage = ((DividendSecurity) o).totalDividends() /
                (o.getShareCount() * o.getCurrentPrice());

        double thisTotalPercentage = thisGrowthPercentage + thisDividendEarningsPercentage;
        double otherTotalPercentage = otherGrowthPercentage + otherDividendEarningsPercentage;

        return Double.compare(otherTotalPercentage, thisTotalPercentage);
    }
}
