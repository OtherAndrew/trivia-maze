package model.questions;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ShortAnswer represents a question that has a number of correct answers.
 */
public class ShortAnswer extends Question implements Serializable {

    /**
     * Class version number.
     */
    @Serial
    private static final long serialVersionUID = -3344095561551924264L;

    /**
     * The accepted answers.
     */
    private final ArrayList<String> myAnswers;

    /**
     * Creates a short answer question.
     *
     * @param theQuestion   the question to ask.
     * @param theChoices    the accepted answers.
     */
    ShortAnswer(final String theQuestion, final ArrayList<String> theChoices) {
        super(theQuestion);
        myAnswers = theChoices;
    }

    /**
     * Checks to see if an answer is accepted.
     *
     * @param theResponse   an answer to check.
     * @return true if the response is accepted, else false.
     */
    @Override
    public boolean checkAnswer(final String theResponse) {
        return myAnswers.contains(theResponse);
    }

    /**
     * Returns the accepted answers.
     *
     * @return list of answers for the short answer question.
     */
    @Override
    public List<String> getAnswers() {
        return new ArrayList<>(myAnswers);
    }
}
