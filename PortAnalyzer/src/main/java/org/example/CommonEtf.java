package org.example;

public class CommonEtf extends CommonSecurity {
    public CommonEtf(String ticker, int shareCount, double originalPrice,
                     double currentPrice, double assumedAnnualReturn) {
        super(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
    }
}
