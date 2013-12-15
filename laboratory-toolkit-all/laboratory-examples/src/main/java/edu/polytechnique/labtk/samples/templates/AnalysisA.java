/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.templates;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class AnalysisA extends Analysis<ResultA, MyInitialDataLabEquipment>{
    
    private AnalysisA() {
    }
    
    public static AnalysisA getInstance() {
        return ComputingResultAHolder.INSTANCE;
    }

    @Override
    protected ResultA computeResult(ResultComputingContext<? extends MyInitialDataLabEquipment> context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static class ComputingResultAHolder {

        private static final AnalysisA INSTANCE = new AnalysisA();
    }
}
