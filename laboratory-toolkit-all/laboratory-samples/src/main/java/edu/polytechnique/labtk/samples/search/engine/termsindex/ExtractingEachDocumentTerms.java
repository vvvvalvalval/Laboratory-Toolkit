/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.samples.search.engine.equipment.Document;
import edu.polytechnique.labtk.samples.search.engine.tokenization.Token;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@link Analysis} that extracts the terms of each document from the tokens.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ExtractingEachDocumentTerms extends Analysis<EachDocumentTermsExtraction, TermsIndexingEquipment> {

    @Override
    protected EachDocumentTermsExtraction computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
        //obtaining the tokens
        Iterable<Token> tokens = context.preliminaryResult(context.equipment().tokenization());

        //creating the backing map and adding an entry for each document.
        Map<Document, Map<String, Collection<Token>>> backingMap = new HashMap<>(context.equipment().allDocuments().size());
        for (Document doc : context.equipment().allDocuments()) {
            Map<String, Collection<Token>> emptyMap = new LinkedHashMap<>();
            backingMap.put(doc, emptyMap);
        }

        //scanning the tokens and filling the backing map
        for (Token token : tokens) {
            Document doc = token.getDocument();
            Map<String, Collection<Token>> docBackingMap = backingMap.get(doc);
            String term = token.getTerm();
            if (docBackingMap.containsKey(term)) {
                docBackingMap.get(term).add(token);
            } else {
                Collection<Token> tokensOfTerm = new ArrayList<>();
                tokensOfTerm.add(token);
                tokensOfTerm.add(token);
                docBackingMap.put(term, tokensOfTerm);
            }
        }

        //maps each document to its specific TermsExtraction.
        final Map<Document, TermsExtraction> termsExtractionOfDocument = new HashMap<>(backingMap.size());
        for (Document doc : backingMap.keySet()) {
            //creating a TermsExtraction from backing map for the document.
            termsExtractionOfDocument.put(doc, new SimpleMapBackedTermsExtraction(backingMap.get(doc)));
        }

        return new EachDocumentTermsExtraction() {
            @Override
            public TermsExtraction termsExtractedFromDocument(Document doc) {
                if (!termsExtractionOfDocument.containsKey(doc)) {
                    throw new IllegalArgumentException("Unknwon document : " + doc);
                }
                return termsExtractionOfDocument.get(doc);
            }
        };
    }

    private static class EachDocumentTermsExtractingHolder {

        private static final ExtractingEachDocumentTerms INSTANCE = new ExtractingEachDocumentTerms();
    }

    private ExtractingEachDocumentTerms() {
    }

    public static ExtractingEachDocumentTerms getInstance() {
        return EachDocumentTermsExtractingHolder.INSTANCE;
    }
}
