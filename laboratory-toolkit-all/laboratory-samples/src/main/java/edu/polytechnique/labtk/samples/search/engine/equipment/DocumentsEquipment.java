/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.equipment;

import java.util.Set;

/**
 * Basic equipment consisting of a collection of {@link Document}s, representing
 * all the documents indexed by the search engine.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface DocumentsEquipment {

    /**
     * All the documents indexed by the search engine.
     *
     * @return the documents as a {@link Set}.
     */
    public Set<Document> allDocuments();
}
