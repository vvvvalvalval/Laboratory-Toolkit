/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search.engine.tokenization;

import search.engine.equipment.Document;

/**
 * A {@link Token} is a getTerm (one might say "word") that is located in a
 * field of a document.
 *
 * @author Valentin Waeselynck <valentin.waeselynck@polytechnique.edu>
 */
public class Token {

    private final String term;
    private final Document document;
    private final Document.TextField field;

    /**
     * Constructs a new {@link Token} instance, representing a term extraced
     * from the specified field of the specified document.
     *
     * @param term
     * @param document
     * @param field
     */
    public Token(String term, Document document, Document.TextField field) {
        if (term == null || document == null || field == null) {
            throw new NullPointerException();
        }
        this.term = term;
        this.document = document;
        this.field = field;
    }

    /**
     * The term that is contained in this {@link Token}.
     *
     * @return the term of this {@link Token}.
     */
    public String getTerm() {
        return term;
    }

    /**
     * The {@link Document} this {@link Token} was extracted from.
     *
     * @return the document this {@link Token} was extracted from.
     */
    public Document getDocument() {
        return document;
    }

    /**
     * The field of the document that this {@link Token} was extracted from.
     *
     * @return the field as a {@link Document.TextField}.
     */
    public Document.TextField getField() {
        return field;
    }
}
