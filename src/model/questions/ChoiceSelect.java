package model.questions;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class ChoiceSelect extends Question implements Serializable {

    @Serial
    private static final long serialVersionUID = 2189392842155253463L;
    private static final String[] OPTIONS = {"a", "b", "c", "d"};

    private final Map<String, Answer> myAnswers;

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theQuestion the question to ask.
     * @param theChoices the answer choices.
     */
    ChoiceSelect(final String theQuestion, final LinkedList<Answer> theChoices) {
        super(theQuestion);
        myAnswers = new HashMap<>();
        for (String option : OPTIONS) {
            if (theChoices.isEmpty()) break;
            else myAnswers.put(option, theChoices.pop());
        }
    }

    /**
     * Checks if the provided option is one of the possible answers to the
     * query.
     *
     * @param theOption the option to check for.
     * @return if the option is one of the possible answers to the query.
     */
    public boolean validChoice(final String theOption) {
        return myAnswers.containsKey(theOption);
    }

    /**
     * Checks if an answer submitted by a player to a true/false or multiple
     * choice question is true.
     *
     * @param theOption an option submitted by a player.
     * @return if the option selected correct.
     */
    @Override
    public boolean checkAnswer(final String theOption) {
        return Optional.ofNullable(myAnswers.get(theOption)).map(Answer::isCorrect).orElse(false);
    }

    /**
     * @return list of answers for question.
     */
    @Override
    public List<String> getAnswers() {
        final List<String> choiceList = new LinkedList<>();
        for (String choice : myAnswers.keySet()) {
            choiceList.add(choice + ". " + myAnswers.get(choice).toString());
        }
        return choiceList;
    }
}
