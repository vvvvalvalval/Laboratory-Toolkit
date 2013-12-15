/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

/**
 * Interface representing a bidirectional function.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface Isomorphism<E, F> {
    /**
     * Gets the image of x by this {@link Isomorphism}.
     * @param x
     * @return 
     */
    public F image(E x);
    /**
     * Gets the antecedent of y by this {@link Isomorphism}.
     * @param y
     * @return 
     */
    public E antecendent(F y);
}
