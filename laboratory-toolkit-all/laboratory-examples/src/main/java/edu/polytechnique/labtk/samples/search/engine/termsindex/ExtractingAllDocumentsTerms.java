/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.samples.search.engine.tokenization.Token;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link Analysis} that extracts the terms of the document from the tokens,
 * resulting in a {@link TermsExtraction}.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ExtractingAllDocumentsTerms extends Analysis<TermsExtraction, TermsIndexingEquipment> {

    private ExtractingAllDocumentsTerms() {
    }

    public static ExtractingAllDocumentsTerms getInstance() {
        return TermsExtractingHolder.INSTANCE;
    }

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

        //returning the result based on the backing map.
        return new SimpleMapBackedTermsExtraction(backingMap);
    }

    private static class TermsExtractingHolder {

        private static final ExtractingAllDocumentsTerms INSTANCE = new ExtractingAllDocumentsTerms();
    }
}
