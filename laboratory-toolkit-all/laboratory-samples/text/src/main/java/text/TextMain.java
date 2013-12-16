/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package text;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.Laboratory;
import edu.polytechnique.labtk.protocols.AnalysisWithProtocol;
import java.util.List;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class TextMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    }

    private static void retrievingTheResults() {
        String myText = "Lorem ipsum blah blah blah...";

        //[...]

        //instantiating the equipment that the Analyses will use
        TextLabEquipment myTextEquipment = new SimpleTextLabEquipment(myText, SimpleTextLabEquipment.LettersCountingStrategy.FOUR_OR_MORE_LETTERS_WORDS);
        //creating a Laboratory with that equipment
        Laboratory<TextLabEquipment> myLab = new Laboratory<>(myTextEquipment);

        //requesting all the results we want.
        List<String> flwords = FourOrMoreLettersWordsExtracting.getInstance().getResult(myLab);
        WordsFrequencies wordsFreqs = WordsFrequenciesCalculating.getInstance().getResult(myLab);
        //computing is not redundant. We can ask the same result lots of times, it will always be the same.
        WordsExtracting.getInstance().getResult(myLab);
        WordsExtracting.getInstance().getResult(myLab);
        WordsExtracting.getInstance().getResult(myLab);
    }
    
    private static void protocolAnalysis(){
        Analysis<WordsScore, TextLabEquipment> myModularAnalysis = new AnalysisWithProtocol<>(
                new PlusOneSquaredWordsScoringProtocol(
                new FrequenciesWordsScoringProtocol()));
    }
}
