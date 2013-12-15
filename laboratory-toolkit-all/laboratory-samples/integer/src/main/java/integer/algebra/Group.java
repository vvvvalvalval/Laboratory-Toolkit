/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import java.util.Set;

/**
 * Interface representing a finite Group, in the mathematical sense, such as the
 * set of relative integers, of the real number, of integers modulo n, or the
 * group of inversible elements in a Ring. See <a
 * href="http://en.wikipedia.org/wiki/Group_%28mathematics%29">Wikipedia</a> for
 * a more formal definition.
 *
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface Group<E> {

    /**
     * All the elements that are in this {@link Group}.
     *
     * @return the elements in this group.
     */
    public Set<E> elements();

    /**
     * The operation of this group. Despite the name, it may not be the usual
     * addition.
     *
     * @param a an element of this {@link Group}.
     * @param b an element of this {@link Group}.
     * @return the element of this {@link Group} that is obtained from the
     */
    public E plus(E a, E b);

    /**
     * The opposite of the specified element for the operation of this group,
     * i.e the element which added to a gives zero.
     *
     * @param a an element
     * @return the opposite of the parameterized element.
     */
    public E opposite(E a);

    /**
     * The identity element for the law of this {@link Group}, i.e the element
     * which added to any element a gives a.
     *
     * @return the identity element.
     */
    public E zero();
}