/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.equipment;

import java.util.List;

/**
 * A simplistic representation of an indexed document (such as a web page), as a
 * list of text fields containing text.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public interface Document {

    /**
     * Returns the content of the document as a list of {@link TextField}s.
     * @return the content of the document.
     */
    public List<TextField> documentContent();
    
    /**
     * A {@link TextField} consists of a field name and a text content.
     */
    public static interface TextField {
        
        /**
         * The name (type) of the field.
         * Examples : "heading", "meta", "paragraph", "title", ...
         * @return the field name of this {@link TextField} as a String.
         */
        public String name();
        
        /**
         * The text content of this {@link TextField}.
         * @return the text contained in this {@link TextField}.
         */
        public String text();
    }
}
