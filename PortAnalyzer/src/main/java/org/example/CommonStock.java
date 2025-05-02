package org.example;

import java.time.LocalDate;

public class CommonStock extends CommonSecurity {
    public CommonStock(String ticker, int shareCount, double originalPrice,
                       double currentPrice, double assumedAnnualReturn) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
    }

    public CommonStock(String ticker, int shareCount, double originalPrice,
                       double currentPrice, double assumedAnnualReturn, LocalDate buyDate) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, buyDate);
    }
}
