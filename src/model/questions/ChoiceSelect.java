package model.questions;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * ChoiceSelect represents a question with multiple answer choices to pick from.
 */
public class ChoiceSelect extends Question implements Serializable {

    /**
     * Class version number.
     */
    @Serial
    private static final long serialVersionUID = 2189392842155253463L;
    /**
     * The options associated with an answer choice.
     */
    private static final String[] OPTIONS = {"a", "b", "c", "d"};

    /**
     * The answer choices along with their corresponding option.
     */
    private final Map<String, Answer> myAnswers;

    /**
     * Creates a multiple choice question with randomized order of answer
     * choices.
     *
     * @param theQuestion   the question to ask.
     * @param theChoices    the answer choices.
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
     * Checks if an answer is correct based off the options.
     *
     * @param theOption an option corresponding to an answer choice.
     * @return true if the option selected correct, else false.
     */
    @Override
    public boolean checkAnswer(final String theOption) {
        return Optional.ofNullable(myAnswers.get(theOption))
                .map(Answer::isCorrect)
                .orElse(false);
    }

    /**
     * Returns a list of the answer choices.
     *
     * @return list of answers for the multiple choice question.
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
