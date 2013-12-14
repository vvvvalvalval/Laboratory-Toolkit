/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.text;

import java.util.Set;

/**
 * Custom result type that gives us information about the distribution of words
 * in the text.
 */
public interface WordsFrequencies {

    /**
     * Gives us all the words without repetition.
     *
     * @return the set of all words in the text.
     */
    public Set<String> setOfWords();

    /**
     * @param anyWord a word, even one that's not in the text.
     * @return the number of occurences of that word in the text.
     */
    public int numberOfOccurencesInText(String anyWord);

    /**
     * Gives us the total number of word occurences in the text, e.g so we can
     * calculate frequencies.
     *
     * @return the total number of word occurences.
     */
    public int totalNumberOfWordsOccurences();
}
