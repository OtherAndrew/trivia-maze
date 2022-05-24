package model.questions;

import java.io.Serial;
import java.io.Serializable;

/**
 * Question is a class that represents a question and its answer choices,
 * right and wrong.
 *
 * @author Anthony Nguyen
 * @version 2022 May 6
 */
public abstract class Question implements Serializable {

    @Serial
    private static final long serialVersionUID = -919594342983300606L;
    private final String myQuery;

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theQuestion
     */
    protected Question(final String theQuestion) {
        myQuery = theQuestion;
    }

    /**
     * Checks if an answer submitted by a player to a question is correct
     *
     * @param theAnswer an answer submitted by a player.
     * @return if the option selected correct.
     */
    public abstract boolean checkAnswer(final String theAnswer);

    /**
     * @return the question query.
     */
    @Override
    public String toString() {
        return myQuery;
    }
}
