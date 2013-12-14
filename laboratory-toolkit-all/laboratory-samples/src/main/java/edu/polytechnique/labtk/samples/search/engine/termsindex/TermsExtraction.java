/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.samples.search.engine.tokenization.Token;
import java.util.Collection;
import java.util.Set;

/**
 * Result type for finding all the {@link Token}s which represent a specified
 * term, thus allowing to locate where a term appears in the documents.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface TermsExtraction {

    /**
     * Returns a {@link Collection} containing all the extracted {@link Token}s
     * which term is the parameterized term.
     *
     * @param term a term
     * @return all the {@link Token}s with the specified term (may be an empty
     * Collection).
     */
    public Collection<Token> tokensOfTerms(String term);

    /**
     * All the terms that were extracted.
     *
     * @return the terms as a {@link Set}.
     */
    public Set<String> allTerms();
}
