/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.samples.search.engine.equipment.Document;

/**
 * A function that associates a weight to a term and a document : for example,
 * the frequency of a word in a document.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface TermDocumentWeight {

    /**
     * The weight of the specified term in the specified document.
     *
     * @param term a term
     * @param document a document
     * @return the weight of the term-document pair.
     */
    public double weight(String term, Document document);
}
