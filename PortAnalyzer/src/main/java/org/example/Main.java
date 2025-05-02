package org.example;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
    }

    /**
     * Reads initial account and security data from a CSV file containing purchase information,
     * and a second CSV containing updated market prices. Combines them to construct
     * a list of populated Account objects with up-to-date valuations.
     * @param initialDataPath path to the CSV file with original buy information.
     * @param currentPriceDataPath path to the CSV file with current prices.
     * @return a list of fully populated Account objects.
     * @throws IOException if either file cannot be read.
     * @throws DataFormatException if any CSV row is malformed.
     */
    public static List<Account> readData(String initialDataPath, String currentPriceDataPath)
            throws IOException, DataFormatException {
        //todo
    }
}