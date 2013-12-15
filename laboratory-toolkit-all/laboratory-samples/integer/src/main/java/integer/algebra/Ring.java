/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

/**
 * Interface representing a {@link Ring} in the mathematical sense, which is a
 * Group extended with a mulpiplication operation which is distributive over
 * the addition of the Group.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface Ring<E> extends Group<E> {

    /**
     * The multiplication law for this {@link Ring}.
     *
     * @param a an element
     * @param b an element
     * @return the product of a and b in this {@link Ring}.
     */
    public E times(E a, E b);

    /**
     * The unity element of this {@link Ring}, i.e the element which multiplied
     * by any element a yields a.
     *
     * @return
     */
    public E one();
}
