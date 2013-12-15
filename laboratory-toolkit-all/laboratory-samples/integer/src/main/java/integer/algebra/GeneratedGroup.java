/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import java.util.Collection;

/**
 * A {@link Group} with which is provided a set of <em>generator</em>, i.e
 * elements which generate the whole group by being added to each other.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface GeneratedGroup<E> extends Group<E> {

    /**
     * Generators for this {@link Group}.
     *
     * @return a {@link Collection} of elements which generate this
     * {@link Group}.
     */
    public Collection<E> generators();
}
