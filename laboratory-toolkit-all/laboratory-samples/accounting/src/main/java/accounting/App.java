package accounting;

import accounting.capital.SummingAssets;
import accounting.documents.BalanceSheet;
import accounting.documents.IncomeStatement;
import accounting.equipment.AccountingEquipment;
import accounting.equipment.SimpleAccountingEquipment;
import accounting.indicators.CalculatingNetIncome;
import accounting.profitability.CalculatingROCE;
import accounting.profitability.CalculatingROE;
import accounting.profitability.EstimatingLeverageEffect;
import edu.polytechnique.labtk.Laboratory;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Let's have a look at the financial state of MyCompany :");
        System.out.println("");
        
        System.out.println("BALANCE SHEET :");
        System.out.println(printBalanceSheet(SAMPLE_BALANCE_SHEET));
        System.out.println("");
        
        System.out.println("INCOME STATEMENT :");
        System.out.println(printIncomeStatement(SAMPLE_INCOME_STATEMENT));
        System.out.println("");
        
        AccountingEquipment equipment = new SimpleAccountingEquipment(SAMPLE_BALANCE_SHEET, SAMPLE_INCOME_STATEMENT);
        Laboratory<AccountingEquipment> lab = new Laboratory<>(equipment);
        
        System.out.print("The total capital of the company is : " );
        System.out.println(SummingAssets.getInstance().getResult(lab) + "$");
        
        System.out.print("The Net Income for this financial year is : ");
        System.out.println(CalculatingNetIncome.getInstance().getResult(lab) + "$");

        System.out.println("Now let's evaluate the profitability of the company with some ratios.");
        
        System.out.println("The Return On Capital Employed is :");
        System.out.println("ROCE = " + CalculatingROCE.getInstance().getResult(lab));
        
        System.out.println("The Return On Equity is :");
        System.out.println("ROE = " + CalculatingROE.getInstance().getResult(lab));
        
        System.out.println("The Leverage Effect (how equity is leveraged by debt) is estimated to :");
        System.out.println("lev = " + EstimatingLeverageEffect.getInstance().getResult(lab));
        
    }

    private static String printBalanceSheet(BalanceSheet bS) {
        StringBuilder sb = new StringBuilder()
                .append("ASSETS :\n")
                .append("Fixed assets : ").append(bS.fixedAssets()).append("$\n")
                .append("Inventories : ").append(bS.inventories()).append("$\n")
                .append("Accounts receivables : ").append(bS.accountReceivables()).append("$\n")
                .append("Cash and cash equivalents : ").append(bS.cash()).append("$\n")
                .append("LIABILITIES :\n")
                .append("Stockholder equity : ").append(bS.stockholderEquity()).append("$\n")
                .append("Long term debt : ").append(bS.longTermDebt()).append("$\n")
                .append("Bank debt : ").append(bS.bankDebt()).append("$\n")
                .append("Accounts payables : ").append(bS.accountPayables()).append("$\n");
        return sb.toString();
    }

    private static String printIncomeStatement(IncomeStatement is) {
        StringBuilder sb = new StringBuilder()
                .append("Operating revenues : ").append(is.operatingRevenues()).append("$\n")
                .append("Operating expenses : ").append(is.operatingExpenses()).append("$\n")
                .append("Amotizations : ").append(is.amortizations()).append("$\n")
                .append("Provisions : ").append(is.provisions()).append("$\n")
                .append("Financial revenues : ").append(is.financialRevenues()).append("$\n")
                .append("Financial expenses : ").append(is.financialExpenses()).append("$\n")
                .append("Income tax : ").append(is.incomeTax()).append("$\n")
                .append("Exceptional Income : ").append(is.exceptionalIncome()).append("$\n");
        return sb.toString();
    }
    private static final BalanceSheet SAMPLE_BALANCE_SHEET = new BalanceSheet() {
        @Override
        public double fixedAssets() {
            return 5_000_000;
        }

        @Override
        public double inventories() {
            return 2_000_000;
        }

        @Override
        public double accountReceivables() {
            return 500_000;
        }

        @Override
        public double cash() {
            return 500_000;
        }

        @Override
        public double stockholderEquity() {
            return 1_500_000;
        }

        @Override
        public double longTermDebt() {
            return 3_500_000;
        }

        @Override
        public double bankDebt() {
            return 2_000_000;
        }

        @Override
        public double accountPayables() {
            return 1_000_000;
        }
    };
    private static final IncomeStatement SAMPLE_INCOME_STATEMENT = new IncomeStatement() {
        @Override
        public double operatingRevenues() {
            return 5_000_000;
        }

        @Override
        public double operatingExpenses() {
            return 4_300_000;
        }

        @Override
        public double amortizations() {
            return 500_000;
        }

        @Override
        public double provisions() {
            return 300_000;
        }

        @Override
        public double financialRevenues() {
            return 2_000_000;
        }

        @Override
        public double financialExpenses() {
            return 2_400_000;
        }

        @Override
        public double incomeTax() {
            return 50_000;
        }

        @Override
        public double exceptionalIncome() {
            return 20_000;
        }
    };
}
