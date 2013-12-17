/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package accounting.profitability;

import accounting.equipment.AccountingEquipment;
import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that estimates the leverage effect of a company from its
 * Return On Equity and its Return On Assets.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class EstimatingLeverageEffect extends Analysis<Double, AccountingEquipment> {

    @Override
    protected Double computeResult(ResultComputingContext<? extends AccountingEquipment> context) {

        double roe = context.preliminaryResult(CalculatingROE.getInstance());
        double roa = context.preliminaryResult(CalculatingROA.getInstance());

        double leverageEffect = roe / roa;

        logger.log(Level.INFO, "Just estimated the leverage effect.");

        return leverageEffect;
    }
    private static final Logger logger = Logger.getLogger(EstimatingLeverageEffect.class.getName());

    private EstimatingLeverageEffect() {
    }

    public static EstimatingLeverageEffect getInstance() {
        return EstimatingLeverageEffectHolder.INSTANCE;
    }

    private static class EstimatingLeverageEffectHolder {

        private static final EstimatingLeverageEffect INSTANCE = new EstimatingLeverageEffect();
    }
}
