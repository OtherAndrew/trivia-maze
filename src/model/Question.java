package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

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
    public Question(final String theQuery,
                    final ArrayList<Answer> theChoices) {
        Collections.shuffle(theChoices);
        myQuery = theQuery;
        myAnswers = new EnumMap<>(Option.class);
        int i = 0;
        for (Option letter : Option.values()) {
            if (i < theChoices.size()) {
                myAnswers.put(letter, theChoices.get(i++));
            } else {
                break;
            }
        }
    }

    // Check if user given option is valid for question
    public boolean validChoice(final Option theOption) {
        return myAnswers.containsKey(theOption);
    }

    // For use by calling method to check number of optins available
    public int numberOfChoices() {
        return myAnswers.size() - 1;
    }

    // For true-false and multiple choice
    public boolean checkAnswer(final Option theOption) {
        // Exception throw would not be necessary if method calling this one
        // checks first, or if we decide to handle this differently.
        Answer result = myAnswers.get(theOption);
        if (result == null) {
            throw new IllegalArgumentException();
        }
        return result.isCorrect();
    }

    // For short answer
    public boolean checkAnswer(final String theResponse) {
        boolean result = false;
        for(Answer choice : myAnswers.values()) {
            if(choice.toString().equals(theResponse)) {
                result = choice.isCorrect();
                break;
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return myQuery;
    }
}
