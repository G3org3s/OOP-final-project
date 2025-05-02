package org.example;

public class CommonStock extends CommonSecurity {
    public CommonStock(String ticker, int shareCount, double originalPrice,
                       double currentPrice, double assumedAnnualReturn) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
    }
}
