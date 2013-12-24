/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import edu.polytechnique.labtk.SimpleAnalysis;
import integer.equipment.IntegerEquipment;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that forms the Group of invertibles of Z/nZ.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class FormingInvertiblesGroup extends SimpleAnalysis<GeneratedGroup<Integer>, IntegerEquipment> {

    @Override
    protected GeneratedGroup<Integer> computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        //preliminary results
        final ProductRing<Integer> chineseRing = context.preliminaryResult(FormingIsomorphicProductRing.getInstance());
        final Isomorphism<Integer, ProductElement<Integer>> iso = context.preliminaryResult(ConstructingChineseIsomorphism.getInstance());
        Collection<PrimePowerModuloNRing> chineseFactors = context.preliminaryResult(FormingPrimePowersRings.getInstance()).values();
        
        //forming the set of invertibles
        Collection<ProductElement<Integer>> recursiveInverses = this.recursiveInverses(chineseFactors.iterator());
        final Set<Integer> invertibles = new LinkedHashSet<>(recursiveInverses.size());
        for(ProductElement<Integer> productInv : recursiveInverses){
            invertibles.add(iso.antecendent(productInv));
        }

        //forming inverses
        final Map<Integer, Integer> inverses = new HashMap<>(invertibles.size());
        for (Integer element : invertibles) {
            if (inverses.containsKey(element)) {
                continue;
            }

            ProductElement<Integer> element1 = iso.image(element);

            ProductElement<Integer> inverse1 = new ProductElement<>(element1.size());
            for (PrimePowerModuloNRing chineseFactor : chineseFactors) {
                Integer inv = chineseFactor.multiplicativeGroup().opposite(element1.get(chineseFactor));
                inverse1.put(chineseFactor, inv);
            }

            Integer inverse = iso.antecendent(inverse1);
            inverses.put(element, inverse);
            inverses.put(inverse, element);
        }

        //forming generators
        final Set<Integer> generators = new LinkedHashSet<>();
        for (PrimePowerModuloNRing chineseFactor : chineseFactors) {
            Collection<Integer> chGenerators = chineseFactor.multiplicativeGroup().generators();

            for (Integer chGenerator : chGenerators) {
                ProductElement<Integer> generator = new ProductElement<>(chineseFactors.size());
                for (PrimePowerModuloNRing chineseFactor2 : chineseFactors) {
                    generator.put(chineseFactor2, chineseFactor2.one());
                }
                generator.put(chineseFactor, chGenerator);

                generators.add(iso.antecendent(generator));
            }
        }

        logger.log(Level.INFO, "Just formed the multiplicative group of invertibles of Z/{0}Z.", context.equipment().n());

        return new GeneratedGroup<Integer>() {
            @Override
            public Collection<Integer> generators() {
                return generators;
            }

            @Override
            public Set<Integer> elements() {
                return invertibles;
            }

            @Override
            public Integer plus(Integer a, Integer b) {
                return iso.antecendent(chineseRing.times(iso.image(a), iso.image(b)));
            }

            @Override
            public Integer opposite(Integer a) {
                return inverses.get(a);
            }

            @Override
            public Integer zero() {
                return 1;
            }
        };
    }
    
    private Collection<ProductElement<Integer>> recursiveInverses(Iterator<PrimePowerModuloNRing> iterator){
        if(iterator.hasNext()){
            PrimePowerModuloNRing ring = iterator.next();
            Set<Integer> inversibles = ring.multiplicativeGroup().elements();
            
            Collection<ProductElement<Integer>> previous = this.recursiveInverses(iterator);
            
            Collection<ProductElement<Integer>> res = new ArrayList<>(inversibles.size()*previous.size());
            
            for(Integer head : inversibles){
                for(ProductElement<Integer> tail : previous){
                    ProductElement<Integer> productInversible = new ProductElement<>(tail.size()+1);
                    
                    productInversible.putAll(tail);
                    productInversible.put(ring, head);
                    
                    res.add(productInversible);
                }
            }
            
            return res;
        } else {
            return Collections.singletonList(new ProductElement<Integer>());
        }
    }
    
    private static final Logger logger = Logger.getLogger(FormingInvertiblesGroup.class.getName());

    private FormingInvertiblesGroup() {
    }

    public static FormingInvertiblesGroup getInstance() {
        return FormingInvertiblesGroupHolder.INSTANCE;
    }

    private static class FormingInvertiblesGroupHolder {

        private static final FormingInvertiblesGroup INSTANCE = new FormingInvertiblesGroup();
    }
}
