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
class ChoiceSelectTest {

    private ChoiceSelect myChoiceSelect;
    private String myQuestion;
    private List<String> myExpected;

    @BeforeEach
    void setUp() {
        myQuestion = "question";

        LinkedList<Answer> answers = new LinkedList<>();
        answers.add(new Answer("true answer", true));
        answers.add(new Answer("false answer1", false));
        answers.add(new Answer("false answer2", false));
        answers.add(new Answer("false answer3", false));

        myExpected = new ArrayList<>();
        myExpected.add("a. true answer");
        myExpected.add("b. false answer1");
        myExpected.add("c. false answer2");
        myExpected.add("d. false answer3");

        myChoiceSelect = new ChoiceSelect(myQuestion, answers);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getQuery() {
        assertEquals(myQuestion, myChoiceSelect.getQuery());
    }

    @Test
    void validChoice() {
        assertFalse(myChoiceSelect.checkAnswer("not an option"));
    }

    @Test
    void checkAnswer() {
        assertTrue(myChoiceSelect.checkAnswer("a"));
        assertFalse(myChoiceSelect.checkAnswer("b"));
        assertFalse(myChoiceSelect.checkAnswer("c"));
        assertFalse(myChoiceSelect.checkAnswer("d"));
    }

    @Test
    void getAnswers() {
        assertEquals(myExpected, myChoiceSelect.getAnswers());
    }
}