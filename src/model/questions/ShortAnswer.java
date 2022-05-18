package model.questions;

import java.io.Serializable;
import java.util.ArrayList;

public class ShortAnswer extends Question implements Serializable {

    private final ArrayList<String> myAnswers;

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theQuestion
     * @param theChoices
     */
    public ShortAnswer(final String theQuestion,
                       final ArrayList<String> theChoices) {
        super(theQuestion);
        myAnswers = theChoices;
    }

    /**
     * Checks to see if the answer to a short answer question is true.
     *
     * @param theResponse a response submitted by a player.
     * @return if the response is correct.
     */
    @Override
    public boolean checkAnswer(final String theResponse) {
        return myAnswers.contains(theResponse);
    }
}
