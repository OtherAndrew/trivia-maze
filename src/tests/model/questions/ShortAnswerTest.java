package model.questions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ShortAnswerTest {

    private String myQuestion;
    private ArrayList<String> myAnswers;
    private ShortAnswer myShortAnswer;

    @BeforeEach
    void setUp() {
        myQuestion = "question";

        myAnswers = new ArrayList<>();
        myAnswers.add("answer1");
        myAnswers.add("answer2");
        myAnswers.add("answer3");
        myAnswers.add("answer4");

        myShortAnswer = new ShortAnswer(myQuestion, myAnswers);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getQuery() {
        assertEquals(myQuestion, myShortAnswer.getQuery());
    }

    @Test
    void checkAnswer() {
        assertFalse(myShortAnswer.checkAnswer("not an answer"));
        assertTrue(myShortAnswer.checkAnswer("answer1"));
        assertTrue(myShortAnswer.checkAnswer("answer2"));
        assertTrue(myShortAnswer.checkAnswer("answer3"));
        assertTrue(myShortAnswer.checkAnswer("answer4"));
    }

    @Test
    void getAnswers() {
        assertEquals(myAnswers, myShortAnswer.getAnswers());
    }
}