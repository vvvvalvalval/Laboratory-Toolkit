/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.indicators;

import accounting.documents.IncomeStatement;
import accounting.equipment.AccountingEquipment;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that calculates the Earning Before Interest and Taxes.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ComputingEBIT extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        double ebitda = context.preliminaryResult(ComputingEBITDA.getInstance());

        IncomeStatement incomeStatement = context.equipment().incomeStatement();
        double amortizations = incomeStatement.amortizations();
        double provisions = incomeStatement.provisions();

        double ebit = ebitda - amortizations - provisions;

        logger.log(Level.INFO, "Just calculated the EBIT.");

        return ebit;
    }
    private static final Logger logger = Logger.getLogger(ComputingEBIT.class.getName());

    private ComputingEBIT() {
    }

    public static ComputingEBIT getInstance() {
        return ComputingEBITHolder.INSTANCE;
    }

    private static class ComputingEBITHolder {

        private static final ComputingEBIT INSTANCE = new ComputingEBIT();
    }
}
