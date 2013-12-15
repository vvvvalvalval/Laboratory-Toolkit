/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import integer.equipment.IntegerEquipment;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that forms the product ring that is isomorphic to the
 * modulo-n ring.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class FormingIsomorphicProductRing extends Analysis<ProductRing<Integer>, IntegerEquipment> {

    @Override
    protected ProductRing<Integer> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        Collection<PrimePowerModuloNRing> powerRings = context.preliminaryResult(FormingPrimePowersRings.getInstance()).values();
        ProductRing<Integer> res = new ProductRing<>(powerRings);

        logger.log(Level.INFO, "Just formed the isomorphic product ring.");

        return res;
    }
    private static final Logger logger = Logger.getLogger(FormingIsomorphicProductRing.class.getName());

    private FormingIsomorphicProductRing() {
    }

    public static FormingIsomorphicProductRing getInstance() {
        return FormingIsomorphicProductRingHolder.INSTANCE;
    }

    private static class FormingIsomorphicProductRingHolder {

        private static final FormingIsomorphicProductRing INSTANCE = new FormingIsomorphicProductRing();
    }
}
