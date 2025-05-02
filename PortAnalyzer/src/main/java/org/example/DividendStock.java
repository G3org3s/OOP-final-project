package org.example;

public class DividendStock extends DividendSecurity {
    public DividendStock(String ticker, int shareCount, double originalPrice,
                         double currentPrice, double assumedAnnualReturn, double dividendYield) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, dividendYield);
    }
}
