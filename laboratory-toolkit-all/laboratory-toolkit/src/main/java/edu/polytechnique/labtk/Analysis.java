/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk;

import edu.polytechnique.labtk.protocols.AnalysisWithProtocol;
import edu.polytechnique.labtk.protocols.Protocol;

/**
 * An {@link Analysis} represents a method of computing a certain result, using
 * a certain equipmnent, in the context of one or several {@link Laboratory}S,
 * as part of an algorithm; this class is meant to be extended into custom
 * {@link Analysis}S that operate with application-specific equipment.
 * <p>
 * An {@link Analysis} computes a <em>result</em> using a certain
 * <em>equipment</em>. The equipment is meant to include input data for the
 * {@link Analysis} to work with, and possibly references to other
 * {@link Analysis}S from which to obtain preliminary results.
 * </p>
 * <p>The result of an {@link Analysis} is <em>always</em> obtained from -and
 * computed in- the context of a particular {@link Laboratory} instance. The
 * {@link Laboratory} collaborates with the {@link Analysis} in 3 different
 * manners :
 * <ul>
 * <li>it provides the {@link Analysis} the equipment with which to compute its
 * result, as well as a way of obtaining preliminary results from other
 * {@link Analysis}.</li>
 * <li>it stores the result of the {@link Analysis} once computed to make it
 * available with no need of repeating the computation.</li>
 * <li>it provides the result of the {@link Analysis} to the other
 * {@link Analysis} that require it in their computation.</li>
 * </ul>
 * </p>
 * <p>
 * Even if it is externally requested (with the
 * {@link Analysis#getResult(Laboratory)} method) several times, the computing
 * of the result by an {@link Analysis} usually occurs once in a
 * {@link Laboratory}'s lifecycle. The client code requesting the result does
 * not decide when the computation is actually performed; that decision is for
 * the contextual {@link Laboratory} to make. For example, even when the result
 * of the {@link Analysis} is requested for the first time, it is quite possible
 * that it has already been computed as a preliminary result for another
 * higher-level {@link Analysis}.
 * </p>
 * <p>
 * <strong>Important note :</strong> it is essential to understand that a
 * {@link Laboratory} stores results associated to {@link Analysis}
 * <em>instances</em>, <strong>not</strong> classes. Requesting the result of 2
 * different {@link Analysis} instances of the same class in the same
 * {@link Laboratory} will result in computing twice and storing twice the
 * result, which is in non-degenerate cases the opposite of the expected
 * behavior. This strongly encourages to adopt one of the 2 following
 * instanciation strategies for {@link Analysis}:
 * <ol>
 * <li>Implement the {@link Analysis} subclass using the Singleton pattern, to
 * enforce that only one instance of this {@link Analysis} subclass can be used
 * by any {@link Laboratory}. This is a simple and common solution</li>
 * <li>Make the {@link Analysis} a property of the equipment of the
 * {@link Laboratory}. This is a more advanced and flexible solution. It still
 * avoids duplication of {@link Analysis} instance, but it adds a level of
 * indirection as to what {@link Analysis} will actually be used for arriving to
 * a result. This approach is more flexible, because it decouples the meaning
 * and usage of the result from its actual computation. It is typically very
 * useful for exploring various algorithmic strategies and parameter across
 * several {@link Laboratory} instances.</li>
 * </ol>
 * On the other hand, it is permitted, and even encouraged, to use the same
 * {@link Analysis} instance with multiple {@link Laboratory} and equipment
 * instances.
 * </p>
 * <p>
 * The only precaution to take when implementing the {@link Analysis#computeResult(ResultComputingContext)
 * } method is not to request the result of this {@link Analysis} as a
 * preliminary result. Circular dependencies between {@link Analysis}S are not
 * allowed. If a circular dependency is detected at runtime, the contextual
 * {@link Laboratory} will throw a RuntimeException.
 * </p>
 * <p>
 * Better flexibility can be achieved using the {@link AnalysisWithProtocol}
 * class instead of subclassing {@link Analysis}. Indeed,
 * {@link AnalysisWithProtocol} relies on object composition (to a
 * {@link Protocol}) instead of class inheritance to declare how the result is
 * computed.
 * </p>
 *
 * @param <R> the type of the result : may be any object.
 * @param <E> the type of the equipment that is required for this
 * {@link Analysis} (may be any object, recommended to be declared as an
 * interface).
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 * @see Laboratory
 * @see AnalysisWithProtocol
 * @see ResultComputingContext
 */
public abstract class Analysis<R, E> {

