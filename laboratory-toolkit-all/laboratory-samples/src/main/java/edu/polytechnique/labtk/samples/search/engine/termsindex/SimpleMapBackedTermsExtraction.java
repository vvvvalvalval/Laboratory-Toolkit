/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.termsindex;

import edu.polytechnique.labtk.samples.search.engine.tokenization.Token;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Simple {@link TermsExtraction} implementation backed by a map.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
class SimpleMapBackedTermsExtraction implements TermsExtraction {

    private final Map<String, Collection<Token>> tokensOfTerm;

    public SimpleMapBackedTermsExtraction(Map<String, Collection<Token>> tokensOfTerms) {
        //defensive copying and making unmodifiable
        Map<String, Collection<Token>> tmpMap = new LinkedHashMap<>(tokensOfTerms.size());
        for (Map.Entry<String, Collection<Token>> entry : tokensOfTerms.entrySet()) {
            tmpMap.put(entry.getKey(), Collections.unmodifiableCollection(entry.getValue()));
        }
        //making set of entries immutable.
        this.tokensOfTerm = Collections.unmodifiableMap(tmpMap);
    }

    @Override
    public Collection<Token> tokensOfTerms(String term) {
        if (tokensOfTerm.containsKey(term)) {
            return tokensOfTerm.get(term);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Set<String> allTerms() {
        return this.tokensOfTerm.keySet();
    }
}
