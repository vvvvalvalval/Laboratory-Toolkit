/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Ring of all the integers modulo n.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ModuloNRing implements Ring<Integer>, GeneratedGroup<Integer> {

    protected final int n;

    public ModuloNRing(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("n must be greater than 1");
        }
        this.n = n;

        LinkedHashSet<Integer> elmts = new LinkedHashSet<>(n);
        for (int i = 0; i < n; i++) {
            elmts.add(i);
        }
        this.elements = Collections.unmodifiableSet(elmts);
    }

    @Override
    public Integer times(Integer a, Integer b) {
        return (a * b) % n;
    }

    @Override
    public Integer one() {
        return 1;
    }
    private final Set<Integer> elements;

    @Override
    public Set<Integer> elements() {
        return this.elements;
    }

    @Override
    public Integer plus(Integer a, Integer b) {
        return (a + b) % n;
    }

    @Override
    public Integer opposite(Integer a) {
        return (n - a) % n;
    }

    @Override
    public Integer zero() {
        return 0;
    }

    @Override
    public Collection<Integer> generators() {
        return Collections.singletonList(1);
    }

    @Override
    public String toString() {
        return "Z/"+n+"Z";
    }
    
    
}
