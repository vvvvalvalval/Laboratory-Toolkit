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
 * {@link Analysis} that calculates the Net Income.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingNetIncome extends SimpleAnalysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        double ebt = context.preliminaryResult(ComputingEBT.getInstance());

        IncomeStatement incomeStatement = context.equipment().incomeStatement();
        double exceptionalIncome = incomeStatement.exceptionalIncome();
        double taxes = incomeStatement.incomeTax();

        double netIncome = ebt - taxes + exceptionalIncome;

        logger.log(Level.INFO, "Just computed the Net Income.");

        return netIncome;
    }
    private static final Logger logger = Logger.getLogger(CalculatingNetIncome.class.getName());

    private CalculatingNetIncome() {
    }

    public static CalculatingNetIncome getInstance() {
        return CalculatingNetIncomeHolder.INSTANCE;
    }

    private static class CalculatingNetIncomeHolder {

        private static final CalculatingNetIncome INSTANCE = new CalculatingNetIncome();
    }
}
