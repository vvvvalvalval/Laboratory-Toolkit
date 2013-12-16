/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine;

import edu.polytechnique.labtk.Analysis;
import search.engine.equipment.Document;
import search.engine.equipment.DocumentsEquipment;
import search.engine.queries.QueryProcessingEquipment;
import search.engine.queries.QueryProcessor;
import search.engine.termsindex.TermDocumentWeight;
import search.engine.termsindex.TermsIndexingEquipment;
import search.engine.tokenization.Token;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class SearchEngineEquipment implements QueryProcessingEquipment {

    private final Analysis<QueryProcessor, TermsIndexingEquipment> creatingQueryProcessor;

    @Override
    public Analysis<QueryProcessor, TermsIndexingEquipment> creatingQueryProcessor() {
        return creatingQueryProcessor;
    }
    private final Analysis<Iterable<Token>, DocumentsEquipment> tokenization;

    @Override
    public Analysis<Iterable<Token>, DocumentsEquipment> tokenization() {
        return tokenization;
    }
    private final Analysis<TermDocumentWeight, TermsIndexingEquipment> termFrequencyCalculation;

    @Override
    public Analysis<TermDocumentWeight, TermsIndexingEquipment> termFrequencyCalculation() {
        return termFrequencyCalculation;
    }
    private final Set<Document> allDocuments;

    @Override
    public Set<Document> allDocuments() {
        return this.allDocuments;
    }

    public SearchEngineEquipment(Analysis<QueryProcessor, TermsIndexingEquipment> creatingQueryProcessor, Analysis<Iterable<Token>, DocumentsEquipment> tokenization, Analysis<TermDocumentWeight, TermsIndexingEquipment> termFrequencyCalculation, Collection<? extends Document> allDocuments) {
        this.creatingQueryProcessor = creatingQueryProcessor;
        this.tokenization = tokenization;
        this.termFrequencyCalculation = termFrequencyCalculation;
        this.allDocuments = Collections.unmodifiableSet(new LinkedHashSet<>(allDocuments));
    }
}
