/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import java.util.logging.Level;
import java.util.logging.Logger;
import search.engine.equipment.Document;

/**
 * {@link Analysis} that computes the TF-IDF of a term in a document,
 * representing the importance of a term specifically to a document. TF-IDF
 * calculation involves Terms Frequencies and Inverted Document Frequencies.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingTfIdf extends SimpleAnalysis<TermDocumentWeight, TermsIndexingEquipment> {

    @Override
    protected TermDocumentWeight computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
        //obtaining tf and idf
        final TermDocumentWeight tf = context.preliminaryResult(context.equipment().termFrequencyCalculation());
        final TermWeight idf = context.preliminaryResult(CalculatingInvertedTermFrequency.getInstance());

        logger.log(Level.INFO, "Just formed the TF-IDF of all terms.");
        
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

    private static final Logger logger = Logger.getLogger(CalculatingTfIdf.class.getName());
    
    private CalculatingTfIdf() {
    }

    public static CalculatingTfIdf getInstance() {
        return TfIdfCalculatingHolder.INSTANCE;
    }

    private static class TfIdfCalculatingHolder {

        private static final CalculatingTfIdf INSTANCE = new CalculatingTfIdf();
    }
}
