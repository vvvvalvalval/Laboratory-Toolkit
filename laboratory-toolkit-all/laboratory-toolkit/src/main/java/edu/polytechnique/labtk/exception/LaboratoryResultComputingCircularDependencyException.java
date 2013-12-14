/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.exception;

/**
 * Exception indicating that a circular dependency has been detected through the
 * computing of the result. That means the {@link ResultProducer#computeResult(com.adomaton.adomatonep.labfw.Laboratory)
 * } methods of the {@link ResultProducer}s implementations that just have been
 * working with the {@link Laboratory} that threw this Exception should be
 * revisited.
 *
 * @author @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class LaboratoryResultComputingCircularDependencyException extends RuntimeException {

    public LaboratoryResultComputingCircularDependencyException() {
    }

    public LaboratoryResultComputingCircularDependencyException(String message) {
        super(message);
    }

    public LaboratoryResultComputingCircularDependencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public LaboratoryResultComputingCircularDependencyException(Throwable cause) {
        super(cause);
    }

    public LaboratoryResultComputingCircularDependencyException(String message, Throwable cause,
                                                                boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
