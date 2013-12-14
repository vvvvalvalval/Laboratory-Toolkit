/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.samples.search.engine.equipment.Document;
import edu.polytechnique.labtk.samples.search.engine.tokenization.Token;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link Analysis} that computes the <em>Inverse Document Frequency</em> (IDF)
 * measuring whether a term is rare across all documents.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingInvertedTermFrequency extends Analysis<TermWeight, TermsIndexingEquipment> {

    private CalculatingInvertedTermFrequency() {
    }

    public static CalculatingInvertedTermFrequency getInstance() {
        return InvertedTermFrequencyCalculatingHolder.INSTANCE;
    }

    @Override
    protected TermWeight computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
        //the total number of documents
        double allDocsCount = context.equipment().allDocuments().size();
        //terms extracted from all documents
        TermsExtraction allTermsExtraction = context.preliminaryResult(ExtractingAllDocumentsTerms.getInstance());
        Set<String> allTerms = allTermsExtraction.allTerms();

        Map<String, Double> backingMap = new HashMap<>();

        for (String term : allTerms) {
            Collection<Token> tokensOfTerm = allTermsExtraction.tokensOfTerms(term);
            //the set of documents in which the term appears.
            Set<Document> documentsOfTerm = new HashSet<>();
            for (Token token : tokensOfTerm) {
                documentsOfTerm.add(token.getDocument());
            }
            double termDocsCount = documentsOfTerm.size();

            //here is the formula
            double idf = Math.log(allDocsCount / termDocsCount);

            backingMap.put(term, idf);
        }

        return new SimpleTermWeight(backingMap, allDocsCount);
    }

    private static class InvertedTermFrequencyCalculatingHolder {

        private static final CalculatingInvertedTermFrequency INSTANCE = new CalculatingInvertedTermFrequency();
    }
}
