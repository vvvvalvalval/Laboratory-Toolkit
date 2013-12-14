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
public class StandardDeviationComputing extends Analysis<Double, StatisticsEquipment>{
    
    private StandardDeviationComputing() {
    }
    
    public static StandardDeviationComputing getInstance() {
        return StandardDeviationComputingHolder.INSTANCE;
    }

    @Override
    protected Double computeResult(ResultComputingContext<? extends StatisticsEquipment> context) {
        double mean = context.preliminaryResult(MeanComputing.getInstance());
        double moment2 = context.preliminaryResult(context.equipment().nthMomentComputing(2));
        
        return Math.sqrt(moment2-Math.pow(mean, 2));        
    }
    
    private static class StandardDeviationComputingHolder {

        private static final StandardDeviationComputing INSTANCE = new StandardDeviationComputing();
    }
}
