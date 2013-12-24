/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.primes;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import integer.equipment.IntegerEquipment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that forms the set of all positive divisors of N.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class FindingAllDivisors extends SimpleAnalysis<NavigableSet<Integer>, IntegerEquipment> {

    @Override
    protected NavigableSet<Integer> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        //obtain prime factors decomposition
        Map<Integer, Integer> primeFactorsDecomposition = context.preliminaryResult(CalculatingPrimeDecomposition.getInstance());

        //compute all the divisors from the decomposition.
        NavigableSet<Integer> divisors = new TreeSet();
        divisors.addAll(this.recursiveProduct(primeFactorsDecomposition.entrySet().iterator()));

        logger.log(Level.INFO, "Formed the set of all the {0} positive divisors of {1}", new Object[]{divisors.size(), context.equipment().n()});
        
        return divisors;
    }

    /**
     * Recursive method for calculating all the products of powers of prime
     * factors of n.
     *
     * @param valuatedPrimes the primes remaining to be used for the product.
     * @return 
     */
    public Collection<Integer> recursiveProduct(Iterator<Map.Entry<Integer, Integer>> valuatedPrimes) {
        if (valuatedPrimes.hasNext()) {
            Map.Entry<Integer, Integer> valuatedPrime = valuatedPrimes.next();

            int prime = valuatedPrime.getKey();
            int valuation = valuatedPrime.getValue();

            Collection<Integer> rightFactors = this.recursiveProduct(valuatedPrimes);

            Collection<Integer> products = new ArrayList<Integer>((valuation + 1) * rightFactors.size());

            int leftFactor = 1;//the left factor, a power of the current prime
            for (int exponent = 0; exponent <= valuation; exponent++) {

                for (int rightFactor : rightFactors) {
                    int product = leftFactor * rightFactor;
                    products.add(product);
                }

                leftFactor *= prime;
            }

            return products;
        } else {
            return Collections.singletonList(1);
        }
    }

    private FindingAllDivisors() {
    }

    public static FindingAllDivisors getInstance() {
        return FindingAllDivisorsHolder.INSTANCE;
    }

    private static class FindingAllDivisorsHolder {

        private static final FindingAllDivisors INSTANCE = new FindingAllDivisors();
    }
    
    private static final Logger logger = Logger.getLogger(FindingAllDivisors.class.getName());
}
