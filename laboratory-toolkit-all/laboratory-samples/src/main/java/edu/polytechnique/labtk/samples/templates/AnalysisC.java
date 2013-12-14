/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.templates;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.io.BufferedReader;
import java.util.Collection;
import java.util.NavigableMap;
import org.omg.CORBA_2_3.portable.InputStream;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class AnalysisC extends Analysis<ResultC, MyInitialDataLabEquipment>{

    @Override
    protected ResultC computeResult(ResultComputingContext<? extends MyInitialDataLabEquipment> context) {
        //fetching the initial data of the algorithm this Analysis participates in
        MyInitialData initialData = context.equipment().initialData();
        
        /*
         * Retrieving preliminary results from other Analyses.
         */
        //implemented as a singleton
        Analysis<ResultA, MyInitialDataLabEquipment> analysisA = AnalysisA.getInstance();
        ResultA resultA = context.preliminaryResult(analysisA);
        //implemented as a property of the equipment.
        Analysis<ResultB, MyInitialDataLabEquipment> analysisB = context.equipment().analysisB();
        ResultB resultB = context.preliminaryResult(analysisB);
        
        //computing the result
        ResultC result = this.performComputation(initialData, resultA, resultB);
        return result;
    }
    
    public static AnalysisC getInstance() {
        return ComputingResultCHolder.INSTANCE;
    }

    private ResultC performComputation(MyInitialData initialData, ResultA resultA, ResultB resultB) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static class ComputingResultCHolder {

        private static final AnalysisC INSTANCE = new AnalysisC();
    }
    
    
    
    private AnalysisC() {
    }
}
