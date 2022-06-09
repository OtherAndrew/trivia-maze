package model.questions;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Question is a class that represents a question and its answers, right and
 * wrong.
 *
 * @author Anthony Nguyen
 * @version 2022 May 6
 */
public abstract class Question implements Serializable {

    /**
     * Class version number.
     */
    @Serial
    private static final long serialVersionUID = -919594342983300606L;

    /**
     * String representation of the question.
     */
    private final String myQuery;

    /**
     * Creates a question.
     *
     * @param theQuestion the question to ask.
     */
    protected Question(final String theQuestion) {
        myQuery = theQuestion;
    }

    /**
     * Checks if an answer to a question is correct.
     *
     * @param theAnswer an answer to be checked.
     * @return true if the answer is correct, else false.
     */
    public abstract boolean checkAnswer(final String theAnswer);

    /**
     * @return the question query.
     */
    public String getQuery() {
        return myQuery;
    }

    /**
     * @return list of answers for question.
     */
    public abstract List<String> getAnswers();
}
