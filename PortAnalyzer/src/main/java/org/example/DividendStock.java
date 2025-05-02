package org.example;

import java.time.LocalDate;

public class DividendStock extends DividendSecurity {
    public DividendStock(String ticker, int shareCount, double originalPrice,
                         double currentPrice, double assumedAnnualReturn, double dividendYield, LocalDate localDate) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, dividendYield);
    }

    public DividendStock(String ticker, int shareCount, double originalPrice, double currentPrice, double assumedAnnualReturn,
                         LocalDate buyDate) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, buyDate);
    }
}
