/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

/**
 * Represents a weighting funtion on terms.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface TermWeight {

    /**
     * Returns the weight of the specified term.
     *
     * @param term a term.
     * @return the weight of the parameterized term.
     */
    public double weight(String term);
}
