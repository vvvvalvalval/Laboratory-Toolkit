/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.queries;

import search.engine.equipment.Document;
import java.util.List;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface QueryRunner {

    /**
     * Searches the documents and ranks them against the specified query.
     *
     * @param query the query as a String
     * @return the ranked documents as a {@link List}, sorted from most to last
     * relevant.
     */
    public List<Document> runSearchQuery(String query);
}
