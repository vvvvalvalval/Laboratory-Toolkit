/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import static integer.algebra.ModuloUtils.moduloPower;

/**
 * A {@link ModuloNRing} for which n is a power of a prime number, resulting in
 * an easy to calculate group of invertibles.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class PrimePowerModuloNRing extends ModuloNRing {

    private final int p;
    private final int r;

    /**
     * Constructs a new {@link PrimePowerModuloNRing}.
     *
     * @param p a prime number
     * @param r the exponent of the prime number
     */
    public PrimePowerModuloNRing(int p, int r) {
        super((int) Math.pow(p, r));
        this.p = p;
        this.r = r;

        this.multiplicativeGroup = new InvertiblesGroup();
    }
    private final GeneratedGroup<Integer> multiplicativeGroup;

    /**
     * The multiplicative group of elements which are invertible for the
     * multiplication.
     *
     * @return
     */
    public GeneratedGroup<Integer> multiplicativeGroup() {
        return this.multiplicativeGroup;
    }

    private class InvertiblesGroup implements GeneratedGroup<Integer> {

        public InvertiblesGroup() {
            //finding the elements
            LinkedHashSet<Integer> elmts = new LinkedHashSet<>((int)Math.pow(p, r - 1) * (p - 1));
            for (int i = 0; i < n; i++) {
                if (i % p != 0) {
                    elmts.add(i);
                }
            }
            this.elements = Collections.unmodifiableSet(elmts);

            //finding the inverses
            this.inverses = new HashMap<>(this.elements.size());
            inverses.put(1, 1);
            for (int element : this.elements) {
                findInverseByExponentiation(element, element);
            }

            Set<Integer> gnrs = new LinkedHashSet<>(2);
            //finding generators
            if (p == 2) {
                //n is a power of 2
                if (r > 1) {
                    gnrs.add(n - 1);
                    gnrs.add(5 % n);
                } else {
                    //trivial group
                    gnrs.add(1);
                }
            } else {
                //p is an odd prime
                //we are looking for an integer which is a generator of (Z/pZ)X
                int b;
                Map<Integer, Integer> pOrders = new LinkedHashMap<>(p - 1);
                for (b = 2; b < p; b++) {
                    if (!pOrders.containsKey(b)) {
                        int exp = 1;
                        for (int pow = 1;; exp++) {
                            pow = (pow * b) % p;
                            if (pow == 1) {
                                pOrders.put(b, exp);
                                break;
                            }
//                            if (pOrders.containsKey(pow)) {
//                                pOrders.put(b, exp * pOrders.get(pow));
//                                break;
//                            }
                        }
                    }
                    if (pOrders.get(b) == p - 1) {
                        break;
                    }
                }
                //finding the n-order of b
                int ord = 1;
                for (int pow = b; pow != 1; ord++) {
                    pow = (pow * b) % n;
                }
                int exp = ord / (p - 1);
                b = moduloPower(b, exp, n);
                //now b has a n-order of p-1

                int generator = (b * (p + 1)) % n;
                gnrs.add(generator);
            }
            this.generators = Collections.unmodifiableSet(gnrs);
        }

        /**
         * Recursive method that computes all the powers of an integer to find
         * inverses.
         *
         * @param power a power of ini
         * @param ini an integer
         * @return the inverse of power
         */
        private Integer findInverseByExponentiation(int power, int ini) {
            if (inverses.containsKey(power)) {
                return inverses.get(power);
            } else {
                int inverse = this.plus(ini, this.findInverseByExponentiation(this.plus(power, ini), ini));
                inverses.put(power, inverse);
                inverses.put(inverse, power);
                return inverse;
            }
        }
        private final Set<Integer> generators;

        @Override
        public Collection<Integer> generators() {
            return generators;
        }
        private final Set<Integer> elements;

        @Override
        public Set<Integer> elements() {
            return this.elements;
        }

        @Override
        public Integer plus(Integer a, Integer b) {
            return (a * b) % n;
        }
        final Map<Integer, Integer> inverses;

        @Override
        public Integer opposite(Integer a) {
            return inverses.get(a);
        }

        @Override
        public Integer zero() {
            return 1;
        }
    }
}
