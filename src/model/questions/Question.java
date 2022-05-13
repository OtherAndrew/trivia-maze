package model.questions;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

    private static final String[] OPTIONS = {"A", "B", "C", "D"};

    protected String myQuery;
    protected Map<String, Answer> myAnswers;

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theStatement  a Statement for a SQLite database.
     */
    public Question(final Statement theStatement, final String theTable) {
        try (ResultSet rs = theStatement.executeQuery("SELECT * FROM "
                + theTable + " ORDER BY RANDOM() LIMIT 1")) {
            myQuery = rs.getString("QUES");

            Stack<Answer> choices = new Stack<>();
            choices.push(new Answer(rs.getString("ANSC"), true));
            int colCount = rs.getMetaData().getColumnCount();
            boolean multipleCorrect = theTable.equals("SA");
            for (int i = 3; i <= colCount; i++) {
                choices.push(new Answer(rs.getString(i), multipleCorrect));
            }
            Collections.shuffle(choices);
            rs.deleteRow();

            myAnswers = new HashMap<>();
            for (String option : OPTIONS) {
                if (choices.isEmpty()) {
                    break;
                } else {
                    myAnswers.put(option, choices.pop());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
