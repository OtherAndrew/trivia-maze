package model.questions;

import java.io.Serializable;
import java.util.LinkedList;

public class ChoiceSelect extends Question implements Serializable {

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theQuestion
     * @param theChoices
     */
    public ChoiceSelect(final String theQuestion,
                        final LinkedList<Answer> theChoices) {
        super(theQuestion, theChoices);
    }

    /**
     * Checks if the provided option is one of the possible answers to the
     * query.
     * @param theOption the option to check for.
     * @return if the option is one of the possible answers to the query.
     */
    public boolean validChoice(final String theOption) {
        return myAnswers.containsKey(theOption);
    }

    /**
     * @return the number of answer choices available.
     */
    public int numberOfChoices() {
        return myAnswers.size();
    }

    /**
     * Checks if an answer submitted by a player to a true/false or multiple
     * choice question is true.
     * @param theOption an option submitted by a player.
     * @return if the option selected correct.
     */
    @Override
    public boolean checkAnswer(final String theOption) {
        return myAnswers.get(theOption).isCorrect();
    }
}
