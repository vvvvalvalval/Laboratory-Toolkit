/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import search.engine.equipment.Document;
import java.util.Map;

/**
 * Helper class exposing a catalog of {@link Analysis} which compute frequencies
 * of terms in a document in a variety of manners.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class TermFrequenciesCatalog {

    /**
     * Computes the frequencies of the terms as the number of occurences.
     */
    public static final Analysis<TermDocumentWeight, TermsIndexingEquipment> RAW_FREQUENCY = new Analysis<TermDocumentWeight, TermsIndexingEquipment>() {
        @Override
        protected TermDocumentWeight computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
            final Map<Document, TermsOccurences> termsOccurences = context.preliminaryResult(CountingTermsOccurencesInEachDocument.getInstance());

            return new TermDocumentWeight() {
                @Override
                public double weight(String term, Document document) {
                    return termsOccurences.get(document).numberOfOccurences(term);
                }
            };
        }
    };
    /**
     * Computes the frequencies as 0 or 1 depending on whether the term is
     * occurs in the document.
     */
    public static final Analysis<TermDocumentWeight, TermsIndexingEquipment> BOOLEAN_FREQUENCY = new Analysis<TermDocumentWeight, TermsIndexingEquipment>() {
        @Override
        protected TermDocumentWeight computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
            final Map<Document, TermsOccurences> termsOccurences = context.preliminaryResult(CountingTermsOccurencesInEachDocument.getInstance());

            return new TermDocumentWeight() {
                @Override
                public double weight(String term, Document document) {
                    double rawFrequency = termsOccurences.get(document).numberOfOccurences(term);
                    return (rawFrequency > 0 ? 1 : 0);
                }
            };
        }
    };
    /**
     * Computes the frequencies using a logarithmic scale.
     */
    public static final Analysis<TermDocumentWeight, TermsIndexingEquipment> LOGARITHMIC_FREQUENCY = new Analysis<TermDocumentWeight, TermsIndexingEquipment>() {
        @Override
        protected TermDocumentWeight computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
            final Map<Document, TermsOccurences> termsOccurences = context.preliminaryResult(CountingTermsOccurencesInEachDocument.getInstance());

            return new TermDocumentWeight() {
                @Override
                public double weight(String term, Document document) {
                    double rawFrequency = termsOccurences.get(document).numberOfOccurences(term);
                    return (rawFrequency > 0 ? 1.0 + Math.log(rawFrequency) : 0.0);
                }
            };
        }
    };
    /**
     * Computes the frequency of a term as the number of occurences of the term
     * divided by the total number of terms occurences in the document.
     */
    public static final Analysis<TermDocumentWeight, TermsIndexingEquipment> NORMALIZED_FREQUENCY = new Analysis<TermDocumentWeight, TermsIndexingEquipment>() {
        @Override
        protected TermDocumentWeight computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
            final Map<Document, TermsOccurences> termsOccurences = context.preliminaryResult(CountingTermsOccurencesInEachDocument.getInstance());

            return new TermDocumentWeight() {
                @Override
                public double weight(String term, Document document) {
                    double rawFrequency = termsOccurences.get(document).numberOfOccurences(term);
                    int totalNumberOfOccurences = termsOccurences.get(document).totalNumberOfOccurences();
                    return rawFrequency / totalNumberOfOccurences;
                }
            };
        }
    };

    private TermFrequenciesCatalog() {
        //prevents instanciation
    }
}
