import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.DataFormatException;

import static org.example.Main.readData;

public class MainTest {
    /*
    The export method can be tested by simply generating a report and checking that all the desired values are there
    There is no cost to generating a report as that is all the program does.

    Example report generated:
    Account Report for ID: 0
Holdings:
-> AAPL: 10 shares @ $175.0 | Profit (%) -> 16.666666666666664 | Avg Annual Return -> 34.76190476190476 | Projected returns over 5 years ($) -> 402.6275000000001 | Dividends earned ($ estimate) -> 0.0 | Projected dividends over 5 years ($) -> 94.01854000000003
-> AMZN: 7 shares @ $135.0 | Profit (%) -> 12.5 | Avg Annual Return -> 57.03125 | Projected returns over 5 years ($) -> 176.93110628550008
-> GOOG: 3 shares @ $2700.0 | Profit (%) -> 8.0 | Avg Annual Return -> 4.430955993930197 | Projected returns over 5 years ($) -> 1057.4050099200003
-> MSFT: 12 shares @ $310.0 | Profit (%) -> 10.714285714285714 | Avg Annual Return -> 7.506169454346037 | Projected returns over 5 years ($) -> 553.9046237640001 | Dividends earned ($ estimate) -> 38.94 | Projected dividends over 5 years ($) -> 266.9348503957081
-> NVDA: 5 shares @ $110.0 | Profit (%) -> 12.244897959183673 | Avg Annual Return -> 1489.795918367347 | Projected returns over 5 years ($) -> 84.15310384200002
-> SPY: 15 shares @ $420.0 | Profit (%) -> 5.0 | Avg Annual Return -> 5.650154798761609 | Projected returns over 5 years ($) -> 420.7655192100001 | Dividends earned ($ estimate) -> 0.0 | Projected dividends over 5 years ($) -> 503.9545116633301
-> TSLA: 8 shares @ $780.0 | Profit (%) -> -2.5 | Avg Annual Return -> -7.668067226890757 | Projected returns over 5 years ($) -> -321.8171499999999
-> VOO: 20 shares @ $380.0 | Profit (%) -> 8.571428571428571 | Avg Annual Return -> 7.2086899275839365 | Projected returns over 5 years ($) -> 881.5968460800003 | Dividends earned ($ estimate) -> 109.5 | Projected dividends over 5 years ($) -> 722.2959101952001

Total Market Value: $35205.00
Total Profit (without dividends): $2115.00
Estimated earned dividends ($): $148.44
Taxable Income Estimate: $1057.50


Account Report for ID: 1
Holdings:
-> AAPL: 10 shares @ $175.0 | Profit (%) -> 16.666666666666664 | Avg Annual Return -> 34.76190476190476 | Projected returns over 5 years ($) -> 402.6275000000001 | Dividends earned ($ estimate) -> 0.0 | Projected dividends over 5 years ($) -> 94.01854000000003
-> AMZN: 7 shares @ $135.0 | Profit (%) -> 12.5 | Avg Annual Return -> 57.03125 | Projected returns over 5 years ($) -> 176.93110628550008
-> GOOG: 3 shares @ $2700.0 | Profit (%) -> 8.0 | Avg Annual Return -> 4.430955993930197 | Projected returns over 5 years ($) -> 1057.4050099200003
-> MSFT: 12 shares @ $310.0 | Profit (%) -> 10.714285714285714 | Avg Annual Return -> 7.506169454346037 | Projected returns over 5 years ($) -> 553.9046237640001 | Dividends earned ($ estimate) -> 38.94 | Projected dividends over 5 years ($) -> 266.9348503957081
-> NVDA: 5 shares @ $110.0 | Profit (%) -> 12.244897959183673 | Avg Annual Return -> 1489.795918367347 | Projected returns over 5 years ($) -> 84.15310384200002
-> SPY: 15 shares @ $420.0 | Profit (%) -> 5.0 | Avg Annual Return -> 5.650154798761609 | Projected returns over 5 years ($) -> 420.7655192100001 | Dividends earned ($ estimate) -> 0.0 | Projected dividends over 5 years ($) -> 503.9545116633301
-> TSLA: 8 shares @ $780.0 | Profit (%) -> -2.5 | Avg Annual Return -> -7.668067226890757 | Projected returns over 5 years ($) -> -321.8171499999999
-> VOO: 20 shares @ $380.0 | Profit (%) -> 8.571428571428571 | Avg Annual Return -> 7.2086899275839365 | Projected returns over 5 years ($) -> 881.5968460800003 | Dividends earned ($ estimate) -> 109.5 | Projected dividends over 5 years ($) -> 722.2959101952001

Total Market Value: $35205.00
Total Profit (without dividends): $2115.00
Estimated earned dividends ($): $148.44


All total values given turn out to be correct and it displays the information needed.
     */
    @Test
    public void testRead() throws DataFormatException, IOException {
        Account A1 = new TaxableAccount("A1");
        readData("src/main/resources/TaxableAccount", A1);

        Map<String, Security> securityMap = new TreeMap<>();
        // NVDA,5,98,110,7,2025/5/6,0,etf
        Security nvdaSecurity = new CommonEtf("NVDA", 5, 98.0, 110.0, 7.0,
                LocalDate.of(2025, Month.MAY, 6));
        securityMap.put(nvdaSecurity.getTicker(), nvdaSecurity);

        // AAPL,10,150,175,10,2024/11/15,0.8,stock
        Security aaplSecurity = new DividendStock("AAPL", 10, 150.0, 175.0, 10.0, 0.8,
                LocalDate.of(2024, Month.NOVEMBER, 15));
        securityMap.put(aaplSecurity.getTicker(), aaplSecurity);

        // GOOG,3,2500,2700,12,2023/7/20,0,stock
        Security googSecurity = new CommonStock("GOOG", 3, 2500.0, 2700.0, 12.0,
                LocalDate.of(2023, Month.JULY, 20));
        securityMap.put(googSecurity.getTicker(), googSecurity);

        // TSLA,8,800,780,15,2025/1/10,0,stock
        Security tslaSecurity = new CommonStock("TSLA", 8, 800.0, 780.0, 15.0,
                LocalDate.of(2025, Month.JANUARY, 10));
        securityMap.put(tslaSecurity.getTicker(), tslaSecurity);
        // VOO,20,350,380,8,2024/3/1,1.5,etf
        Security vooSecurity = new DividendEtf("VOO", 20, 350.0, 380.0, 8.0,
                LocalDate.of(2024, Month.MARCH, 1), 1.5);
        securityMap.put(vooSecurity.getTicker(), vooSecurity);

        // MSFT,12,280,310,9,2023/12/5,1.1,stock
        Security msftSecurity = new DividendStock("MSFT", 12, 280.0, 310.0, 9.0, 1.1,
                LocalDate.of(2023, Month.DECEMBER, 5));
        securityMap.put(msftSecurity.getTicker(), msftSecurity);

        // SPY,15,400,420,7,2024/6/20,1.3,etf
        Security spySecurity = new DividendEtf("SPY", 15, 400.0, 420.0, 7.0,
                LocalDate.of(2024, Month.JUNE, 20), 1.3);
        securityMap.put(spySecurity.getTicker(), spySecurity);

        // AMZN,7,120,135,11,2025/2/18,0,stock
        Security amznSecurity = new CommonStock("AMZN", 7, 120.0, 135.0, 11.0,
                LocalDate.of(2025, Month.FEBRUARY, 18));
        securityMap.put(amznSecurity.getTicker(), amznSecurity);

        Assertions.assertEquals(securityMap, A1.getHoldings());
    }

}
