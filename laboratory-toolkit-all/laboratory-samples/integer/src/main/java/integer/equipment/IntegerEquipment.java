/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.equipment;

/**
 * Equipment type holding a positive integer.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class IntegerEquipment {

    private final int n;

    /**
     * The positive integer this equipment is set for.
     *
     * @return the positive integer this equipment is set for.
     */
    public int n() {
        return n;
    }

    public IntegerEquipment(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Only positive integers are allowed.");
        }
        this.n = n;
    }
}
