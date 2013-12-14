/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk;

import edu.polytechnique.labtk.exception.LaboratoryResultComputingCircularDependencyException;
import edu.polytechnique.labtk.exporter.ResultExporter;

import java.util.*;

/**
 * A {@link Laboratory} is a context object in which {@link Analysis}S compute
 * their result, as part of an algorithm. A {@link Laboratory} cooperates with
 * the {@link Analysis}S that work in its context through 3 sorts of interaction
 * :
 * <ul>
 * <li>It provides the {@link Analysis}S the <em>equipment</em> they need to
 * compute their result, and access to prelimary results from other
 * {@link Analysis}S, through a {@link ResultComputingContext} view.</li>
 * <li>It mediates requests between {@link Analysis} that have dependencies
 * (obtaining preliminary results).</li>
 * <li>It triggers the computation of the results if they have not already been
 * computed, and stores the results that have been computed, making them
 * available without repeating their computation.</li>
 * </ul>
 * <p>
 * A {@link Laboratory} holds an <em>equipment</em> object. The equipment object
 * is meant to contain the initial data of the algorithm(s) in which the
 * {@link Analysis} participate, as well as utility methods and references to
 * preliminary {@link Analysis}S for them to compute their result. There are no
 * particular constraints on the equipment object, but it is strongly
 * recommended that it be immutable.
 * </p>
 * <p>
 * An {@link Analysis} can only work in the context of a {@link Laboratory} that
 * has <em>enough</em> equipment for it. This means that the
 * {@link Laboratory}'s equipment type must be a <em>subtype</em> of the
 * {@link Analysis}'s equipment type. For that reason, it is recommended to
 * declare the {@link Analysis}S's types as interfaces, because using such a
 * design enables a {@link Laboratory} to work with {@link Analysis}S that
 * operate with different equipments, thanks to interface multiple inheritance.
 * </p>
 * <p>
 * Depending on its equimpent, an {@link Analysis}S may produce different
 * results. Therefore, using various equipments in different {@link Laboratory}S
 * instances is an excellent way of exploring various algorithmic parameters and
 * strategies. This encourages implementing the equipment as an Abstract Factory
 * of {@link Analysis}S.
 * </p>
 * <p>
 * To avoid repeating heavy computations, results of {@link Analysis}S may be
 * exported from one {@link Laboratory} to others using a
 * {@link ResultExporter}, which can be instatiated from the {@link LabUtils}
 * helper class.
 * </p>
 *
 * @see Analysis
 * @see ResultComputingContext
 * @see LabUtils
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 * @param <E> the type of the equipment of this {@link Laboratory}.
 */
public final class Laboratory<E> {

    /**
     * Contructs a new {@link Laboratory} instance with the specified equipment.
     *
     * @param equipment the equipment for the newly created {@link Laboratory}.
     */
    public Laboratory(E equipment) {
        this.equipment = equipment;
    }
    private final E equipment;

    /**
     * The equipment of this {@link Laboratory}.
     *
     * @return the equipment of this {@link Laboratory}.
     */
    public E equipment() {
        return this.equipment;
    }
    /**
     * The heterogenous container which stores the result of the
     * {@link Analysis} which have computed their results in the context of this
     * {@link Laboratory}.
     */
    private final Map<Analysis<?, ? super E>, Object> storedResults = new HashMap<>();
    /**
     * Keeps track of the {@link Analysis} which results are currently being
     * computed, and if known for what {@link Analysis} they are being computed
     * ({@code null} if unknown).
     */
    private final Map<Analysis<?, ? super E>, Analysis<?, ? super E>> resultsBeingComputed = new HashMap<>();

    /**
     * Whether the result for the specified {@link Analysis} has already been
     * computed and stored.
     *
     * @param analysis a {@link Analysis} compatible with this
     * {@link Laboratory}.
     * @return {@code true} if the result for the specified {@link Analysis} has
     * already been computed and stored.
     */
    final boolean isResultStoredFor(Analysis<?, ? super E> analysis) {
        if (analysis == null) {
            throw new NullPointerException();
        }
        return storedResults.containsKey(analysis);
    }

    /**
     * Retrieves the result for the specified {@link Analysis} from the stored
     * results.
     *
     * @param <R> the type of the result
     * @param analysis the {@link Analysis} is to be retrieved.
     * @return the result that is stored for thie {@link Analysis}.
     * @throws java.util.NoSuchElementException if no result is stored for the
     * {@link Analysis}.
     * @throws NullPointerException if the parameterized {@link Analysis} is
     * {@code null}.
     */
    private <R> R fetchStoredResultFor(Analysis<R, ? super E> analysis) {
        if (analysis == null) {
            throw new NullPointerException();
        }
        if (!isResultStoredFor(analysis)) {
            throw new NoSuchElementException();
        }
        return (R) this.storedResults.get(analysis);
    }

