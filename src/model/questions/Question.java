package model.questions;

import java.io.Serializable;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Question is a class that represents a question and its answer choices,
 * right and wrong.
 *
 * @author Anthony Nguyen
 * @version 2022 May 6
 */
public abstract class Question implements Serializable {

    protected static final String[] OPTIONS = {"A", "B", "C", "D"};

    protected final String myQuery;
    protected final Map<String, Answer> myAnswers;

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theDB a connection to a SQLite database.
     */
    public Question(Connection theDB, String theTable) {
        // SQLite code access DB and corresponding table using string table
        // Select random row:
        // "SELECT * FROM table ORDER BY RANDOM() LIMIT 1;"

        myQuery = ""; // Get string for QUES column

        Stack<Answer> choices = new Stack<>();

        // Build Answer objs from ANS columns
        // Push them onto choices

        Collections.shuffle(choices);

        myAnswers = new HashMap<>();
        for (String option : OPTIONS) {
            if (choices.isEmpty()) {
                break;
            } else {
                myAnswers.put(option, choices.pop());
            }
        }
    }

    /**
     * Checks if an answer submitted by a player to a question is correct
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
