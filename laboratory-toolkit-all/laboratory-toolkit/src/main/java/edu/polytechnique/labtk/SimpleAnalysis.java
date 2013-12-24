/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk;

/**
 * Simple {@link Analysis} implementation with a built-in {@link Protocol} that
 * is implicitly implemented through the abstract
 * {@link #computeResult(ResultComputingContext)} method of this class.
 * <p>
 * In most cases, subclassing the {@link SimpleAnalysis} class is simpler than
 * instantiating an {@link Analysis} with a hand-made {@link Protocol} that
 * would have to be created and managed.
 * </p>
 * <p>
 * Extending this class is by far the most common way to declare an
 * {@link Analysis}. However, under some circumstances, more flexibility on the
 * {@link Protocol} used by the {@link Analysis} is desired. In such cases,
 * extend the {@link AnalysisWithProtocol} class instead.
 * </p>
 *
 * @see Analysis
 * @see AnalysisWithProtocol
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public abstract class SimpleAnalysis<R, E> extends Analysis<R, E> {

    private final Protocol<R, E> protocol;

    public SimpleAnalysis() {
        protocol = new LocalProtocol();
    }

    /**
     * Computes the result of this {@link Analysis} in the context of a
     * particular {@link Laboratory}, implicitely represented by the
     * parameterized {@link ResultComputingContext}.
     * <p>A client subclass of {@link SimpleAnalysis} implements this method,
     * and uses the provided {@link ResultComputingContext} to access the
     * necessary <em>equipment</em> and obtain preliminary results from other
     * {@link Analysis}.</p>
     *
     *
     * @param context the {@link ResultComputingContext} in which the
     * computation is to be performed.
     * @return the result of the computation.
     */
    protected abstract R computeResult(ResultComputingContext<? extends E> context);

    @Override
    public final Protocol<? extends R, ? super E> protocol() {
        return this.protocol;
    }

    /**
     * Custom {@link Protocol} implementation that delegates its computation to
     * the owner {@link SimpleAnalysis}.
     */
    private final class LocalProtocol implements Protocol<R, E> {

        @Override
        public R computeResult(ResultComputingContext<? extends E> context) {
            return SimpleAnalysis.this.computeResult(context);
        }

        @Override
        public String toString() {
            return "Local " + Protocol.class.getSimpleName() + " for " + SimpleAnalysis.this;
        }
    }
}
