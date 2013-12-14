/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.text;

import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.protocols.Protocol;

/**
 *
 * A scoring protocol that adds one and squares the result of another.
 */
public class PlusOneSquaredWordsScoringProtocol implements Protocol<WordsScore, TextLabEquipment>{
    
    //the decorated protocol, to which the computing is forwarded
    private final Protocol<WordsScore, TextLabEquipment> delegate;
    
    @Override
    public WordsScore computeResult(ResultComputingContext<? extends TextLabEquipment> context) {
        
        //forward the computing to the delegate
        final WordsScore delegateResult = delegate.computeResult(context);
        
        return new WordsScore() {

            @Override
            public double score(String word) {
                double delegateScore = delegateResult.score(word);
                // the +, squared thing
                return Math.pow(delegateScore + 1, 2);
            }
        };
    }

    /**
     * Constucs a {@link PlusOneSquaredWordsScoringProtocol} from the delegate.
     * @param delegate 
     */
    public PlusOneSquaredWordsScoringProtocol(Protocol<WordsScore, TextLabEquipment> delegate) {
        this.delegate = delegate;
    }    
}
