package model.questions;

import java.sql.*;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class QuestionFactory {
    private static final Random RAND = new Random();
    private static final String[] TYPE = {"TF", "MC", "SA"};

    private Connection myConnection;
    private Statement myStatement;

    public QuestionFactory() {
        try {
            myConnection = DriverManager.getConnection("jdbc:sqlite:Questions.db");
            myConnection.setAutoCommit(false);
            myStatement = myConnection.createStatement();
            myStatement.setQueryTimeout(30);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Question createQuestion() {
        Question question = null;
        String table = TYPE[RAND.nextInt(3)];
        boolean multipleCorrect = table.equals("SA");

        try (ResultSet rs = myStatement.executeQuery("SELECT * FROM "
                + table + " ORDER BY RANDOM() LIMIT 1")) {

            String query = rs.getString("QUES");

            Stack<Answer> choices = new Stack<>();
            choices.push(new Answer(rs.getString("ANSC"), true));
            int colCount = rs.getMetaData().getColumnCount();
            for (int i = 3; i <= colCount; i++) {
                choices.push(new Answer(rs.getString(i), multipleCorrect));
            }
            Collections.shuffle(choices);
            rs.deleteRow();

            if (multipleCorrect) {
                question = new ShortAnswer(query, choices);
            } else {
                question = new ChoiceSelect(query, choices);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public void cleanUp() {
        try {
            if (myConnection != null) {
                myConnection.rollback();
                myConnection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
