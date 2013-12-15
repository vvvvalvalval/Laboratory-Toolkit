/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.primes;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import integer.equipment.IntegerEquipment;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that computes the prime decomposition of the integer. The
 * return result is a {@link Map} which keys are the prime factors of the
 * integer and values are the exponents of said prime factors in the
 * decomposition.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingPrimeDecomposition extends Analysis<Map<Integer, Integer>, IntegerEquipment> {

    @Override
    protected Map<Integer, Integer> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {

        int n = context.equipment().n();
        //obtaining a list that contains all the possible prime dividors of the integer.
        List<Integer> potentialPrimeDividors = Eratosthene.getInstance().potentialPrimeDividors(n);

        Map<Integer, Integer> decomposition = new LinkedHashMap<>();

        /*
         * Successive divisions by prime dividors until we reach 1.
         */
        int quotient = n;
        decomposing:
        for (int prime : potentialPrimeDividors) {
            while (quotient % prime == 0) {//prime divides our integer
                quotient /= prime;
                //incrementing exponent for prime in decomposition
                if (!decomposition.containsKey(prime)) {
                    decomposition.put(prime, 0);
                }
                decomposition.put(prime, decomposition.get(prime) + 1);

            }
            //if quotient is 1, there is no point continuing
            if (quotient == 1) {
                break decomposing;
            }
        }
        if(quotient!=1){
            throw new IllegalStateException(""+quotient);
        }

        logger.log(Level.INFO, "Computed prime decomposition of number :{0}", n);

        return Collections.unmodifiableMap(decomposition);
    }

    private CalculatingPrimeDecomposition() {
    }

    public static CalculatingPrimeDecomposition getInstance() {
        return CalculatingPrimeDecompositionHolder.INSTANCE;
    }

    private static class CalculatingPrimeDecompositionHolder {

        private static final CalculatingPrimeDecomposition INSTANCE = new CalculatingPrimeDecomposition();
    }
    private static final Logger logger = Logger.getLogger(CalculatingPrimeDecomposition.class.getName());
}
