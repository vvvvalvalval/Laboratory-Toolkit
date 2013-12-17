/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.capital;

import accounting.documents.BalanceSheet;
import accounting.equipment.AccountingEquipment;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that calculates the Net Debt.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingNetDebt extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {
        BalanceSheet bS = context.equipment().balanceSheet();

        double debt = bS.bankDebt() + bS.accountPayables() + bS.longTermDebt()
                - bS.stockholderEquity();

        double netDebt = debt - bS.cash();

        logger.log(Level.INFO, "Just calculated the Net Debt.");

        return netDebt;
    }
    private static final Logger logger = Logger.getLogger(CalculatingNetDebt.class.getName());

    private CalculatingNetDebt() {
    }

    public static CalculatingNetDebt getInstance() {
        return CalculatingNetDebtHolder.INSTANCE;
    }

    private static class CalculatingNetDebtHolder {

        private static final CalculatingNetDebt INSTANCE = new CalculatingNetDebt();
    }
}
