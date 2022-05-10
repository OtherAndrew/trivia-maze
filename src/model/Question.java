package model;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

/**
 * Question is a class that represents an option-based question and its answer
 * choices, right and wrong.
 *
 * @author Anthony Nguyen
 * @version 2022 May 6
 */
public class Question implements Serializable {

    private static final Random RAND = new Random();
    private static final int TRUEFALSE = 0;
    private static final int MULTICHOICE = 1;
    private static final int SHORTANS = 2;

    private final String myQuery;
    private final Map<Option, Answer> myAnswers;

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theDB a connection to a SQLite database.
     */
    public Question(Connection theDB) {
        String table;
        switch (RAND.nextInt(3)) {
            case TRUEFALSE -> table = "TF";
            case MULTICHOICE -> table = "MC";
            case SHORTANS -> table = "SA";
        }

        // SQLite code access DB and corresponding table using string table
        // Select random row:
        // "SELECT * FROM table ORDER BY RANDOM() LIMIT 1;"

        myQuery = ""; // Get string for QUES column

        Stack<Answer> choices = new Stack<>();

        // Build Answer objs from ANS columns
        // Push them onto choices

        Collections.shuffle(choices);

        myAnswers = new EnumMap<>(Option.class);
        for (Option letter : Option.values()) {
            if (choices.isEmpty()) {
                break;
            } else {
                myAnswers.put(letter, choices.pop());
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
    public boolean checkAnswer(final String theResponse) {
        // for short answer
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
