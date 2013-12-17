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
 * {@link Analysis} that calculaltes the Operating Income of a company.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingOperatingIncome extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {
        
        double revenues = context.equipment().incomeStatement().operatingRevenues();
        double expenses = context.equipment().incomeStatement().operatingExpenses();

        logger.log(Level.INFO, "Just calculated the Operating Income.");
        
        return revenues - expenses;
    }
    private static final Logger logger = Logger.getLogger(CalculatingOperatingIncome.class.getName());

    private CalculatingOperatingIncome() {
    }

    public static CalculatingOperatingIncome getInstance() {
        return CalculatingOperatingIncomeHolder.INSTANCE;
    }

    private static class CalculatingOperatingIncomeHolder {

        private static final CalculatingOperatingIncome INSTANCE = new CalculatingOperatingIncome();
    }
}
