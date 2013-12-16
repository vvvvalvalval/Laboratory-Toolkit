/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import search.engine.equipment.Document;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that computes the average length of documents.
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ComputingAverageDocumentLength extends Analysis<Double, TermsIndexingEquipment>{
    
    private static final Logger logger = Logger.getLogger(ComputingAverageDocumentLength.class.getName());
    
    private ComputingAverageDocumentLength() {
    }
    
    public static ComputingAverageDocumentLength getInstance() {
        return ComputingAverageDocumentLengthHolder.INSTANCE;
    }

    @Override
    protected Double computeResult(ResultComputingContext<? extends TermsIndexingEquipment> context) {
        Set<Document> allDocuments = context.equipment().allDocuments();
        Map<Document, TermsOccurences> termsOccurences = context.preliminaryResult(CountingTermsOccurencesInEachDocument.getInstance());
        
        double sum = 0.0;
        for(Document document : allDocuments){
            sum = termsOccurences.get(document).totalNumberOfOccurences();
        }

        double average = sum/allDocuments.size();
        
        logger.log(Level.INFO, "Just computed the average document length : {0}", average);
        
        return average;
    }
    
    private static class ComputingAverageDocumentLengthHolder {

        private static final ComputingAverageDocumentLength INSTANCE = new ComputingAverageDocumentLength();
    }
}
