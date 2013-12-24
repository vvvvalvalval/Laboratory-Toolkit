/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import integer.equipment.IntegerEquipment;
import integer.primes.CalculatingPrimeDecomposition;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that constructs the isomorphism between the modulo-n ring
 * and its Chinese product-ring decomposition.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ConstructingChineseIsomorphism extends SimpleAnalysis<Isomorphism<Integer, ProductElement<Integer>>, IntegerEquipment> {

    @Override
    protected Isomorphism<Integer, ProductElement<Integer>> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        Map<Integer, Integer> primeDecomposition = context.preliminaryResult(CalculatingPrimeDecomposition.getInstance());
        Map<Integer, PrimePowerModuloNRing> powerRings = context.preliminaryResult(FormingPrimePowersRings.getInstance());
        ModuloNRing moduloNRing = context.preliminaryResult(FormingModuloNRing.getInstance());
        int n = context.equipment().n();

        final Map<Integer, ProductElement<Integer>> directMap = new LinkedHashMap<>(n);
        for (int i : moduloNRing.elements()) {
            directMap.put(i, new ProductElement<Integer>(powerRings.size()));
        }

        for (int primeFactor : primeDecomposition.keySet()) {
            int power = (int) Math.pow(primeFactor, primeDecomposition.get(primeFactor));
            PrimePowerModuloNRing ring = powerRings.get(primeFactor);
            for (Integer i : directMap.keySet()) {
                ProductElement<Integer> productElement = directMap.get(i);
                productElement.put(ring, i % power);
            }
        }

        final Map<ProductElement<Integer>, Integer> reverseMap = new LinkedHashMap<>(n);
        for (Map.Entry<Integer, ProductElement<Integer>> entry : directMap.entrySet()) {
            reverseMap.put(entry.getValue(), entry.getKey());
        }

        logger.log(Level.INFO, "Just computed the Chinese isomorphism.");

        return new Isomorphism<Integer, ProductElement<Integer>>() {
            @Override
            public ProductElement<Integer> image(Integer x) {
                return directMap.get(x);
            }

            @Override
            public Integer antecendent(ProductElement<Integer> y) {
                return reverseMap.get(y);
            }
        };
    }
    private static final Logger logger = Logger.getLogger(ConstructingChineseIsomorphism.class.getName());

    private ConstructingChineseIsomorphism() {
    }

    public static ConstructingChineseIsomorphism getInstance() {
        return ConstructingChineseIsomorphismHolder.INSTANCE;
    }

    private static class ConstructingChineseIsomorphismHolder {

        private static final ConstructingChineseIsomorphism INSTANCE = new ConstructingChineseIsomorphism();
    }
}
