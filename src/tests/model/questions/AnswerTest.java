package model.questions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnswerTest {

    private String myTextTrue, myTextFalse;
    private Answer myTrueAnswer, myFalseAnswer;

    @BeforeEach
    void setUp() {
        myTextTrue = "test true";
        myTextFalse = "test false";
        myTrueAnswer = new Answer(myTextTrue, true);
        myFalseAnswer = new Answer(myTextFalse, false);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isCorrect() {
        assertTrue(myTrueAnswer.isCorrect());
        assertFalse(myFalseAnswer.isCorrect());
    }

    @Test
    void testToString() {
        assertEquals(myTextTrue, myTrueAnswer.toString());
        assertEquals(myTextFalse, myFalseAnswer.toString());
    }
}