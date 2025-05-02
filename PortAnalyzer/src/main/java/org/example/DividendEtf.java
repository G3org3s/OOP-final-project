package org.example;

import java.time.LocalDate;

public class DividendEtf extends DividendSecurity {
    public DividendEtf(String ticker, int shareCount, double originalPrice,
                       double currentPrice, double assumedAnnualReturn, double dividendYield) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, dividendYield);
    }

    public DividendEtf(String ticker, int shareCount, double originalPrice,
                       double currentPrice, double assumedAnnualReturn, LocalDate buyDate) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, buyDate);
    }
}
