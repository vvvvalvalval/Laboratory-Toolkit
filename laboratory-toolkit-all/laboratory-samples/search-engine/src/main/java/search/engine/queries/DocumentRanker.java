/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.queries;

import search.engine.equipment.Document;

/**
 * A {@link DocumentRanker} gives score to {@link Document}s in order to rank
 * them.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface DocumentRanker {

    public double score(Document document);
}
