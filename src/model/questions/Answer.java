package model.questions;

import java.io.Serial;
import java.io.Serializable;

/**
 * Answer is a class that represents an answer to a question.
 *
 * @author Anthony Nguyen
 * @version 2022 May 6
 */
public class Answer implements Serializable {

    @Serial
    private static final long serialVersionUID = 7662114296546856340L;
    private final String myAnswer;
    private final boolean myValidity;

    Answer(final String theAnswer, final boolean theValidity) {
        myAnswer = theAnswer;
        myValidity = theValidity;
    }

    public boolean isCorrect() {
        return myValidity;
    }

    @Override
    public String toString() {
        return myAnswer;
    }
}
