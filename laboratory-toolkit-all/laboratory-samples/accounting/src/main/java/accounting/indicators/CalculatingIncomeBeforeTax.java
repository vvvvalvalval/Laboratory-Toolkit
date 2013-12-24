/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.indicators;

import accounting.equipment.AccountingEquipment;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingIncomeBeforeTax extends SimpleAnalysis<Double, AccountingEquipment>{

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {
        
        double operatingIncome = context.preliminaryResult(CalculatingOperatingIncome.getInstance());
        double financialIncome = context.preliminaryResult(CalculatingFinancialIncome.getInstance());
        
        logger.log(Level.INFO, "Just computed the Income Before Tax.");
        
        return operatingIncome + financialIncome;
    }
    
    private static final Logger logger = Logger.getLogger(CalculatingIncomeBeforeTax.class.getName());
    
    private CalculatingIncomeBeforeTax() {
    }
    
    public static CalculatingIncomeBeforeTax getInstance() {
        return CalculatingIncomeBeforeTaxHolder.INSTANCE;
    }
    
    private static class CalculatingIncomeBeforeTaxHolder {

        private static final CalculatingIncomeBeforeTax INSTANCE = new CalculatingIncomeBeforeTax();
    }
}
