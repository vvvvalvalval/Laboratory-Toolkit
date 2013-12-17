/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.indicators;

import accounting.equipment.AccountingEquipment;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that computes the Financial Income of the company.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingFinancialIncome extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        double revenues = context.equipment().incomeStatement().financialRevenues();
        double expenses = context.equipment().incomeStatement().financialExpenses();

        logger.log(Level.INFO, "Just computed the Financial Income.");
        
        return revenues - expenses;
    }

    private static final Logger logger = Logger.getLogger(CalculatingFinancialIncome.class.getName());
    private CalculatingFinancialIncome() {
    }

    public static CalculatingFinancialIncome getInstance() {
        return CalculatingFinancialIncomeHolder.INSTANCE;
    }

    private static class CalculatingFinancialIncomeHolder {

        private static final CalculatingFinancialIncome INSTANCE = new CalculatingFinancialIncome();
    }
}
