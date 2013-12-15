/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.statistics;

import edu.polytechnique.labtk.Analysis;
import java.util.List;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface StatisticsEquipment {

    public List<Double> values();

    public Analysis<Double, StatisticsEquipment> valuesSorting();

    public Analysis<Double, StatisticsEquipment> nthMomentComputing(int order);
}
