package model.questions;

import java.io.Serializable;
import java.sql.Connection;

public class ShortAnswer extends Question implements Serializable {

    /**
     * Constructs a Question object from a query and a set of answers. The
     * resulting set of answers in the Question will be a shuffled version
     * of the set provided in arguments.
     *
     * @param theDB a connection to a SQLite database.
     */
    public ShortAnswer(final Connection theDB) {
        super(theDB, "SA");
    }

    /**
     * Checks to see if the answer to a short answer question is true.
     * @param theResponse a response submitted by a player.
     * @return if the response is correct.
     */
    @Override
    public boolean checkAnswer(final String theResponse) {
        boolean result = false;
        for(Answer choice : myAnswers.values()) {
            if (choice.toString().equals(theResponse)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
