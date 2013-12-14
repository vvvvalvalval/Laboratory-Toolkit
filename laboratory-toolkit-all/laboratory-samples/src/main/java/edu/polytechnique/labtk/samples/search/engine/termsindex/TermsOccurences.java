/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

/**
 * Result type that provides information about the number of occurences of terms
 * in a bag of terms, e.g a document or a collection of documents.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface TermsOccurences {

    /**
     * The number of occurences of the specified term.
     * @param term
     * @return 
     */
    public int numberOfOccurences(String term);

    /**
     * The total number of occurences of all terms.
     * @return 
     */
    public int totalNumberOfOccurences();
}
