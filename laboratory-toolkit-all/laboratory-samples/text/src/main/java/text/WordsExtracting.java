/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Analysis that extracts the words from the raw text.
 */
public class WordsExtracting extends Analysis<List<String>, TextLabEquipment> {

    //this method defines of the extracting of the words is performed.
    @Override
    protected List<String> computeResult(ResultComputingContext<? extends TextLabEquipment> context) {
        /*
         * The ResultComputingContext gives us access to the equipment, from which we access the text.
         */
        TextLabEquipment equipment = context.equipment();
        String rawText = equipment.text();
        //now we compute the result, by splitting the text into words.
        String[] split = rawText.split("\\W");
        List<String> result = Collections.unmodifiableList(Arrays.asList(split));
        
        return result;
    }

    /*
     * Our class is implemented using the Singleton pattern, which is common for Analyses.
     */
    private WordsExtracting() {
    }

    public static WordsExtracting getInstance() {
        return WordsExtractingHolder.INSTANCE;
    }

    private static class WordsExtractingHolder {

        private static final WordsExtracting INSTANCE = new WordsExtracting();
    }
}
