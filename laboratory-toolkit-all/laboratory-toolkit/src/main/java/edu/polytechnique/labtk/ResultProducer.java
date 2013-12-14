/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk;

/**
 * Simple interface representing an object that produces a result. This
 * interface is similar to {@link java.util.concurrent.Callable}, except that it doesn't throw an
 * {@link Exception}, and isn't especially intended for concurrency.
 * <p/>
 * <p>In the context of the <em>Laboratory Toolkit</em>, a
 * {@link ResultProducer} may typically be a simplified view of a particular
 * {@link Analysis} instance plugged into a particular {@link Laboratory}
 * instance, such as returns the {@link LabUtils#asResultProducerForLab(Analysis, Laboratory) } method.</p>
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 * @see LabUtils
 * @see java.util.concurrent.Callable
 */
public interface ResultProducer<R> {

    /**
     * Returns the result.
     *
     * @return the result.
     */
    public R getResult();
}
