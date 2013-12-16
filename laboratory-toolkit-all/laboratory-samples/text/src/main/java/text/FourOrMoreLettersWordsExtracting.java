/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link Analysis} that extracts all the words of the text but those with less than 4 letters.
 */
public class FourOrMoreLettersWordsExtracting extends Analysis<List<String>, TextLabEquipment>{
    
    private FourOrMoreLettersWordsExtracting() {
    }
    
    public static FourOrMoreLettersWordsExtracting getInstance() {
        return FourOrMoreLettersWordsExtractingHolder.INSTANCE;
    }

    @Override
    protected List<String> computeResult(ResultComputingContext<? extends TextLabEquipment> context) {
        
        //we explicitely request the result of WordsExtracting as a preliminary result.
        List<String> allWords = context.preliminaryResult(WordsExtracting.getInstance());
        
        //we filtrate the words
        List<String> fourOrMoreLettersWords = new ArrayList<>();
        for(String word : allWords){
            if(word.length()>=4){
                fourOrMoreLettersWords.add(word);
            }
        }
        //we make it immutable, it's safer
        fourOrMoreLettersWords = Collections.unmodifiableList(fourOrMoreLettersWords);
        
        return fourOrMoreLettersWords;
    }
    
    private static class FourOrMoreLettersWordsExtractingHolder {

        private static final FourOrMoreLettersWordsExtracting INSTANCE = new FourOrMoreLettersWordsExtracting();
    }
}
