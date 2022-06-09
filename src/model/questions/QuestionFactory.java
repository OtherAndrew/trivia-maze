package model.questions;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * QuestionFactory is a factory that constructs and returns random questions,
 * either multiple choice or short answer, from a SQLite database.
 */
public class QuestionFactory {

    /**
     * The connection to the SQLite database.
     */
    private Connection myConnection;
    /**
     * Statement for executing SQLite statements and getting the results.
     */
    private Statement myStatement;

    /**
     * Creates a factory and establishes the connection to the database.
     */
    public QuestionFactory() {
        try {
            myConnection = DriverManager.getConnection(
                    "jdbc:sqlite::resource:Questions.db");
            myConnection.setAutoCommit(false);
            myStatement = myConnection.createStatement();
            myStatement.setQueryTimeout(30);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a random question, either multiple choice or short answer, via
     * statements extracting data from the database.
     *
     * @return a question.
     */
    public Question createQuestion() {
        Question question = null;
        try (final ResultSet rs = myStatement.executeQuery("SELECT * FROM " +
                "unified WHERE qid IS NOT NULL ORDER BY RANDOM() LIMIT 1")) {

            final String query = rs.getString(2);

            final int colCount = rs.getMetaData().getColumnCount();
            if (rs.getString(3).equals("SA")) {
                final ArrayList<String> choices = new ArrayList<>();
                for (int i = 4; i <= colCount; i++) {
                    if (rs.getString(i) != null)
                        choices.add(rs.getString(i));
                }
                question = new ShortAnswer(query, choices);
            } else {
                final LinkedList<Answer> choices = new LinkedList<>();
                choices.push(new Answer(rs.getString(4), true));
                for (int i = 5; i <= colCount; i++) {
                    if (rs.getString(i) != null)
                        choices.push(new Answer(rs.getString(i), false));
                }
                Collections.shuffle(choices);
                question = new ChoiceSelect(query, choices);
            }

            myStatement.executeUpdate("UPDATE unified SET qid=null WHERE qid=" +
                    rs.getInt(1));
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    /**
     * Cleans up the connection to the database.
     */
    public void cleanUp() {
        try {
            if (myStatement != null) myStatement.close();
            if (myConnection != null) myConnection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }
}
