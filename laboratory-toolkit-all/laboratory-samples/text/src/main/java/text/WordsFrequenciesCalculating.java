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
 * {@link Analysis} that extracts words frequencies information from text.
 */
public class WordsFrequenciesCalculating extends Analysis<WordsFrequencies, TextLabEquipment> {

    @Override
    protected WordsFrequencies computeResult(ResultComputingContext<? extends TextLabEquipment> context) {
        
        //first, let's retrieve the words.
        List<String> words = context.preliminaryResult(FourOrMoreLettersWordsExtracting.getInstance());

        //this map will hold both the set of words and their frequencies.
        final Map<String, Integer> frequencies = new LinkedHashMap<>();
        //this integer counts the occurences of all words.
        int wordsCount = 0;
        for (String word : words) {
            //we increment the number of occurences of all words
            wordsCount++;
            //we increment the number of occurences of the current word.
            if (frequencies.containsKey(word)) {
                frequencies.put(word, frequencies.get(word) + 1);
            } else {
                frequencies.put(word, 1);
            }
        }

        final Set<String> setOfWords = Collections.unmodifiableSet(frequencies.keySet());
        final int totalNumberOfOccurences = wordsCount;

        //we return a result backed by the map of frequencies and the count of words :
        return new WordsFrequencies() {
            @Override
            public Set<String> setOfWords() {
                return setOfWords;
            }

            @Override
            public int numberOfOccurencesInText(String anyWord) {
                if (setOfWords().contains(anyWord)) {
                    return 0;
                } else {
                    return frequencies.get(anyWord);
                }
            }

            @Override
            public int totalNumberOfWordsOccurences() {
                return totalNumberOfOccurences;
            }
        };
    }

    private WordsFrequenciesCalculating() {
    }

    public static WordsFrequenciesCalculating getInstance() {
        return WordsFrequenciesCalculatingHolder.INSTANCE;
    }

    private static class WordsFrequenciesCalculatingHolder {

        private static final WordsFrequenciesCalculating INSTANCE = new WordsFrequenciesCalculating();
    }
}
