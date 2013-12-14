/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk;

import edu.polytechnique.labtk.exception.LaboratoryResultComputingCircularDependencyException;
import edu.polytechnique.labtk.protocols.AnalysisWithProtocol;
import edu.polytechnique.labtk.protocols.Protocol;

/**
 * Context for performing the computing of the result of an {@link Analysis} in
 * the context of a particular {@link Laboratory}.
 * <p>
 * In addition to providing the {@link Analysis} the {@link Laboratory} in which
 * its result is being computed, the {@link ResultComputingContext} most
 * importantly provides means of obtaining preliminary results from other
 * {@link Analysis} in the same {@link Laboratory}. This way of obtaining
 * preliminary results should almost <em>always</em> be preferred over direct
 * invocation of the {@link Analysis#getResult} method, because it enables the
 * contextual {@link Laboratory} to monitor the dependencies between
 * {@link Analysis}.
 * </p>
 * <p>
 * Note that there is <em>no reason</em> whatsoever for a client to implement
 * this interface. The <em>only</em> use of this interface by the client should
 * be when implementing the {@link Analysis#computeResult(ResultComputingContext)}
 * } method of an {@link Analysis} subclass, or the
 * {@link Protocol#computeResult(ResultComputingContext)} method of the protocol
 * of an {@link AnalysisWithProtocol}.
 * </p>
 *
 * @param <E> the type of the eqpuiment of the {@link Laboratory} this
 * {@link ResultComputingContext} is a view of.
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 * @see Analysis
 * @see AnalysisWithProtocol
 * @see Protocol
 * @see Laboratory
 */
public interface ResultComputingContext<E> {

    /**
     * Returns the equipment of the {@link Laboratory} in which the result will
     * be computed. This equipment may be used in order to retrieve input data,
     * or references to other {@link Analysis} which results are involved in the
     * current computation.
     *
     * @return the equipment of the {@link Laboratory} in which the result will
     * be computed.
     */
    public E equipment();

    /**
     * Fetches the result of another {@link Analysis} in the context of the same
     * {@link Laboratory}, to be used as a preliminary result in the current
     * computation.
     * <p>As compared to obtaining the preliminary result through direct
     * invocation of the preliminary {@link Analysis}'s
     * {@link Analysis#getResult(Laboratory)} method, using this method has 2
     * advantages :
     * <ul>
     * <li>you do not have to ensure the {@link Laboratory} from which the
     * preliminary result is fetched is the same as the one in which the current
     * computation is being performed, which is a security;</li>
     * <li>this enables the contextual {@link Laboratory} to account for the
     * dependency between the preliminary {@link Analysis} and the one which is
     * computing the result at runtime, thus being able to fail in a safer and
     * more informed manner if a cyclic dependency is detected.</li>
     * </ul>
     * </p>
     *
     * @param <R1> the type of the preliminary result.
     * @param from the {@link Analysis} from which to obtain the preliminary
     * result.
     * @return the preliminary result fetched from this
     * {@link ResultComputingContext}'s {@link Laboratory}.
     * @throws LaboratoryResultComputingCircularDependencyException if the
     * contextual {@link Laboratory} detects that the computing of the
     * preliminary results cycles back to the computing of the current result.
     */
    public <R1> R1 preliminaryResult(Analysis<R1, ? super E> from);
}
