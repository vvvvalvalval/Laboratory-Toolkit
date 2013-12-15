/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An element of a {@link ProductRing}.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ProductElement<E> extends LinkedHashMap<Ring<E>, E> {

    @Override
    public String toString() {
        return values().toString();
    }

    public ProductElement(int initialCapacity) {
        super(initialCapacity);
    }

    public ProductElement() {
    }

    public ProductElement(Map<? extends Ring<E>, ? extends E> m) {
        super(m);
    }
    
    
}
