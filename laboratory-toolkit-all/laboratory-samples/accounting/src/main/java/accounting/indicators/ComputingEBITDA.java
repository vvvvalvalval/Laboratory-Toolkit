/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.indicators;

import accounting.documents.IncomeStatement;
import accounting.equipment.AccountingEquipment;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that calculates the Earnings Before Interest, Taxex,
 * Depreciation and Amortization.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ComputingEBITDA extends SimpleAnalysis<Double, AccountingEquipment>{

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {
        IncomeStatement incomeStatement = context.equipment().incomeStatement();
        double opRev = incomeStatement.operatingRevenues();
        double opEx = incomeStatement.operatingExpenses();
        
        double ebitda = opRev - opEx;
        
        logger.log(Level.INFO, "Just calculated the EBITDA");
        
        return ebitda;
    }
    
    private static final Logger logger = Logger.getLogger(ComputingEBITDA.class.getName());
    
    private ComputingEBITDA() {
    }

    public static ComputingEBITDA getInstance() {
        return ComputingEBITDAHolder.INSTANCE;
    }

    private static class ComputingEBITDAHolder {

        private static final ComputingEBITDA INSTANCE = new ComputingEBITDA();
    }
}
