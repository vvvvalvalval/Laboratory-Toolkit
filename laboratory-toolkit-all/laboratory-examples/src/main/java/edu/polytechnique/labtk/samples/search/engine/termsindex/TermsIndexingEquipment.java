/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.samples.search.engine.equipment.DocumentsEquipment;
import edu.polytechnique.labtk.samples.search.engine.tokenization.Token;

/**
 * Equipment that allows terms indexing functionalities.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface TermsIndexingEquipment extends DocumentsEquipment {

    /**
     * The {@link Analysis} that is responsible for extracting the
     * {@link Token}s from the documents.
     *
     * @return the tokenization {@link Analysis}.
     */
    public Analysis<Iterable<Token>, DocumentsEquipment> tokenization();

    /**
     * The term frequency formula to be used.
     *
     * @return the preferred term frequency {@link Analysis}.
     */
    public Analysis<TermDocumentWeight, TermsIndexingEquipment> termFrequencyCalculation();
}
