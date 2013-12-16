/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import search.engine.equipment.DocumentsEquipment;
import search.engine.tokenization.Token;

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
