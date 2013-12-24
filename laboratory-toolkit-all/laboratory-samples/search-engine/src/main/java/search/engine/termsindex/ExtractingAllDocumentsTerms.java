/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import search.engine.tokenization.Token;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that extracts the terms of the document from the tokens,
 * resulting in a {@link TermsExtraction}.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ExtractingAllDocumentsTerms extends SimpleAnalysis<TermsExtraction, TermsIndexingEquipment> {

    @Override
    protected TermsExtraction computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {

        //the sequence of tokens extracted from the documents.
        Iterable<Token> tokens = context.preliminaryResult(context.equipment().tokenization());

        //the backing map
        Map<String, Collection<Token>> backingMap = new LinkedHashMap<>();

        //filling the map
        for (Token token : tokens) {
            String term = token.getTerm();
            if (backingMap.containsKey(term)) {
                backingMap.get(term).add(token);
            } else {
                Collection<Token> tokensOfTerm = new ArrayList<>();
                tokensOfTerm.add(token);
                tokensOfTerm.add(token);
                backingMap.put(term, tokensOfTerm);
            }
        }

        logger.log(Level.INFO, "Just extracted the terms from all documents.");
        
        //returning the result based on the backing map.
        return new SimpleMapBackedTermsExtraction(backingMap);
    }

    private ExtractingAllDocumentsTerms() {
    }

    public static ExtractingAllDocumentsTerms getInstance() {
        return TermsExtractingHolder.INSTANCE;
    }

    private static final Logger logger = Logger.getLogger(ExtractingAllDocumentsTerms.class.getName());
    
    private static class TermsExtractingHolder {

        private static final ExtractingAllDocumentsTerms INSTANCE = new ExtractingAllDocumentsTerms();
    }
}
