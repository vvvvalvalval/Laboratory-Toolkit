/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.protocols;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.Laboratory;
import edu.polytechnique.labtk.ResultComputingContext;

/**
 * A {@link Protocol} represents a method of computing a result in a context
 * provided by a {@link Laboratory} in the form of a
 * {@link ResultComputingContext}.
 *
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 * @param <R> the type of the result.
 * @param <E> the type of the equipment with which the result is to be computed.
 * @see AnalysisWithProtocol
 * @see Analysis
 * @see ResultComputingContext
 * @see Laboratory
 */
public interface Protocol<R, E> {
    /**
     * Computes the result in the context of a
     * particular {@link Laboratory}, implicitely represented by the
     * parameterized {@link ResultComputingContext}.
     *
     * @param context the {@link ResultComputingContext} in which the
     * computation is to be performed.
     * @return the result of the computation.
     */
    public R computeResult(ResultComputingContext<? extends E> context);
}
