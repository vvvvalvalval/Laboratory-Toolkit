/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.statistics;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class MeanComputing extends Analysis<Double, StatisticsEquipment>{
    
    private MeanComputing() {
    }
    
    public static MeanComputing getInstance() {
        return MeanComputingHolder.INSTANCE;
    }

    @Override
    protected Double computeResult(ResultComputingContext<? extends StatisticsEquipment> context) {
        return context.preliminaryResult(context.equipment().nthMomentComputing(1));
    }
    
    private static class MeanComputingHolder {

        private static final MeanComputing INSTANCE = new MeanComputing();
    }
}
