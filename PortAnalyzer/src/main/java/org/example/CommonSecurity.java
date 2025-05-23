package org.example;

import java.time.LocalDate;

public abstract class CommonSecurity extends Security {
    public CommonSecurity(String ticker, int shareCount, double originalPrice,
                          double currentPrice, double assumedAnnualReturn) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
    }

    public CommonSecurity(String ticker, int shareCount, double originalPrice,
                          double currentPrice, double assumedAnnualReturn, LocalDate buyDate) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, buyDate);
    }
}
