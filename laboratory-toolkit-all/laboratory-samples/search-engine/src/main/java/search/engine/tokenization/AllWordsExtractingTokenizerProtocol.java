/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.tokenization;

import edu.polytechnique.labtk.ResultComputingContext;
import search.engine.equipment.Document;
import search.engine.equipment.DocumentsEquipment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * {@link TokenizerProtocol} that simply splits the text of each field of each
 * document into words.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class AllWordsExtractingTokenizerProtocol implements TokenizerProtocol{

    private static final Pattern WORD_SPLITTER_PATTERN = Pattern.compile("\\W+");
    
    @Override
    public Iterable<Token> computeResult(ResultComputingContext<? extends DocumentsEquipment> context) {
        
        Set<Document> allDocuments = context.equipment().allDocuments();
        
        List<Token> tokens = new ArrayList<>();
        
        for(Document doc : allDocuments){
            for(Document.TextField field : doc.documentContent()){
                String[] splitText = WORD_SPLITTER_PATTERN.split(field.text());
                for (String term : splitText) {
                    tokens.add(new Token(term, doc, field));
                }
            }
        }
        
        logger.log(Level.INFO, "Just tokenized the documents into {0} words.", tokens.size());
        
        return Collections.unmodifiableList(tokens);
    }
    
    private static final Logger logger = Logger.getLogger(AllWordsExtractingTokenizerProtocol.class.getName());
}