    /**
     * Store the specified result for the parameterized {@link Analysis}.
     *
     * @param analysis the {@link Analysis} which current result is to be
     * stored.
     * @param result the result to be stored (which may be {@code null}).
     */
    private void storeResultFor(Analysis<?, ? super E> analysis, Object result) {
        if (analysis == null) {
            throw new NullPointerException("Producer is null");
        }
        if (result == null) {
            //storing null is allowed.
        }
        this.storedResults.put(analysis, result);
    }

    /**
     * Retrieves the result for the specified {@link Analysis} if it is stored,
     * or has it computed and by the {@link Analysis} and stores it if not.
     *
     * @param <R> the type of the result
     * @param analysis the {@link Analysis} which result is to be fetched.
     * @return the result
     */
    final <R> R fetchResult(Analysis<R, ? super E> analysis) {
        if (analysis == null) {
            throw new NullPointerException();
        }
        if (!this.isResultStoredFor(analysis)) {
            //computation is needed
            if (resultsBeingComputed.containsKey(analysis)) {
                //means there is a circular dependency.
                this.throwCircularDependencyException(analysis);
            }
            this.resultsBeingComputed.put(analysis, null);
            this.storeResultFor(analysis, analysis.giveResultToLab(this));
            this.resultsBeingComputed.remove(analysis);
        }
        return this.fetchStoredResultFor(analysis);
    }

    /**
     * Fetches a result for a {@link Analysis} while accounting for the fact
     * that this result is being used for another {@link Analysis}, in order to
     * monitor the dependencies between {@link Analysis}s.
     *
     * @param <R> the type of the result
     * @param from the {@link Analysis} which result is to be used
     * @param to the {@link Analysis} which uses the result
     * @return the result
     */
    final <R> R fetchPreliminaryResult(Analysis<R, ? super E> from, Analysis<?, ? super E> to) {
        if (from == null) {
            throw new NullPointerException();
        }
        if (to == null) {
            throw new NullPointerException();
        }
        if (!this.isResultStoredFor(from)) {
            //computation is needed
            if (this.resultsBeingComputed.containsKey(from)) {
                this.resultsBeingComputed.put(from, to);
                //a circular dependency has been detected.
                this.throwCircularDependencyException(from);
            }
            this.resultsBeingComputed.put(from, to);
            this.storeResultFor(from, from.giveResultToLab(this));
            this.resultsBeingComputed.remove(from);
        }
        return this.fetchStoredResultFor(from);
    }

    /**
     * Tries to reconstruct the circular dependency from the
     * {@link #resultsBeingComputed} history the circular dependency, and throws
     * an {@link LaboratoryResultComputingCircularDependencyException} with
     * error message.
     *
     * @throws LaboratoryResultComputingCircularDependencyException when called.
     */
    private void throwCircularDependencyException(Analysis<?, ? super E> loopPoint) {
        Deque<Analysis<?, ?>> stack = new ArrayDeque<>();
        boolean loopCompleted = false;
        for (Analysis<?, ? super E> current = this.resultsBeingComputed.get(loopPoint);
                current != null;
                current = this.resultsBeingComputed.get(current)) {
            stack.addFirst(current);
            if (loopPoint.equals(current)) {
                loopCompleted = true;
                break;
            }
        }
        stack.addLast(loopPoint);
        StringBuilder sb = new StringBuilder();
        sb.append("Detected circular dependency while computing result for : ").append(loopPoint);
        sb.append("; chain = ");
        if (!loopCompleted) {
            sb.append("[Could not complete loop, not enough dependency information] ");
        }
        sb.append(stack.pollFirst());
        for (Analysis producer : stack) {
            sb.append("->")
                    .append(producer);
        }
        sb.append(".");
        //clear history
        this.resultsBeingComputed.clear();
        throw new LaboratoryResultComputingCircularDependencyException(sb.toString());
    }

    /**
     * Removes from storage any result that was stored for the specified
     * {@link Analysis}.
     *
     * @param analysis the {@link Analysis} which stored result is to be
     * removed.
     * @return {@code true} if a result was indeed stored for the parameterized
     * {@link Analysis}.
     * @throws NullPointerException if the parameterized {@link Analysis}.
     */
    public final boolean forgetResultOf(Analysis<?, ? super E> analysis) {
        if (analysis == null) {
            throw new NullPointerException();
        }
        boolean contains = this.isResultStoredFor(analysis);
        this.storedResults.remove(analysis);
        return contains;
    }

    /**
     * Wipes this {@link Laboratory} of all the results it has stored.
     */
    public final void clear() {
        this.storedResults.clear();
    }

    /**
     * Sets a provided result for the specified {@link Analysis}, preventing
     * computation for the {@link Analysis}.
     *
     * @param result the result to be set.
     * @param analysis the {@link Analysis}.
     */
    final void importResultFor(Object result, Analysis<?, ? super E> analysis) {
        if (analysis == null) {
            throw new NullPointerException();
        }
        this.storeResultFor(analysis, result);
    }
}
