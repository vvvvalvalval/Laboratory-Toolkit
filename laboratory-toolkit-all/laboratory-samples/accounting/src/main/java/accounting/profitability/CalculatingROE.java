/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.profitability;

import accounting.equipment.AccountingEquipment;
import accounting.indicators.CalculatingNetIncome;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that calculates the Return On Equity profitability ratio.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingROE extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        double netIncome = context.preliminaryResult(CalculatingNetIncome.getInstance());
        double equity = context.equipment().balanceSheet().stockholderEquity();

        double roe = netIncome / equity;

        logger.log(Level.INFO, "Just calculted the ROE profitability ratio.");

        return roe;
    }
    private static final Logger logger = Logger.getLogger(CalculatingROE.class.getName());

    private CalculatingROE() {
    }

    public static CalculatingROE getInstance() {
        return CalculatingROEHolder.INSTANCE;
    }

    private static class CalculatingROEHolder {

        private static final CalculatingROE INSTANCE = new CalculatingROE();
    }
}
