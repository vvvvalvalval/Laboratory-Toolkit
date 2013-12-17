/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.profitability;

import accounting.capital.SummingAssets;
import accounting.documents.BalanceSheet;
import accounting.equipment.AccountingEquipment;
import accounting.indicators.CalculatingNetIncome;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that computes the Return On Assets profitability ratio.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingROA extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        BalanceSheet bS = context.equipment().balanceSheet();
        double totalAssets = context.preliminaryResult(SummingAssets.getInstance());
                
        double netIncome = context.preliminaryResult(CalculatingNetIncome.getInstance());

        double roa = netIncome / totalAssets;

        logger.log(Level.INFO, "Just calculated the ROA profitability ratio.");

        return roa;
    }
    private static final Logger logger = Logger.getLogger(CalculatingROA.class.getName());

    private CalculatingROA() {
    }

    public static CalculatingROA getInstance() {
        return CalculatingROAHolder.INSTANCE;
    }

    private static class CalculatingROAHolder {

        private static final CalculatingROA INSTANCE = new CalculatingROA();
    }
}
