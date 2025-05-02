package org.example;

public class DividendEtf extends DividendSecurity {
    public DividendEtf(String ticker, int shareCount, double originalPrice,
                       double currentPrice, double assumedAnnualReturn, double dividendYield) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, dividendYield);
    }
}
