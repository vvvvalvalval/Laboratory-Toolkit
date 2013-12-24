/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text;

import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.Protocol;

/**
 * Protocol that computes the initial formula for scoring words.
 */
public class FrequenciesWordsScoringProtocol implements Protocol<WordsScore, TextLabEquipment>{

    @Override
    public WordsScore computeResult(ResultComputingContext<? extends TextLabEquipment> context) {
        
        //fetching the preliminary results
        final LettersFrequencies lettersFrequencies = context.preliminaryResult(LettersCounting.getInstance());
        final WordsFrequencies wordsFrequencies = context.preliminaryResult(WordsFrequenciesCalculating.getInstance());

        //return the result
        return new WordsScore() {

            @Override
            public double score(String word) {
                
                //compute hideous formula
                return (Math.log(lettersFrequencies.numberOfOccurencesForLetter(word.charAt(0))
                        /lettersFrequencies.totalNumberOfLettersOccurences()) + 1)
                        * wordsFrequencies.numberOfOccurencesInText(word);
            }
        };
    }
    
}
