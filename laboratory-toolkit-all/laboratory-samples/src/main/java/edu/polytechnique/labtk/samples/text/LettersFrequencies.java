/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.text;

import java.util.Set;

/**
 *
 * Result type for counting the letters in text.
 */
public interface LettersFrequencies {

    public Set<Character> setOfLetters();

    public int numberOfOccurencesForLetter(char letter);

    public int totalNumberOfLettersOccurences();
}
