/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * A {@link Ring} that is obtained as the cartesian product of other rings.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ProductRing<E> implements Ring<ProductElement<E>> {

    private final Collection<? extends Ring<E>> rings;
    private Set<ProductElement<E>> elements;

    public ProductRing(Collection<? extends Ring<E>> rings) {
        this.rings = rings;

        this.zero = new ProductElement(rings.size());
        for (Ring<E> ring : rings) {
            zero.put(ring, ring.zero());
        }

        this.one = new ProductElement(rings.size());
        for (Ring<E> ring : rings) {
            one.put(ring, ring.one());
        }
        this.elements = Collections.unmodifiableSet(new LinkedHashSet<>(this.recursiveProduct(rings.iterator())));

    }

    /**
     * The rings that are the factors of this product ring.
     *
     * @return the rings as a Collection of rings.
     */
    public Collection<? extends Ring<E>> rings() {
        return this.rings;
    }

    private Collection<ProductElement<E>> recursiveProduct(Iterator<? extends Ring<E>> ringsItr) {
        if (ringsItr.hasNext()) {
            Ring<E> ring = ringsItr.next();

            Collection<ProductElement<E>> previousElements = this.recursiveProduct(ringsItr);
            Collection<ProductElement<E>> res = new ArrayList<>(ring.elements().size() * previousElements.size());

            for (E element : ring.elements()) {
                for (ProductElement previousElement : previousElements) {
                    ProductElement<E> newElement = new ProductElement<>(previousElement.size() + 1);
                    newElement.putAll(previousElement);
                    newElement.put(ring, element);
                    res.add(newElement);
                }
            }

            return res;

        } else {
            return Collections.singletonList(new ProductElement<E>(rings.size()));
        }
    }

    @Override
    public ProductElement<E> times(ProductElement<E> a, ProductElement<E> b) {
        ProductElement<E> product = new ProductElement<>(rings.size());
        for (Ring<E> ring : a.keySet()) {
            product.put(ring, ring.times(a.get(ring), b.get(ring)));
        }
        return product;
    }
    private final ProductElement<E> one;

    @Override
    public ProductElement<E> one() {
        return one;
    }

    @Override
    public Set<ProductElement<E>> elements() {
        return elements;
    }

    @Override
    public ProductElement<E> plus(ProductElement<E> a, ProductElement<E> b) {
        ProductElement<E> sum = new ProductElement<>(rings.size());
        for (Ring<E> ring : a.keySet()) {
            sum.put(ring, ring.plus(a.get(ring), b.get(ring)));
        }
        return sum;
    }

    @Override
    public ProductElement<E> opposite(ProductElement<E> a) {
        ProductElement<E> difference;
        difference = new ProductElement<>(rings.size());
        for (Ring<E> ring : a.keySet()) {
            difference.put(ring, ring.opposite(a.get(ring)));
        }
        return difference;
    }
    private final ProductElement zero;

    @Override
    public ProductElement<E> zero() {
        return zero;
    }
}
