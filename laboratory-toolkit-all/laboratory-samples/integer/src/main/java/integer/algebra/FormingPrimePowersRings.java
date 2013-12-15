/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import integer.equipment.IntegerEquipment;
import integer.primes.CalculatingPrimeDecomposition;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that forms the modulo-n rings of the prime powers divisors
 * of n.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class FormingPrimePowersRings extends Analysis<Map<Integer, PrimePowerModuloNRing>, IntegerEquipment> {

    @Override
    protected Map<Integer, PrimePowerModuloNRing> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        Map<Integer, Integer> primeDecomposition = context.preliminaryResult(CalculatingPrimeDecomposition.getInstance());

        Map<Integer, PrimePowerModuloNRing> res = new LinkedHashMap<>(primeDecomposition.size());

        for (Map.Entry<Integer, Integer> entry : primeDecomposition.entrySet()) {
            int p = entry.getKey();
            int r = entry.getValue();
            res.put(p,new PrimePowerModuloNRing(p, r));
        }

        logger.log(Level.INFO, "Just formed the p^r rings.");

        return Collections.unmodifiableMap(res);
    }
    private static final Logger logger = Logger.getLogger(FormingPrimePowersRings.class.getName());

    private FormingPrimePowersRings() {
    }

    public static FormingPrimePowersRings getInstance() {
        return FormingPrimePowersRingsHolder.INSTANCE;
    }

    private static class FormingPrimePowersRingsHolder {

        private static final FormingPrimePowersRings INSTANCE = new FormingPrimePowersRings();
    }
}
