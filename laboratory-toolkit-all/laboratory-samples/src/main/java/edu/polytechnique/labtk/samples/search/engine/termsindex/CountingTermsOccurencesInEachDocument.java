/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.samples.search.engine.equipment.Document;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@link Analysis} that counts the number of occurences of each term in each
 * document.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CountingTermsOccurencesInEachDocument extends Analysis<Map<Document, TermsOccurences>, TermsIndexingEquipment> {

    @Override
    protected Map<Document, TermsOccurences> computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {

        Set<Document> allDocuments = context.equipment().allDocuments();
        EachDocumentTermsExtraction termsExtractions = context.preliminaryResult(ExtractingEachDocumentTerms.getInstance());

        Map<Document, TermsOccurences> res = new HashMap<>(allDocuments.size());

        for (Document doc : allDocuments) {
            final TermsExtraction docTermsExtraction = termsExtractions.termsExtractedFromDocument(doc);

            //conting the total number of occurences
            int count = 0;
            for (String term : docTermsExtraction.allTerms()) {
                count += docTermsExtraction.tokensOfTerms(term).size();
            }
            final int totalNumber = count;

            //creating a TermsOccurences for that document
            TermsOccurences docTermsOccurences = new TermsOccurences() {
                @Override
                public int numberOfOccurences(String term) {
                    return docTermsExtraction.tokensOfTerms(term).size();
                }

                @Override
                public int totalNumberOfOccurences() {
                    return totalNumber;
                }
            };
            res.put(doc, docTermsOccurences);
        }

        //making immutable
        res = Collections.unmodifiableMap(res);

        return res;
    }

    public CountingTermsOccurencesInEachDocument() {
    }

    public static CountingTermsOccurencesInEachDocument getInstance() {
        return CountingTermsOccurencesInEachDocumentHolder.INSTANCE;
    }

    private static class CountingTermsOccurencesInEachDocumentHolder {

        private static final CountingTermsOccurencesInEachDocument INSTANCE = new CountingTermsOccurencesInEachDocument();
    }
}
