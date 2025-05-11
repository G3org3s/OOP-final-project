package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.DataFormatException;

public class Main {
    public static void main(String[] args) throws DataFormatException, IOException {
        Account TaxableAccount = new TaxableAccount("0");
        readData("src/main/resources/TaxableAccount", TaxableAccount);

        Account NonTaxableAccount = new NonTaxableAccount("1");
        readData("src/main/resources/NonTaxableAccount", NonTaxableAccount);

        List<Account> accounts = new ArrayList<>();
        accounts.add(TaxableAccount);
        accounts.add(NonTaxableAccount);
        Comparator<Account> comparator = new Account.AccountComparator();
        accounts.sort(comparator);
        writeReport("src/main/resources/Report", Collections.unmodifiableList(accounts));
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
                        System.out.println("Skipping row due to parsing error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Skipping row due to insufficient columns: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: CSV file not found at " + filePath);
        }
    }

    /**
     * Writes the report for the accounts in the list of accounts created by the user
     * @param filePath file to write into
     * @param accounts List of accounts
     */
    public static void writeReport(String filePath, List<Account> accounts) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (Account account : accounts) {
                double dividendsEarned = 0.0;
                writer.write("Account Report for ID: " + account.getAccountId() + "\n");

                writer.write("Holdings:\n");
                for (Security s : account.getHoldings().values()) {
                    writer.write("-> " + s.getTicker() + ": " + s.getShareCount() + " shares @ $" +
                            s.getCurrentPrice() + " | " + "Profit (%) -> "  + s.calculatePercentageProfit() +
                            " | " + "Avg Annual Return -> " + s.getAvgAnnualizedReturn() + " | " +
                            "Projected returns over 5 years ($) -> " + s.calculateFutureProfit(5.0));
                    if (s instanceof DividendSecurity divSecurity) {
                        writer.write(" | " + "Dividends earned ($ estimate) -> " + ((DividendSecurity) s).totalDividends() +
                                " | " + "Projected dividends over 5 years ($) -> " + ((DividendSecurity) s).projectDividend(5)
                        + "\n");
                        dividendsEarned += ((DividendSecurity) s).totalDividends();
                    } else {
                        writer.write("\n");
                    }
                }

                double marketValue = account.getPortfolioMarketValue();
                double profit = account.getTotalProfit();;

                writer.write("\nTotal Market Value: $" + String.format("%.2f", marketValue) + "\n");
                writer.write("Total Profit (without dividends): $" + String.format("%.2f", profit) + "\n");
                writer.write("Estimated earned dividends ($): $" + String.format("%.2f", dividendsEarned) + "\n");

                if (account instanceof TaxableIncomeCalculable taxable) {
                    double taxableIncome = taxable.calculateTaxableIncome();
                    writer.write("Taxable Income Estimate: $" + String.format("%.2f", taxableIncome) + "\n \n \n");
                } else {
                    writer.write("\n \n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
