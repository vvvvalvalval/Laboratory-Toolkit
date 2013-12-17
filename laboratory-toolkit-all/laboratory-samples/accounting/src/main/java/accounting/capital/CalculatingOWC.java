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
 * {@link Analysis} that calculates the Overall Working Capital.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingOWC extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {
        
        BalanceSheet bS = context.equipment().balanceSheet();
        
        double owc = bS.inventories() + bS.accountReceivables() - bS.accountPayables();
        
        logger.log(Level.INFO, "Just calculated the Overall Working Capital.");

        return owc;
    }
    private static final Logger logger = Logger.getLogger(CalculatingOWC.class.getName());

    private CalculatingOWC() {
    }

    public static CalculatingOWC getInstance() {
        return CalculatingOWCHolder.INSTANCE;
    }

    private static class CalculatingOWCHolder {

        private static final CalculatingOWC INSTANCE = new CalculatingOWC();
    }
}
