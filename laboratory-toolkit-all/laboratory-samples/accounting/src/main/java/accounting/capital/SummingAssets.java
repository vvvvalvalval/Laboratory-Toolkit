/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.capital;

import accounting.documents.BalanceSheet;
import accounting.equipment.AccountingEquipment;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that sums all the assets to the company's captital.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class SummingAssets extends SimpleAnalysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        BalanceSheet bs = context.equipment().balanceSheet();

        double totalAssets =
                bs.fixedAssets()
                + bs.inventories()
                + bs.accountReceivables()
                + bs.cash();

        logger.log(Level.INFO, "Just computed the total assets.");

        return totalAssets;
    }
    private static final Logger logger = Logger.getLogger(SummingAssets.class.getName());

    private SummingAssets() {
    }

    public static SummingAssets getInstance() {
        return SummingAssetsHolder.INSTANCE;
    }

    private static class SummingAssetsHolder {

        private static final SummingAssets INSTANCE = new SummingAssets();
    }
}
