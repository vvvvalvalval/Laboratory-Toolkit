/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.decimal;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import integer.equipment.IntegerEquipment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link Analysis} that finds the numeral representation of the integer in a
 * certain base.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class RepresentingInNumeralSystem extends Analysis<List<Integer>, IntegerEquipment> {

    private final int radix;

    /**
     * Constructs a new {@link RepresentingInNumeralSystem} instance which uses
     * the specified numeral system base.
     *
     * @param radix the base in which to write the integer.
     */
    public RepresentingInNumeralSystem(int radix) {
        if (radix <= 1) {
            throw new IllegalArgumentException("Radix must be at leats 2.");
        }
        this.radix = radix;
    }

    @Override
    protected List<Integer> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        //obtaining the integer we're working on
        int integer = context.equipment().n();

        //the result, a list of digits with most significant at the beginning of the list
        List<Integer> digits = new ArrayList<Integer>();

        //performing successive divisions to find the digits.
        int remainder = integer;
        while (remainder != 0) {
            int digit = remainder % radix;
            digits.add(digit);
            remainder /= radix;
        }

        Collections.reverse(digits);
        return Collections.unmodifiableList(digits);
    }
}
