package model.questions;

import java.sql.*;
import java.util.*;

import static model.questions.QuestionFactory.Type.*;

public class QuestionFactory {

    enum Type {TF, MC, SA}
    private static final Random RAND = new Random();
    private static final List<Type> TYPES = new ArrayList<>(List.of(TF, MC, SA));
    private Connection myConnection;
    private Statement myStatement;
    private final Map<Type, Integer> myTableUsage;

    public QuestionFactory() {
        setUp();
        myTableUsage = new EnumMap<>(Type.class);
        evaluateTableUsage();
    }

    private void setUp() {
        try {
            myConnection = DriverManager.getConnection("jdbc:sqlite:Questions.db");
            myConnection.setAutoCommit(false);
            myStatement = myConnection.createStatement();
            myStatement.setQueryTimeout(30);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    private void evaluateTableUsage() {
        ResultSet counter = null;
        try {
            for (Type table : TYPES) {
                counter = myStatement.executeQuery("SELECT COUNT(qid) AS " +
                        " total FROM " + table);
                myTableUsage.put(table, counter.getInt("total"));
            }
        } catch (final SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (counter != null) counter.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void evaluateTableUsage(final Type theTable) {
        int count = myTableUsage.get(theTable);
        if (--count == 0) TYPES.remove(theTable);
        else myTableUsage.replace(theTable, count);
    }

    public Question createQuestion() {
        return createQuestion(TYPES.get(RAND.nextInt(TYPES.size())));
    }

    private Question createQuestion(final Type theTable) {
        Question question = null;
        try (final ResultSet rs = myStatement.executeQuery("SELECT * FROM " +
                theTable + " WHERE qid IS NOT NULL ORDER BY RANDOM() LIMIT 1")) {

            final String query = rs.getString(2);

            final int colCount = rs.getMetaData().getColumnCount();
            if (theTable == SA) {
                final ArrayList<String> choices = new ArrayList<>();
                for (int i = 3; i <= colCount; i++)
                    choices.add(rs.getString(i));
                question = new ShortAnswer(query, choices);
            } else {
                final LinkedList<Answer> choices = new LinkedList<>();
                choices.push(new Answer(rs.getString(3), true));
                for (int i = 4; i <= colCount; i++)
                    choices.push(new Answer(rs.getString(i), false));
                Collections.shuffle(choices);
                question = new ChoiceSelect(query, choices);
            }

            myStatement.executeUpdate("UPDATE " + theTable +
                    " SET qid=null WHERE qid=" + rs.getInt("qid"));
            evaluateTableUsage(theTable);
        } catch (final SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public void cleanUp() {
        try {
            if (myStatement != null) myStatement.close();
            if (myConnection != null) myConnection.close();
        } catch (final SQLException e) {
            e.printStackTrace();
        }
    }

    // FOR TESTING
    public static void main(final String[] theArgs) {
        final QuestionFactory qf = new QuestionFactory();
        System.out.println(qf.createQuestion());
        System.out.println(qf.createQuestion());
        System.out.println(qf.createQuestion());
        qf.cleanUp();
    }
}
