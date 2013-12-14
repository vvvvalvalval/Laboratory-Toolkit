/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.protocols;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.Laboratory;
import edu.polytechnique.labtk.ResultComputingContext;

/**
 * An {@link AnalysisWithProtocol} is an {@link Analysis} that delegates the
 * computing of its result to its {@link Protocol} object.
 * <p>
 * Because {@link Protocol} is an interface, this permits a more flexible
 * design, as {@link Protocol}s don't have the inheritance constraints of
 * {@link Analysis}S. For example, {@link Protocol}s can be combined using the
 * Decorator and Strategy GoF's design patterns.
 * </p>
 * <p>
 * Note that the {@link Analysis} class does <strong>not</strong> implement the
 * {@link Protocol} interface, although it has a method with the same signature.
 * There are good reasons for that. The {@link Analysis#computeResult(ResultComputingContext)
 * } method is not meant to be called from anywhere but the contextual
 * {@link Laboratory}.
 * </p>
 * @see Protocol
 * @see Analysis
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class AnalysisWithProtocol<R, E> extends Analysis<R, E> {

    private final Protocol<? extends R, ? super E> protocol;

    /**
     * Creates a new {@link AnalysisWithProtocol} that uses the specified
     * {@link Protocol}.
     *
     * @param protocol the {@link Protocol} this {@link Analysis} will use to
     * compute its algorithm.
     */
    public AnalysisWithProtocol(Protocol<? extends R, ? super E> protocol) {
        if (protocol == null) {
            throw new NullPointerException();
        }
        this.protocol = protocol;
    }

    @Override
    protected final R computeResult(ResultComputingContext<? extends E> context) {
        return this.protocol.computeResult(context);
    }
}
