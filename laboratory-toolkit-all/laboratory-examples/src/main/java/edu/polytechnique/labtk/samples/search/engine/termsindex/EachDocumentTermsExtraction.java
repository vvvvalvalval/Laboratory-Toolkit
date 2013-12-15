/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.samples.search.engine.equipment.Document;

/**
 * Result type for extracting the terms from each document separately.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface EachDocumentTermsExtraction {

    /**
     * Returns the terms extracted from the specified {@link Document}.
     *
     * @param doc a {@link Document}.
     * @return the extracted terms as a {@link TermsExtraction}.
     */
    public TermsExtraction termsExtractedFromDocument(Document doc);
}
