/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integer.algebra;

import edu.polytechnique.labtk.Analysis;
import edu.polytechnique.labtk.ResultComputingContext;
import integer.equipment.IntegerEquipment;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * {@link Analysis} that forms the {@link Ring} of intergers modulo n.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class FormingModuloNRing extends Analysis<ModuloNRing, IntegerEquipment> {

    @Override
    protected ModuloNRing computeResult(ResultComputingContext<? extends IntegerEquipment> context) {
        ModuloNRing ring = new ModuloNRing(context.equipment().n());

        logger.log(Level.INFO, "Just formed the ring of remainders modulo {0}", context.equipment().n());
        
        return ring;
    }

    private FormingModuloNRing() {
    }

    public static FormingModuloNRing getInstance() {
        return FormingModuloNRingHolder.INSTANCE;
    }

    private static class FormingModuloNRingHolder {

        private static final FormingModuloNRing INSTANCE = new FormingModuloNRing();
    }
    private static final Logger logger = Logger.getLogger(FormingModuloNRing.class.getName());
}
