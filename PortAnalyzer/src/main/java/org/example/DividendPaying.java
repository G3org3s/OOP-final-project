package org.example;

public interface DividendPaying {
    default double projectDividend(int years) {
        return 0.0;
    }
}
