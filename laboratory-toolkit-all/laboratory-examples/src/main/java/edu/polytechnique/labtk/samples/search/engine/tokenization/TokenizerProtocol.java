/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.tokenization;

import edu.polytechnique.labtk.protocols.Protocol;
import edu.polytechnique.labtk.samples.search.engine.equipment.DocumentsEquipment;

/**
 * A {@link TokenizerProtocol} is a protocol for extracting all the tokens of a
 * document. This interface is only for clearer naming.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface TokenizerProtocol extends Protocol<Iterable<Token>, DocumentsEquipment> {
}
