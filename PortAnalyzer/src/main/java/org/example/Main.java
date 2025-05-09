package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) {
        System.out.println(LocalDate.now());
    }

    /**
     * Reads initial account and security data from a CSV file containing purchase information,
     * and a second CSV containing updated market prices. Combines them to construct
     * a list of populated Account objects with up-to-date valuations.
     * @param filePath path to the file containing information
     * @throws IOException if either file cannot be read.
     * @throws DataFormatException if any CSV row is malformed.
     */
    public static void readData(String filePath, Account account)
            throws IOException, DataFormatException {

        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/M/d");

        File file = new File(filePath);

        try (Scanner scanner = new Scanner(file)) {
            boolean isHeader = true;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (isHeader) {
                    isHeader = false;
                    continue; // Skip the header row
                }

                String[] values = line.split(",");

                if (values.length >= 8) { // Ensure we have enough columns
                    try {
                        String ticker = values[0].trim();
                        int shareCount = Integer.parseInt(values[1].trim());
                        double originalPrice = Double.parseDouble(values[2].trim());
                        double currentPrice = Double.parseDouble(values[3].trim());
                        double assumedAnnualReturn = Double.parseDouble(values[4].trim());
                        LocalDate buyDate = LocalDate.parse(values[5].trim(), DATE_FORMATTER);
                        double dividend = Double.parseDouble(values[6].trim());
                        String type = values[7].trim().toLowerCase();

                        if (dividend == 0) {
                            if (type.equalsIgnoreCase("stock")) {
                                Security newSecurity = new CommonStock(ticker, shareCount, originalPrice, currentPrice,
                                        assumedAnnualReturn, buyDate);
                                account.addSecurity(newSecurity);
                            }
                            else if (type.equalsIgnoreCase("etf")) {
                                Security newSecurity = new CommonEtf(ticker, shareCount, originalPrice, currentPrice,
                                        assumedAnnualReturn, buyDate);
                                account.addSecurity(newSecurity);
                            }
                        }
                        if (dividend > 0) {
                            if (type.equalsIgnoreCase("stock")) {
                                Security newSecurity = new DividendStock(ticker, shareCount, originalPrice, currentPrice,
                                        assumedAnnualReturn, dividend, buyDate);
                                account.addSecurity(newSecurity);
                            }
                            if (type.equalsIgnoreCase("etf")) {
                                Security newSecurity = new DividendEtf(ticker, shareCount, originalPrice, currentPrice,
                                        assumedAnnualReturn, buyDate, dividend);
                                account.addSecurity(newSecurity);
                            }
                        }

                    } catch (NumberFormatException | java.time.format.DateTimeParseException e) {
                        System.err.println("Skipping row due to parsing error: " + e.getMessage());
                    }
                } else {
                    System.err.println("Skipping row due to insufficient columns: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: CSV file not found at " + filePath);
        }
    }
}