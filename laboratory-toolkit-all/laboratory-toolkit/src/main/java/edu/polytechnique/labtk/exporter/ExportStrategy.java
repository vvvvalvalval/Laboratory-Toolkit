/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.exporter;


/**
 * Decides where is computed the result of an {@link Analysis} that is exported
 * from one {@link Laboratory} to another.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 * @see ResultExporter
 * @see LabUtils
 * @see Analysis
 * @see Laboratory
 */
public enum ExportStrategy {

    /**
     * If the result has not been computed in the source {@link Laboratory}, it
     * will be computed in the source {@link Laboratory}.
     */
    COMPUTE_IF_NECESSARY,
    /**
     * If the result has not been computed in the source {@link Laboratory}, it
     * will not be exported to the destination {@link Laboratory}S.
     */
    NO_EXPORT_IF_UNCOMPUTED;
}
