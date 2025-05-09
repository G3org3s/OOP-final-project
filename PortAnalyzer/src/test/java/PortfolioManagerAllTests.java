
import org.example.Account;
import org.example.CommonStock;
import org.example.DividendStock;
import org.example.TaxableAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PortfolioManagerAllTests {

    @Test
    public void testGetMarketValue() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 0.05, LocalDate.now().minusYears(2)
        );
        Assertions.assertEquals(100 * 12.0, common.getMarketValue(), 1e-6);
    }

    @Test
    public void testCalculateProfit() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 0.05, LocalDate.now().minusYears(2)
        );
        double expected = (12.0 - 10.0) * 100 * Math.pow(1.05, 2);
        Assertions.assertEquals(expected, common.calculateFutureProfit(2.0), 1e-6);
    }

    @Test
    public void testProjectDividend() {
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 0.04, 4, LocalDate.now().minusYears(3)
        );
        double perYear = 25.0 * 0.04 * 50;
        Assertions.assertEquals(perYear * 3, dividend.projectDividend(3), 1e-6);
    }

    @Test
    public void testTotalDividends() {
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 0.04, 4, LocalDate.now().minusYears(3)
        );
        double avg = (20.0 + 25.0) / 2;
        double annual = avg * 0.04 * 50 * 3;
        Assertions.assertEquals(annual, dividend.totalDividends(), 1e-6);
    }

    @Test
    public void testCompareToDividendSecurity() {
        DividendStock div1 = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 0.04, 4, LocalDate.now().minusYears(3)
        );
        DividendStock div2 = new DividendStock(
                "LMN", 50, 20.0, 23.0, 0.02, 4, LocalDate.now().minusYears(3)
        );
        Assertions.assertTrue(div1.compareTo(div2) < 0);
    }

    @Test
    public void testGetPortfolioMarketValue() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 0.05, LocalDate.now().minusYears(2)
        );
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 0.04, 4, LocalDate.now().minusYears(3)
        );
        TaxableAccount acct = new TaxableAccount("A1", "user1");
        acct.addSecurity(common);
        acct.addSecurity(dividend);

        double expected = 100 * 12.0 + 50 * 25.0;
        Assertions.assertEquals(expected, acct.getPortfolioMarketValue(), 1e-6);
    }

    @Test
    public void testGetTotalProfit() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 0.05, LocalDate.now().minusYears(2)
        );
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 0.04, 4, LocalDate.now().minusYears(3)
        );
        TaxableAccount acct = new TaxableAccount("A1", "user1");
        acct.addSecurity(common);
        acct.addSecurity(dividend);

        double p1 = common.calculateFutureProfit(1.0);
        double p2 = dividend.calculateFutureProfit(1.0);
        Assertions.assertEquals(p1 + p2, acct.getTotalProfit(), 1e-6);
    }

    @Test
    public void testCalculateTaxableIncome() {
        CommonStock common = new CommonStock(
                "ABC", 100, 10.0, 12.0, 0.05, LocalDate.now().minusYears(2)
        );
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 0.04, 4, LocalDate.now().minusYears(3)
        );
        TaxableAccount acct = new TaxableAccount("A1", "user1");
        acct.addSecurity(common);
        acct.addSecurity(dividend);

        double totalProf = acct.getTotalProfit();
        Assertions.assertEquals(0.5 * totalProf, acct.calculateTaxableIncome(), 1e-6);
    }

    @Test
    public void testAccountComparator() {
        DividendStock dividend = new DividendStock(
                "XYZ", 50, 20.0, 25.0, 0.04, 4, LocalDate.now().minusYears(3)
        );
        TaxableAccount acct1 = new TaxableAccount("A1", "user1");
        acct1.addSecurity(dividend);
        TaxableAccount acct2 = new TaxableAccount("A2", "user2");
        Account.AccountComparator comp = new Account.AccountComparator();

        Assertions.assertTrue(comp.compare(acct1, acct2) == 0);
    }
}
