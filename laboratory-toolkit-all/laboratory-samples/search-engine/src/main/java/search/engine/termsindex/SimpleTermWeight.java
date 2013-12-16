/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.termsindex;

import java.util.Map;

/**
 * A simple {@link TermWeight} implementation backed by a map, returning a
 * predifined value for unknwon terms.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class SimpleTermWeight implements TermWeight {

    private final Map<String, Double> backingMap;
    private final double unknownTermsValue;

    @Override
    public double weight(String term) {
        if (backingMap.containsKey(term)) {
            return backingMap.get(term);
        } else {
            return unknownTermsValue;
        }
    }

    public SimpleTermWeight(Map<String, Double> backingMap, double unknownTermsValue) {
        this.backingMap = backingMap;
        this.unknownTermsValue = unknownTermsValue;
    }
}
