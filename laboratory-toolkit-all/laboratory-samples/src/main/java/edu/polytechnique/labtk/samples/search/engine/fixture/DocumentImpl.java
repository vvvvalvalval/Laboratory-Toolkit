/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.polytechnique.labtk.samples.search.engine.fixture;

import edu.polytechnique.labtk.samples.search.engine.equipment.Document;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class DocumentImpl implements Document {

    private final List<TextField> textFields;

    private DocumentImpl(List<TextField> textFields) {
        this.textFields = textFields;
    }

    @Override
    public List<TextField> documentContent() {
        return this.textFields;
    }

    public static DocumentBuilder newDocumentBuilder(){
        return new DocumentBuilder();
    }
    
    private static class TextFieldImpl implements TextField {

        final String name;
        final String text;

        public TextFieldImpl(String name, String text) {
            this.name = name;
            this.text = text;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public String text() {
            return this.text;
        }
    }

    /**
     * Builder for documents.
     */
    public static class DocumentBuilder {

        private List<TextField> textFields1 = new ArrayList<>();

        public DocumentBuilder addTextField(String name, String content) {
            if (name == null) {
                throw new NullPointerException();
            }
            if (content == null) {
                throw new NullPointerException();
            }
            textFields1.add(new TextFieldImpl(name, content));
            return this;
        }

        public Document build() {
            return new DocumentImpl(Collections.unmodifiableList(this.textFields1));
        }
    }
}
