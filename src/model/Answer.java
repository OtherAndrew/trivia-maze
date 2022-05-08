package model;

/**
 * Answer is a class that represents an answer to a question.
 *
 * @author Anthony Nguyen
 * @version 2022 May 6
 */
public class Answer {
    private final String myAnswer;
    private final boolean myValidity;

    public Answer(final String theAnswer, final boolean theValidity) {
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
