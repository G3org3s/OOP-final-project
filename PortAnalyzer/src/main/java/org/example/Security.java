package org.example;

import java.time.LocalDate;
import java.util.Objects;

public abstract class Security implements Comparable<Security> {
    protected String ticker;
    protected int shareCount;
    protected double originalPrice;
    protected double currentPrice;
    protected double assumedAnnualReturn; //can be assumed with things like average profit (%) in last 5 years
    protected LocalDate buyDate;

    public Security(String ticker, int shareCount, double originalPrice,
                    double currentPrice, double assumedAnnualReturn) {
        this.ticker = ticker;
        this.shareCount = shareCount;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.assumedAnnualReturn = assumedAnnualReturn;
        this.buyDate = LocalDate.now();
    }

    /**
     * calculates future profits for an inputted amount of years
     * the assumedAnnualReturn must be divided by 100 since its in percentage form and need to be converted
     * @param years number of years you want to project for
     * @return profit generated in that number of years
     */
    public double calculateProfit(double years) {
        return (currentPrice - originalPrice) * shareCount * Math.pow(1 + assumedAnnualReturn / 100, years);
    }

    /**
     * calculates the market value of the shares you own
     * @return your shareCount * currentPrice, which is the value of
     * your shares if you liquidated at that price
     */
    public double getMarketValue() {
        return shareCount * currentPrice;
    }

    /**
     * compares two securities based on percentage increase of the share price
     * in descending order
     * @param o the object to be compared.
     * @return the descending ordering of the 2 securities
     */
    @Override
    public int compareTo(Security o) {
        double thisProfit = (this.currentPrice - this.originalPrice) / this.originalPrice;
        double otherProfit = (o.currentPrice - o.originalPrice) / o.originalPrice;
        return Double.compare(otherProfit, thisProfit);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Security security = (Security) o;
        return shareCount == security.shareCount && Double.compare(originalPrice, security.originalPrice) == 0 && Double.compare(currentPrice, security.currentPrice) == 0 && Double.compare(assumedAnnualReturn, security.assumedAnnualReturn) == 0 && Objects.equals(ticker, security.ticker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticker, shareCount, originalPrice, currentPrice, assumedAnnualReturn);
    }

    @Override
    public String toString() {
        return "Security{" +
                "ticker='" + ticker + '\'' +
                ", shareCount=" + shareCount +
                ", originalPrice=" + originalPrice +
                ", currentPrice=" + currentPrice +
                ", assumedAnnualReturn=" + assumedAnnualReturn +
                '}';
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getAssumedAnnualReturn() {
        return assumedAnnualReturn;
    }

    public void setAssumedAnnualReturn(double assumedAnnualReturn) {
        this.assumedAnnualReturn = assumedAnnualReturn;
    }
}
