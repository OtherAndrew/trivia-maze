package model;

import java.util.*;

/**
 * Question is a class that represents an option-based question and its answer
 * choices, right and wrong.
 *
 * @author Anthony Nguyen
 * @version 2022 May 6
 */
public class Question {

    private final String myQuery;
    private final Map<Option, Answer> myAnswers;

    // create a List of Answers elsewhere (i.e. when reading from SQLite),
    // shuffle it, and just pass it to Question

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     * @param theQuery the query
     * @param theChoices the set of potential answers to the query
     */
    public Question(final String theQuery, final List<Answer> theChoices) {
        // create local copy of choices to shuffle instead of param
        final List<Answer> choices = new ArrayList<>(theChoices);
        Collections.shuffle(choices);
        myQuery = theQuery;
        myAnswers = new EnumMap<>(Option.class);
        int i = 0;
        for (Option letter : Option.values()) {
            if (i < choices.size()) {
                myAnswers.put(letter, choices.get(i++));
            } else {
                break;
            }
        }
    }

    /**
     * Checks if the provided option is one of the possible answers to the
     * query.
     * @param theOption the option to check for.
     * @return if the option is one of the possible answers to the query.
     */
    public boolean validChoice(final Option theOption) {
        return myAnswers.containsKey(theOption);
    }

    /**
     * @return the number of answer choices available.
     */
    public int numberOfChoices() {
        return myAnswers.size() - 1;
    }

    /**
     * Checks if an answer submitted by a player to a true/false or multiple
     * choice question is true.
     * @param theOption an option submitted by a player.
     * @return if the option selected correct.
     */
    // For true-false and multiple choice
    // TODO: split multiple choice/true-false/short answer into different classes
    public boolean checkAnswer(final Option theOption) {
        // Exception throw would not be necessary if method calling this one
        // checks first, or if we decide to handle this differently.
        Answer result = myAnswers.get(theOption);
        if (result == null) {
            throw new IllegalArgumentException();
        }
        return result.isCorrect();
    }

    /**
     * Checks to see if the answer to a short answer question is true.
     * @param theResponse a response submitted by a player.
     * @return if the response is correct.
     */
    // For short answer
    public boolean checkAnswer(final String theResponse) {
        boolean result = false;
        for(Answer choice : myAnswers.values()) {
            if (choice.toString().equals(theResponse)) {
                result = choice.isCorrect();
                break;
            }
        }
        return result;
    }

    /**
     * @return the question query.
     */
    @Override
    public String toString() {
        return myQuery;
    }
}
