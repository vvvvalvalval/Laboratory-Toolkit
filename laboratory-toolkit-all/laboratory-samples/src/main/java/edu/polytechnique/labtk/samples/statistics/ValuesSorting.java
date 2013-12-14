/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.statistics;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class ValuesSorting extends Analysis<SortedValues, StatisticsEquipment> {

    private ValuesSorting() {
    }

    public static ValuesSorting getInstance() {
        return ValuesSortingHolder.INSTANCE;
    }

    @Override
    protected SortedValues computeResult(ResultComputingContext<? extends StatisticsEquipment> context) {
        List<Double> values = context.equipment().values();

        ArrayList<Double> templist = new ArrayList<>(values);
        Collections.sort(templist);

        final List<Double> sortedList = Collections.unmodifiableList(templist);

        final TreeSet<Double> tempSet = new TreeSet<>(templist);
        return new SortedValues() {
            @Override
            public List<Double> sortedList() {
                return sortedList();
            }

            @Override
            public NavigableSet<Double> sortedSet() {
                return tempSet;
            }
        };
    }

    private static class ValuesSortingHolder {

        private static final ValuesSorting INSTANCE = new ValuesSorting();
    }
}
