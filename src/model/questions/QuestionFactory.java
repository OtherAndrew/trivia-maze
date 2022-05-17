package model.questions;

import java.sql.*;
import java.util.*;

import static model.questions.Type.*;

public class QuestionFactory {
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
            myConnection = DriverManager.getConnection("jdbc:sqlite:Questions"
                    + ".db");
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
        final boolean multipleCorrect = theTable == SA;

        try (final ResultSet rs = myStatement.executeQuery("SELECT * FROM " +
                theTable + " WHERE qid IS NOT NULL ORDER BY RANDOM() LIMIT 1")) {
            final String query = rs.getString("ques");

            final LinkedList<Answer> choices = new LinkedList<>();
            choices.push(new Answer(rs.getString("ansc"), true));
            final int colCount = rs.getMetaData().getColumnCount();
            for (int i = 4; i <= colCount; i++) {
                final String choice = rs.getString(i);
                if (choice != null) choices.push(new Answer(choice, multipleCorrect));
            }
            Collections.shuffle(choices);

            if (multipleCorrect) question = new ShortAnswer(query, choices);
            else question = new ChoiceSelect(query, choices);

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
