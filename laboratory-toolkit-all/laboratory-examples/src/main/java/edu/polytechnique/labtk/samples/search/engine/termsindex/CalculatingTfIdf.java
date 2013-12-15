/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.samples.search.engine.equipment.Document;

/**
 * {@link Analysis} that computes the TF-IDF of a term in a document,
 * representing the importance of a term specifically to a document. TF-IDF
 * calculation involves Terms Frequencies and Inverted Document Frequencies.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingTfIdf extends Analysis<TermDocumentWeight, TermsIndexingEquipment> {

    private CalculatingTfIdf() {
    }

    public static CalculatingTfIdf getInstance() {
        return TfIdfCalculatingHolder.INSTANCE;
    }

    @Override
    protected TermDocumentWeight computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
        //obtaining tf and idf
        final TermDocumentWeight tf = context.preliminaryResult(context.equipment().termFrequencyCalculation());
        final TermWeight idf = context.preliminaryResult(CalculatingInvertedTermFrequency.getInstance());

        return new TermDocumentWeight() {
            @Override
            public double weight(String term, Document document) {
                /*
                 * As the name suggests, tfidf = tf*idf
                 */
                return tf.weight(term, document) * idf.weight(term);
            }
        };

    }

    private static class TfIdfCalculatingHolder {

        private static final CalculatingTfIdf INSTANCE = new CalculatingTfIdf();
    }
}
