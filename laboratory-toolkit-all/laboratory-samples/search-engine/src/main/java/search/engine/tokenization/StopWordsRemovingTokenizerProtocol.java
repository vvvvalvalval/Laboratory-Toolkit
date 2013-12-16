/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.tokenization;

import edu.polytechnique.labtk.ResultComputingContext;
import search.engine.equipment.DocumentsEquipment;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.Predicate;

/**
 * A {@link TokenizerProtocol} that filtrates the <em>stop-words</em> (common
 * words that contribute poorly to the overall meaning, such as "the", "a",
 * "is", ...) from the {@link Token}s extracted by another
 * {@link TokenizerProtocol}.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class StopWordsRemovingTokenizerProtocol implements TokenizerProtocol {

    private final TokenizerProtocol input;
    private final Set<String> stopWords;

    @Override
    public Iterable<Token> computeResult(ResultComputingContext<? extends DocumentsEquipment> context) {

        final Iterable<Token> inputTokens = input.computeResult(context);

        logger.log(Level.INFO, "Filtering the stopwords from tokens.");
        
        return new Iterable<Token>() {
            @Override
            public Iterator<Token> iterator() {
                //filtrate stopwords
                return IteratorUtils.filteredIterator(inputTokens.iterator(),
                        new Predicate<Token>() {
                    @Override
                    public boolean evaluate(Token token) {
                        return !isStopWord(token.getTerm());
                    }
                });
            }
        };
    }

    protected boolean isStopWord(String word) {
        if (word == null) {
            throw new NullPointerException();
        }
        return this.stopWords.contains(word);
    }

    public StopWordsRemovingTokenizerProtocol(TokenizerProtocol input, Set<String> stopWords) {
        this.input = input;
        this.stopWords = stopWords;
    }
    
    private static final Logger logger = Logger.getLogger(StopWordsRemovingTokenizerProtocol.class.getName());
}
