/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.statistics;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.List;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class NthMomentComputing extends Analysis<Double, StatisticsEquipment> {

    private final int order;

    public NthMomentComputing(int order) {
        this.order = order;
    }

    @Override
    protected Double computeResult(ResultComputingContext<? extends StatisticsEquipment> context) {
        List<Double> values = context.equipment().values();
        double res = 0.0;

        for (Double value : values) {
            res += Math.pow(value, order);
        }
        if (values.size() > 0) {
            res /= values.size();
        }

        return res;
    }
}
