/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.AnalysisWithProtocol;
import edu.polytechnique.labtk.Laboratory;
import search.engine.equipment.DocumentsEquipment;
import search.engine.fixture.HardCodedDocuments;
import search.engine.queries.ObtainingQueryRunner;
import search.engine.queries.QueryProcessor;
import search.engine.queries.RankingWithTfIdfInVSM;
import search.engine.termsindex.TermDocumentWeight;
import search.engine.termsindex.TermFrequenciesCatalog;
import search.engine.termsindex.TermsIndexingEquipment;
import search.engine.tokenization.AllWordsExtractingTokenizerProtocol;
import search.engine.tokenization.Token;

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
