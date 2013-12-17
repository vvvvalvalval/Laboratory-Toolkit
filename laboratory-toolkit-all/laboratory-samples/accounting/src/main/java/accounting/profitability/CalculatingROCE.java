/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.profitability;

import accounting.capital.CalculatingNetDebt;
import accounting.equipment.AccountingEquipment;
import accounting.indicators.ComputingEBIT;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that calculates the Return On Capital Employed profitability
 * ratio.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingROCE extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        double ebit = context.preliminaryResult(ComputingEBIT.getInstance());
        double incomeTax = context.equipment().incomeStatement().incomeTax();
        double equity = context.equipment().balanceSheet().stockholderEquity();
        double netDebt = context.preliminaryResult(CalculatingNetDebt.getInstance());

        double roce = (ebit - incomeTax) / (equity + netDebt);

        logger.log(Level.INFO, "Just calculated the ROCE.");

        return roce;
    }
    private static final Logger logger = Logger.getLogger(CalculatingROCE.class.getName());

    private CalculatingROCE() {
    }

    public static CalculatingROCE getInstance() {
        return CalculatingROCEHolder.INSTANCE;
    }

    private static class CalculatingROCEHolder {

        private static final CalculatingROCE INSTANCE = new CalculatingROCE();
    }
}