    /**
     * Retrieves the result of this {@link Analysis} in the context of the
     * specified {@link Laboratory}.
     * <p>This is the <em>only</em> method that should be invoked from external
     * client code to retrieve the result of an {@link Analysis}.</p>
     * <p> It is strongly discouraged to use this method for obtaining a
     * preliminary result for computing another {@link Analysis}' result (i.e
     * calling it from body of its
     * {@link #computeResult(ResultComputingContext)} method) : use
     * {@link ResultComputingContext#preliminaryResult(Analysis)} instead.</p>
     *
     * @param lab a {@link Laboratory} to be used as a context for computing or
     * retrieving the result, which contains enough equipment for this
     * {@link Analysis}.
     * @return the result corresponding to this {@link Analysis}.
     * @throws NullPointerException if the parameterized {@link Laboratory} is
     * {@code null}.
     */
    public final R getResult(Laboratory<? extends E> lab) {
        if (lab == null) {
            throw new NullPointerException();
        }
        return lab.fetchResult(this);
    }

    /**
     * Retrieves the result of another {@link Analysis} in the specified
     * {@link Laboratory} context, while accounting for the fact that the
     * preliminary result will be used for computing the result of this
     * {@link Analysis}, which allows the contextual {@link Laboratory} to
     * monitor the dependencies among {@link Analysis}S.
     *
     * @param <R1> the type of the preliminary result to be fetched.
     * @param from the {@link Analysis} to obtain the result from, which
     * operates with less equipment than this {@link Analysis}.
     * @param lab the contextual {@link Laboratory}. Should be the same as used
     * in the calling {@link #computeResult} method.
     * @return the obtained preliminary result.
     */
    private <R1> R1 preliminaryResult(Analysis<R1, ? super E> from, Laboratory<? extends E> lab) {
        if (from == null) {
            throw new NullPointerException();
        }
        if (lab == null) {
            throw new NullPointerException();
        }
        return lab.fetchPreliminaryResult(from, this);
    }

    /**
     * Method called from a contextual {@link Laboratory} to obtain the computed
     * result.
     *
     * @param lab the {@link Laboratory} that needs the result.
     * @return the computed result.
     */
    final R giveResultToLab(Laboratory<? extends E> lab) {
        if (lab == null) {
            throw new NullPointerException();
        }
        return this.computeResult(new ResultComputingContextImpl<>(lab, this));
    }

    /**
     * Computes the result of this {@link Analysis} in the context of a
     * particular {@link Laboratory}, implicitely represented by the
     * parameterized {@link ResultComputingContext}.
     * <p>A client subclass of {@link Analysis} implements this method, and uses
     * the provided {@link ResultComputingContext} to access the required
     * equipment and obtain preliminary results from other {@link Analysis}.</p>
     *
     *
     * @param context the {@link ResultComputingContext} in which the
     * computation is to be performed.
     * @return the result of the computation.
     */
    protected abstract R computeResult(ResultComputingContext<? extends E> context);

    /**
     * Returns a {@link Protocol} which performs the same computation as this
     * {@link Analysis}.
     *
     * @return a {@link Protocol}.
     */
    public final Protocol<R, E> toProtocol() {
        return new ProtocolView();
    }

    /**
     * {@link Protocol} implementation that delegates the computing to this
     * {@link Analysis}'s {@link #computeResult(ResultComputingContext) }
     * method.
     */
    private final class ProtocolView implements Protocol<R, E> {

        @Override
        public R computeResult(ResultComputingContext<? extends E> context) {
            return Analysis.this.computeResult(context);
        }

        @Override
        public String toString() {
            return "ProtocolView{" + Analysis.this.toString() + '}';
        }
    }

    /**
     * Sets a provided result for this {@link Analysis} in the specified
     * {@link Laboratory}, overriding any previous computation and preventing a
     * new computation from being preformed.
     * <p>
     * <strong>Warning :</strong> this method should be used with caution. If a
     * higher-level {@link Analysis}'result has been computed using a
     * preliminary former result from this {@link Analysis}, its result will
     * likely still be based on the old (and obsolete) result of this
     * {@link Analysis}. This method should be used only for import before
     * results are requested from the specified {@link Laboratory}.
     * </p>
     *
     * @param result the result to be set.
     * @param lab the {@link Laboratory} in which to set the result.
     */
    public final void importResultInto(R result, Laboratory<? extends E> lab) {
        if (lab == null) {
            throw new NullPointerException();
        }
        lab.importResultFor(result, this);
    }

    /**
     * Simple {@link ResultComputingContext} implementation.
     *
     * @param <E>
     */
    private static final class ResultComputingContextImpl<E> implements ResultComputingContext<E> {

        final Laboratory<? extends E> laboratory;
        final Analysis<?, E> analysis;

        public ResultComputingContextImpl(Laboratory<? extends E> laboratory, Analysis<?, E> analysis) {
            if (laboratory == null) {
                throw new NullPointerException();
            }
            if (analysis == null) {
                throw new NullPointerException();
            }
            this.laboratory = laboratory;
            this.analysis = analysis;
        }

        @Override
        public E equipment() {
            return this.laboratory.equipment();
        }

        @Override
        public <R1> R1 preliminaryResult(Analysis<R1, ? super E> from) {
            return analysis.preliminaryResult(from, this.laboratory);
        }
    }
}
