/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.primes;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import integer.equipment.IntegerEquipment;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that extracts the prime factors of n.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class FormingSetOfPrimeFactors extends SimpleAnalysis<Set<Integer>, IntegerEquipment> {

    @Override
    protected Set<Integer> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        Set<Integer> primeFactors = context.preliminaryResult(CalculatingPrimeDecomposition.getInstance()).keySet();
        int n = context.equipment().n();

        logger.log(Level.INFO, "Just formed the set of prime factors of {0}, containing {1} different factors", new Object[]{n, primeFactors.size()});

        return primeFactors;
    }
    private static final Logger logger = Logger.getLogger(FormingSetOfPrimeFactors.class.getName());

    private FormingSetOfPrimeFactors() {
    }

    public static FormingSetOfPrimeFactors getInstance() {
        return FormingSetOfPrimeFactorsHolder.INSTANCE;
    }

    private static class FormingSetOfPrimeFactorsHolder {

        private static final FormingSetOfPrimeFactors INSTANCE = new FormingSetOfPrimeFactors();
    }
}
