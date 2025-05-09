package org.example;

import java.time.LocalDate;

public class DividendStock extends DividendSecurity {
    public DividendStock(String ticker, int shareCount, double originalPrice,
                         double currentPrice, double assumedAnnualReturn, double dividendYield, LocalDate buyDate) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, dividendYield);
    }
}
