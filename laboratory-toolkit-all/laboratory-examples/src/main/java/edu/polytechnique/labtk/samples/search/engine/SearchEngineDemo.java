/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.Laboratory;
import edu.polytechnique.labtk.protocols.AnalysisWithProtocol;
import edu.polytechnique.labtk.samples.search.engine.equipment.DocumentsEquipment;
import edu.polytechnique.labtk.samples.search.engine.fixture.HardCodedDocuments;
import edu.polytechnique.labtk.samples.search.engine.queries.ObtainingQueryRunner;
import edu.polytechnique.labtk.samples.search.engine.queries.QueryProcessor;
import edu.polytechnique.labtk.samples.search.engine.queries.RankingWithTfIdfInVSM;
import edu.polytechnique.labtk.samples.search.engine.termsindex.TermDocumentWeight;
import edu.polytechnique.labtk.samples.search.engine.termsindex.TermFrequenciesCatalog;
import edu.polytechnique.labtk.samples.search.engine.termsindex.TermsIndexingEquipment;
import edu.polytechnique.labtk.samples.search.engine.tokenization.AllWordsExtractingTokenizerProtocol;
import edu.polytechnique.labtk.samples.search.engine.tokenization.Token;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class SearchEngineDemo {
    
    public static void main(String[] args){
        
        Analysis<QueryProcessor, TermsIndexingEquipment> creatingQueryProcessor = RankingWithTfIdfInVSM.getInstance();
        Analysis<Iterable<Token>, DocumentsEquipment> tokenization = new AnalysisWithProtocol<>(new AllWordsExtractingTokenizerProtocol());
        Analysis<TermDocumentWeight, TermsIndexingEquipment> termFrequencyCalculation = TermFrequenciesCatalog.RAW_FREQUENCY;
        SearchEngineEquipment searchEngineEquipment = new SearchEngineEquipment(
                                                              creatingQueryProcessor,
                                                              tokenization,
                                                              termFrequencyCalculation,
                                                              HardCodedDocuments.ALL_DOCUMENTS);
        
        Laboratory<SearchEngineEquipment> lab = new Laboratory<>(searchEngineEquipment);
        
        String query = "Myth";
        System.out.println(ObtainingQueryRunner.getInstance().getResult(lab).runSearchQuery(query));
    }
}
