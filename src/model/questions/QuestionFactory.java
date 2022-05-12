package model.questions;

import java.sql.Connection;
import java.util.Random;

public class QuestionFactory {
    private static final Random RAND = new Random();
    private static final int TRUEFALSE = 0;
    private static final int MULTICHOICE = 1;
    private static final int SHORTANS = 2;

    public Question createQuestion(final Connection theDB) {
        Question question = null;
        switch (RAND.nextInt(3)) {
            case TRUEFALSE -> question = new ChoiceSelection(theDB, "TF");
            case MULTICHOICE -> question = new ChoiceSelection(theDB, "MC");
            case SHORTANS -> question = new ShortAnswer(theDB);
        }
        return question;
    }
}
