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
 * {@link Analysis} that computes the Earning Before Taxes.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ComputingEBT extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        double ebit = context.preliminaryResult(ComputingEBIT.getInstance());

        IncomeStatement incomeStatement = context.equipment().incomeStatement();
        double financialIncome = incomeStatement.financialIncome();
        double financialExpenses = incomeStatement.financialExpenses();

        double ebt = ebit + financialIncome - financialExpenses;

        logger.log(Level.INFO, "Just calculated the EBT.");

        return ebt;
    }
    private static final Logger logger = Logger.getLogger(ComputingEBT.class.getName());

    private ComputingEBT() {
    }

    public static ComputingEBT getInstance() {
        return ComputingEBTHolder.INSTANCE;
    }

    private static class ComputingEBTHolder {

        private static final ComputingEBT INSTANCE = new ComputingEBT();
    }
}
