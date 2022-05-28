package model.questions;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShortAnswer extends Question implements Serializable {

    @Serial
    private static final long serialVersionUID = -3344095561551924264L;
    private final ArrayList<String> myAnswers;

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theQuestion the question to ask.
     * @param theChoices the possible answer choices.
     */
    ShortAnswer(final String theQuestion, final ArrayList<String> theChoices) {
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

    /**
     * @return list of answers for question.
     */
    @Override
    public List<String> getAnswers() {
        return new ArrayList<>(myAnswers);
    }
}
