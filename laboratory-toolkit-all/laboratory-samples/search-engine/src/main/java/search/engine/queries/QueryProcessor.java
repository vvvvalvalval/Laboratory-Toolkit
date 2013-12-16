/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.queries;

import java.util.Collection;

/**
 * A {@link QueryProcessor} ranks documents according to their relevance to a
 * query.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface QueryProcessor {

    /**
     * Returns a {@link DocumentRanker} that is tailor-made for the
     * parameterized query.
     *
     * @param query a query as a sequence of words.
     * @return a {@link DocumentRanker} that ranks the documents for the
     * specified query.
     */
    public DocumentRanker rankForQuery(Collection<String> query);
}
