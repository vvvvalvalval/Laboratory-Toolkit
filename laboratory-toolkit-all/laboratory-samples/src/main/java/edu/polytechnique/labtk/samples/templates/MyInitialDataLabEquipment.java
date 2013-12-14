/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.templates;

import edu.polytechnique.labtk.Analysis;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface MyInitialDataLabEquipment {

    public MyInitialData initialData();

    public Analysis<ResultB, MyInitialDataLabEquipment> analysisB();
}
