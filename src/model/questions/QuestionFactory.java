package model.questions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class QuestionFactory {
    private static final Random RAND = new Random();
    private static final int TRUEFALSE = 0;
    private static final int MULTICHOICE = 1;
    private static final int SHORTANS = 2;

    private Connection myConnection;
    private Statement myStatement;

    public void setUp() {
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
        switch (RAND.nextInt(3)) {
            case TRUEFALSE -> question = new ChoiceSelect(myStatement, "TF");
            case MULTICHOICE -> question = new ChoiceSelect(myStatement, "MC");
            case SHORTANS -> question = new ShortAnswer(myStatement);
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
