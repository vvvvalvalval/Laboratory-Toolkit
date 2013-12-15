/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.statistics;

import java.util.List;
import java.util.NavigableSet;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface SortedValues {
    
    public List<Double> sortedList();
    
    public NavigableSet<Double> sortedSet();
}
