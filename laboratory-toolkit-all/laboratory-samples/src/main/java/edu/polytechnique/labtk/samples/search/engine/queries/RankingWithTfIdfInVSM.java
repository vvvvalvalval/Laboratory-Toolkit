/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.queries;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.samples.search.engine.equipment.Document;
import edu.polytechnique.labtk.samples.search.engine.termsindex.CalculatingTfIdf;
import edu.polytechnique.labtk.samples.search.engine.termsindex.CalculatingTfIdfDocumentNorms;
import edu.polytechnique.labtk.samples.search.engine.termsindex.EachDocumentTermsExtraction;
import edu.polytechnique.labtk.samples.search.engine.termsindex.ExtractingEachDocumentTerms;
import edu.polytechnique.labtk.samples.search.engine.termsindex.TermDocumentWeight;
import edu.polytechnique.labtk.samples.search.engine.termsindex.TermsIndexingEquipment;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} for creating a query processor which ranks the documents in
 * a Vector Space Model (VSM) approach using the tf-idf weighting of the terms.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class RankingWithTfIdfInVSM extends Analysis<QueryProcessor, TermsIndexingEquipment> {

    private RankingWithTfIdfInVSM() {
    }

    public static RankingWithTfIdfInVSM getInstance() {
        return RankingWithTfIdfInVSMHolder.INSTANCE;
    }

    @Override
    protected QueryProcessor computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
        //for obtaining set of terms in each document.
        final EachDocumentTermsExtraction documentsTerms = context.preliminaryResult(ExtractingEachDocumentTerms.getInstance());
        final TermDocumentWeight tfIdf = context.preliminaryResult(CalculatingTfIdf.getInstance());
        final Map<Document, Double> docsNorms = context.preliminaryResult(CalculatingTfIdfDocumentNorms.getInstance());
        
        QueryProcessor res = new QueryProcessor() {
            @Override
            public DocumentRanker rankForQuery(final Collection<String> query) {
                return new DocumentRanker() {
                    @Override
                    public double score(Document document) {
                        /*
                         * VSM similarity is calculated here
                         */

                        /*
                         * Computing the TF-IDF of the query, i.e the number of occurences of each term
                         */
                        Map<String, Double> queryTfIdf = new HashMap<>();
                        for (String term : query) {
                            if (queryTfIdf.containsKey(term)) {
                                //incrementing
                                queryTfIdf.put(term, queryTfIdf.get(term) + 1);
                            } else {
                                queryTfIdf.put(term, 1.0);
                            }
                        }

                        //Computing the norm of the query as a tf-idf vector.
                        double queryNorm = 0.0;
                        for (Map.Entry<String, Double> entry : queryTfIdf.entrySet()) {
                            queryNorm += Math.pow(entry.getValue(), 2);
                        }
                        queryNorm = Math.sqrt(queryNorm);

                        double docNorm = docsNorms.get(document);

                        //obtaining the set of terms in the document
                        Set<String> allDocTerms = documentsTerms.termsExtractedFromDocument(document).allTerms();

                        //computing the dot product of the query and the document
                        double dotProduct = 0.0;
                        for (String term : queryTfIdf.keySet()) {
                            if (allDocTerms.contains(term)) {
                                dotProduct += queryTfIdf.get(term) * tfIdf.weight(term, document);
                            }
                        }

                        //normalizing
                        double res = dotProduct / (queryNorm * docNorm);

                        return res;
                    }
                };
            }
        };
        
        logger.log(Level.INFO, "Created query processor that uses the VSM approach with TF-IDF");
        
        return res;
    }

    private static class RankingWithTfIdfInVSMHolder {

        private static final RankingWithTfIdfInVSM INSTANCE = new RankingWithTfIdfInVSM();
    }
    private static final Logger logger = Logger.getLogger(RankingWithTfIdfInVSM.class.getName());
}
