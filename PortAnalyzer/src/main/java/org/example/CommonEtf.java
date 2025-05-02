package org.example;

import java.time.LocalDate;

public class CommonEtf extends CommonSecurity {
    public CommonEtf(String ticker, int shareCount, double originalPrice,
                     double currentPrice, double assumedAnnualReturn) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
    }

    public CommonEtf(String ticker, int shareCount,
                     double originalPrice, double currentPrice, double assumedAnnualReturn, LocalDate buyDate) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn, buyDate);
    }
}
