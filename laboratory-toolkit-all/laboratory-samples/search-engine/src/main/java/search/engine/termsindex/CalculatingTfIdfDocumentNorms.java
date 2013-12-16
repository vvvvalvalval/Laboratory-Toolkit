/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import search.engine.equipment.Document;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that computes the euclidian norm of documents viewed as
 * TF-IDF vectors on a terms space.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class CalculatingTfIdfDocumentNorms extends Analysis<Map<Document, Double>, TermsIndexingEquipment> {

    @Override
    protected Map<Document, Double> computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
        TermDocumentWeight tfIdf = context.preliminaryResult(CalculatingTfIdf.getInstance());
        EachDocumentTermsExtraction termsExtraction = context.preliminaryResult(ExtractingEachDocumentTerms.getInstance());
        Set<Document> allDocuments = context.equipment().allDocuments();

        Map<Document, Double> res = new HashMap<>(allDocuments.size());

        for (Document document : allDocuments) {
            Set<String> docTerms = termsExtraction.termsExtractedFromDocument(document).allTerms();
            double norm = 0.0;
            for (String term : docTerms) {
                double coordinate = tfIdf.weight(term, document);
                norm += Math.pow(coordinate, 2);
            }
            norm = Math.sqrt(norm);
            res.put(document, norm);
        }

        logger.log(Level.INFO, "Just computed the TF-IDF vector euclidian norm of documents.");

        return Collections.unmodifiableMap(res);
    }

    private CalculatingTfIdfDocumentNorms() {
    }

    public static CalculatingTfIdfDocumentNorms getInstance() {
        return CalculatingTfIdfDocumentNormsHolder.INSTANCE;
    }

    private static class CalculatingTfIdfDocumentNormsHolder {

        private static final CalculatingTfIdfDocumentNorms INSTANCE = new CalculatingTfIdfDocumentNorms();
    }
    private static final Logger logger = Logger.getLogger(CalculatingTfIdfDocumentNorms.class.getName());
}