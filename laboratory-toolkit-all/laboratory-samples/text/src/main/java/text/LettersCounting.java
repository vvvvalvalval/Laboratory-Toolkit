/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class LettersCounting extends Analysis<LettersFrequencies, TextLabEquipment>{

    @Override
    protected LettersFrequencies computeResult(ResultComputingContext<? extends TextLabEquipment> context) {
        
        //we extract the relevant words for counting the letters
        Analysis<List<String>, TextLabEquipment> relevantWordsForLettersCounting = context.equipment().relevantWordsForLettersCounting();
        List<String> relevantWords = context.preliminaryResult(relevantWordsForLettersCounting);
        
        /*
         * We count the letters;
         */
        int lettersCount = 0;
        final Map<Character, Integer> frequencies = new LinkedHashMap<>();
        
        for(String word : relevantWords){
            char[] array = word.toLowerCase().toCharArray();
            for (int i = 0; i < array.length; i++) {
                Character letter = array[i];
                if(frequencies.containsKey(letter)){
                    frequencies.put(letter, frequencies.get(letter)+1);
                } else {
                    frequencies.put(letter, 1);
                }
                
                lettersCount++;
            }
        }
        
        /*
         * returning the result
         */
        
        final Set<Character> setOfLetters = Collections.unmodifiableSet(frequencies.keySet());
        final int totalNumberOfOccurences = lettersCount;
        
        return new LettersFrequencies() {

            @Override
            public Set<Character> setOfLetters() {
                return setOfLetters;
            }

            @Override
            public int numberOfOccurencesForLetter(char letter) {
                letter = Character.toLowerCase(letter);
                if(frequencies.containsKey(letter)){
                    return frequencies.get(letter);
                } else {
                    return 0;
                }
            }

            @Override
            public int totalNumberOfLettersOccurences() {
                return totalNumberOfOccurences;
            }
        };
    }
    
    private LettersCounting() {
    }
    
    public static LettersCounting getInstance() {
        return LettersCountingHolder.INSTANCE;
    }
    
    private static class LettersCountingHolder {

        private static final LettersCounting INSTANCE = new LettersCounting();
    }
}
