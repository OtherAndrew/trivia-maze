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

    /**
     * Class version number.
     */
    @Serial
    private static final long serialVersionUID = 7662114296546856340L;

    /**
     * String representation of the answer.
     */
    private final String myAnswer;
    /**
     * The answer's correctness with regard to the question.
     */
    private final boolean myValidity;

    /**
     * Creates an answer.
     *
     * @param theAnswer     the String representation of the answer.
     * @param theValidity   the answer's correctness.
     */
    Answer(final String theAnswer, final boolean theValidity) {
        myAnswer = theAnswer;
        myValidity = theValidity;
    }

    /**
     * Returns if this answer is a correct one.
     *
     * @return true if the answer is correct, else false.
     */
    public boolean isCorrect() {
        return myValidity;
    }

    /**
     * Returns the answer text.
     *
     * @return the String representation of the answer.
     */
    @Override
    public String toString() {
        return myAnswer;
    }
}
