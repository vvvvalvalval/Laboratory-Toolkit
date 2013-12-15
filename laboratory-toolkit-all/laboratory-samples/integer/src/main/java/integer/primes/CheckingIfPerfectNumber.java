/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.primes;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import integer.equipment.IntegerEquipment;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} for checking if N is a perfect number, i.e if it is the sum
 * of its proper divisors.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CheckingIfPerfectNumber extends Analysis<Boolean, IntegerEquipment>{

    @Override
    protected Boolean computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        //fetching the set of divisors
        NavigableSet<Integer> allDivisors = context.preliminaryResult(FindingAllDivisors.getInstance());
        SortedSet<Integer> properDivisors = allDivisors.headSet(context.equipment().n());
        
        //calculating the sum of all proper divisors
        int aliquotSum = 0;
        for(int properDivisor : properDivisors){
            aliquotSum+=properDivisor;
        }
        
        boolean isPerfect = aliquotSum==context.equipment().n();
        
        logger.log(Level.INFO, "Just calculated the sum of divisors ({0}) to check if {1} is a perfect number.", new Object[]{aliquotSum, context.equipment().n()});
        
        return isPerfect;
    }
    
    private CheckingIfPerfectNumber() {
    }

    public static CheckingIfPerfectNumber getInstance() {
        return CheckingIfPerfectNumberHolder.INSTANCE;
    }

    private static class CheckingIfPerfectNumberHolder {

        private static final CheckingIfPerfectNumber INSTANCE = new CheckingIfPerfectNumber();
    }
    
    private static final Logger logger = Logger.getLogger(CheckingIfPerfectNumber.class.getName());
}
