package model.questions;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

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
     * @param theQuestion the question to ask.
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

    // public abstract void display() for GUI display

    /**
     * @return the number of answer choices available.
     */
    public int numberOfChoices() {
        return getAnswers().size();
    }

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

    /**
     * @return question and answer choices as string.
     */
    @Override
    public String toString() {
        StringJoiner sj = new StringJoiner("\n");
        sj.add(getQuery());
        sj.add("\n");
        for (String answer : getAnswers()) {
            sj.add(answer);
        }
        return sj.toString();
    }

}
