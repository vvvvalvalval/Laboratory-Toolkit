/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk;

/**
 * {@link Analysis} implementation which {@link Protocol} is externally provided
 * at creation time.
 * <p>
 * This {@link Analysis} subclass is useful when the {@link Protocol} is
 * obtained externally, for example when it is a combination of other
 * {@link Protocol}s through composition and inheritance.
 * </p>
 * <p>
 * When there is no reason to define the {@link Protocol} externally - which is
 * most cases - the {@link SimpleAnalysis} class provides a simpler alternative
 * to {@link AnalysisWithProtocol}s.
 * </p>
 *
 * @see SimpleAnalysis
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class AnalysisWithProtocol<R, E> extends Analysis<R, E> {

    private final Protocol<? extends R, ? super E> protocol;

    /**
     * Constructs a new {@link AnalysisWithProtocol} from the speified
     * {@link Protocol}.
     *
     * @param protocol the {@link Protocol} with which this {@link Analysis} is
     * to perform its computations.
     * @throws NullPointerException if the parameterized {@link Protocol} is
     * {@code null}.
     */
    public AnalysisWithProtocol(Protocol<? extends R, ? super E> protocol) {
        if (protocol == null) {
            throw new NullPointerException("The " + Protocol.class.getSimpleName() + " may not be null.");
        }
        this.protocol = protocol;
    }

    @Override
    public final Protocol<? extends R, ? super E> protocol() {
        return this.protocol;
    }
}
