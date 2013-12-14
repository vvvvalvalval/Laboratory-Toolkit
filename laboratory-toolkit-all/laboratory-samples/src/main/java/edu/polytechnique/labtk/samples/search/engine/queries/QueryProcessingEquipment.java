/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.queries;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.samples.search.engine.termsindex.TermsIndexingEquipment;

/**
 * Equipment for running queries that search and rank the documents.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface QueryProcessingEquipment extends TermsIndexingEquipment {

    /**
     * Creating the {@link QueryProcessor} that is used for ranking the
     * documents against a query.
     *
     * @return the {@link QueryProcessor}-creating {@link Analysis} of this
     * {@link QueryProcessingEquipment}.
     */
    public Analysis<QueryProcessor, TermsIndexingEquipment> creatingQueryProcessor();
}
