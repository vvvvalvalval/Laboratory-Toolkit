/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.text;

import edu.polytechnique.labtk.Analysis;
import java.util.List;

/**
 * Simple {@link TextLabEquipment} implementation.
 */
public class SimpleTextLabEquipment implements TextLabEquipment {
    
    public static enum LettersCountingStrategy{
        ALL_WORDS,
        FOUR_OR_MORE_LETTERS_WORDS;
    }
    
    //that enum constant decides from which words extraction the letters are counted.
    private final LettersCountingStrategy lettersCountingStrategy;

    //when we instantiate our equipment object, we have to specify a strategy.
    public SimpleTextLabEquipment(String text, LettersCountingStrategy lettersCountingStrategy) {
        this.text = text;
        this.lettersCountingStrategy = lettersCountingStrategy;
    }
    
    @Override
    public Analysis<List<String>, TextLabEquipment> relevantWordsForLettersCounting() {
        switch(this.lettersCountingStrategy){
            case ALL_WORDS:
                return WordsExtracting.getInstance();
            case FOUR_OR_MORE_LETTERS_WORDS:
                return FourOrMoreLettersWordsExtracting.getInstance();
            default:
                throw new AssertionError();//won't happen.
        }
    }

    /*
     * The following part was already there at the beginning.
     */
    
    private final String text;

    @Override
    public String text() {
        return this.text;
    }
}
