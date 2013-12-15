/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.text;

import edu.polytechnique.labtk.Analysis;
import java.util.List;

/**
 * The equipment out future {@link Analysis}S shall use : for the moment, it
 * only gives access to the initial text.
 */
public interface TextLabEquipment {

    //here we give access to our initial data.
    public String text();

    //we just added this method, to be used by the Analysis that counts the letters.
    public Analysis<List<String>, TextLabEquipment> relevantWordsForLettersCounting();
}
