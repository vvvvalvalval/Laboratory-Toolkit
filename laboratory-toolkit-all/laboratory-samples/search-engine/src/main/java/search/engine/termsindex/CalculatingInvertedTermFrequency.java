/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import search.engine.equipment.Document;
import search.engine.tokenization.Token;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that computes the <em>Inverse Document Frequency</em> (IDF)
 * measuring whether a term is rare across all documents.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingInvertedTermFrequency extends SimpleAnalysis<TermWeight, TermsIndexingEquipment> {


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

        logger.log(Level.INFO, "Just calculated the Inverted Terms Frequencies.");
        
        return new SimpleTermWeight(backingMap, allDocsCount);
    }

    private static final Logger logger = Logger.getLogger(CalculatingInvertedTermFrequency.class.getName());
    
    private CalculatingInvertedTermFrequency() {
    }

    public static CalculatingInvertedTermFrequency getInstance() {
        return InvertedTermFrequencyCalculatingHolder.INSTANCE;
    }
    
    private static class InvertedTermFrequencyCalculatingHolder {

        private static final CalculatingInvertedTermFrequency INSTANCE = new CalculatingInvertedTermFrequency();
    }
}
