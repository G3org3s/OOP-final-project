package org.example;

public abstract class CommonSecurity extends Security {
    public CommonSecurity(String ticker, int shareCount, double originalPrice,
                          double currentPrice, double assumedAnnualReturn) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
    }
}
