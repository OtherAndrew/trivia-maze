package model.questions;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuestionFactoryTest {

    private QuestionFactory myQF;

    @BeforeEach
    void setUp() {
        myQF = new QuestionFactory();
    }

    @AfterEach
    void tearDown() {
        myQF.cleanUp();
    }

    @Test
    void createQuestion() {
        for (int qid = 1; qid < 111; qid++) {
            final Question question = myQF.createQuestion();
            assertNotNull(question);
            assertNotEquals("", question.getQuery());
            final List<String> answers = question.getAnswers();
            assertNotNull(answers);
            for (String answer : answers) assertNotEquals("", answer);
        }
        final Question question = myQF.createQuestion();
        assertNull(question);
    }

    @Test
    void cleanUp() {
        myQF.cleanUp();
        assertNull(myQF.createQuestion());
        assertDoesNotThrow(() -> myQF.cleanUp());
    }
}