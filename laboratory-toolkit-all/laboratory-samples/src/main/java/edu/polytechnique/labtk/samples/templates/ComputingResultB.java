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
public class ComputingResultB extends Analysis<ResultB, MyInitialDataLabEquipment>{
    
    private final double algorithmParameter;

    public ComputingResultB(double algorithmParameter) {
        this.algorithmParameter = algorithmParameter;
    }

    @Override
    protected ResultB computeResult(ResultComputingContext<? extends MyInitialDataLabEquipment> context) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
