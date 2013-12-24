/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.queries;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import search.engine.equipment.Document;
import search.engine.termsindex.CalculatingInvertedTermFrequency;
import search.engine.termsindex.ComputingAverageDocumentLength;
import search.engine.termsindex.CountingTermsOccurencesInEachDocument;
import search.engine.termsindex.EachDocumentTermsExtraction;
import search.engine.termsindex.ExtractingEachDocumentTerms;
import search.engine.termsindex.TermDocumentWeight;
import search.engine.termsindex.TermWeight;
import search.engine.termsindex.TermsIndexingEquipment;
import search.engine.termsindex.TermsOccurences;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} for creating query processors which ranking method is based
 * on the BM25 scoring algorithm.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class RankingWithBM25 extends SimpleAnalysis<QueryProcessor, TermsIndexingEquipment> {

    /*
     * Parameters for the algorithm
     */
    /**
     * Controls term frequency saturation.
     */
    private final double k1;
    /**
     * Controls length normalization.
     */
    private final double b;

    /**
     * Constructs a new {@link RankingWithBM25} with the specified parameters.
     *
     * @param k1 controls term frequency saturation
     * @param b controls document length normalization
     */
    public RankingWithBM25(double k1, double b) {
        this.k1 = k1;
        this.b = b;
    }

    /**
     * Constructs a new {@link RankingWithBM25} with default parameter values.
     */
    public RankingWithBM25() {
        this.k1 = 1.2;
        this.b = 0.75;
    }

    @Override
    protected QueryProcessor computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {

        /*
         * Obtainig a number of preliminary results.
         */
        final TermWeight idf = context.preliminaryResult(CalculatingInvertedTermFrequency.getInstance());
        final TermDocumentWeight tf = context.preliminaryResult(context.equipment().termFrequencyCalculation());
        //for obtaining set of terms in each document.
        final EachDocumentTermsExtraction documentsTerms = context.preliminaryResult(ExtractingEachDocumentTerms.getInstance());
        //for obtaining document length
        final Map<Document, TermsOccurences> termsOccurences = context.preliminaryResult(CountingTermsOccurencesInEachDocument.getInstance());
        final double averageDocLength = context.preliminaryResult(ComputingAverageDocumentLength.getInstance());

        QueryProcessor res = new QueryProcessor() {
            @Override
            public DocumentRanker rankForQuery(final Collection<String> query) {
                return new DocumentRanker() {
                    @Override
                    public double score(Document document) {
                        /*
                         * BM25 score is calculated here
                         */

                        //obtaining the set of terms in the document
                        Set<String> allDocTerms = documentsTerms.termsExtractedFromDocument(document).allTerms();

                        //keeping only the query terms that are in the document
                        Collection<String> relevantTerms = new LinkedHashSet<>(query);
                        relevantTerms.retainAll(allDocTerms);

                        //compute score using a sum formula on each query term
                        int docLength = termsOccurences.get(document).totalNumberOfOccurences();
                        double score = 0.0;
                        for (String term : relevantTerms) {
                            double termIDF = idf.weight(term);
                            double termTF = tf.weight(term, document);

                            score += termIDF * (termTF * (k1 + 1)) / (termTF + k1 * (1 - b + b * (docLength / averageDocLength)));
                        }

                        return score;
                    }
                };

            }
        };

        logger.log(Level.INFO, "Just created a BM25 query processor with parameters : " + "k1={0}, b={1}", new Object[]{k1, b});

        return res;
    }
    private static final Logger logger = Logger.getLogger(RankingWithBM25.class.getName());
}
