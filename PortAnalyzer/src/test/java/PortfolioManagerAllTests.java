
import org.example.Account;
import org.example.CommonStock;
import org.example.DividendStock;
import org.example.TaxableAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PortfolioManagerAllTests {

    @Test
    public void testGetMarketValue() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 4, LocalDate.now().minusYears(2)
        );
        Assertions.assertEquals(100 * 12.0, common.getMarketValue(), 1e-6);
    }

    @Test
    public void testProjectDividend() {
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 4, 4, LocalDate.now().minusYears(3)
        );
        double expected = 162.5;
        Assertions.assertEquals(expected, dividend.projectDividend(3), 0.5);
    }

    @Test
    public void testTotalDividends() {
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 4, 4, LocalDate.now().minusYears(3)
        );
        double avg = (20.0 + 25.0) / 2;
        double annual = avg * 0.04 * 50 * 3;
        Assertions.assertEquals(annual, dividend.totalDividends(), 1e-6);
    }

    @Test
    public void testCompareToDividendSecurity() {
        DividendStock div1 = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 5, 4, LocalDate.now().minusYears(3)
        );
        DividendStock div2 = new DividendStock(
                "LMN", 50, 20.0, 23.0, 4, 4, LocalDate.now().minusYears(3)
        );
        assertTrue(div1.compareTo(div2) < 0);
    }

    @Test
    public void testGetPortfolioMarketValue() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 5, LocalDate.now().minusYears(2)
        );
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 4, 4, LocalDate.now().minusYears(3)
        );
        TaxableAccount acct = new TaxableAccount("A1");
        acct.addSecurity(common);
        acct.addSecurity(dividend);

        double expected = 100 * 12.0 + 50 * 25.0;
        Assertions.assertEquals(expected, acct.getPortfolioMarketValue(), 1e-6);
    }

    @Test
    public void testGetTotalProfit() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 5, LocalDate.now().minusYears(2)
        );
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 4, 4, LocalDate.now().minusYears(3)
        );
        TaxableAccount acct = new TaxableAccount("A1");
        acct.addSecurity(common);
        acct.addSecurity(dividend);

        double p1 = common.getMarketValue() - common.getOriginalPrice() * common.getShareCount();
        double p2 = dividend.getMarketValue() - dividend.getOriginalPrice() * dividend.getShareCount();
        Assertions.assertEquals(p1 + p2, acct.getTotalProfit(), 0.5);
    }

    @Test
    public void testCalculateTaxableIncome() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 5, LocalDate.now().minusYears(2)
        );
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 4, 4, LocalDate.now().minusYears(3)
        );
        TaxableAccount acct = new TaxableAccount("A1");
        acct.addSecurity(common);
        acct.addSecurity(dividend);

        double totalProf = acct.getTotalProfit();
        Assertions.assertEquals(0.5 * totalProf, acct.calculateTaxableIncome(), 1e-6);
    }

    @Test
    public void testAccountComparator() {
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 4, 4, LocalDate.now().minusYears(3)
        );
        DividendStock dividend2 = new DividendStock(
                "XYZ", 50, 20.0, 1000.0, 4, 4, LocalDate.now().minusYears(3)
        );
        Account acct1 = new TaxableAccount("A1");
        Account acct2 = new TaxableAccount("A2");
        acct1.addSecurity(dividend);
        acct2.addSecurity(dividend2);

        Account.AccountComparator comparator = new Account.AccountComparator();

        // Act: Compare the two accounts
        int result = comparator.compare(acct1, acct2);

        // Assert: Expect a positive result because account1 should come before account2
        // in descending order based on profit (1500.50 > 1000.25)
        assertTrue(result > 0);
    }

    @Test
    public void testFutureProfit() {
        CommonStock common = new CommonStock(
                "ABC", 5, 98, 110.0, 7.0, LocalDate.now().minusYears(2)
        );

        double initialProfit = (common.getCurrentPrice() - common.getOriginalPrice()) * common.getShareCount();
        double expectedFutureProfit = initialProfit * Math.pow(1 + common.getAssumedAnnualReturn() / 100.0, 5.0);

        Assertions.assertEquals(expectedFutureProfit, common.calculateFutureProfit(5.0), 0.01);
    }

}
