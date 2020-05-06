package ch.zhaw.it.pm2.prefessor;

import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;
import ch.zhaw.it.pm2.professor.model.Question;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class QuestionGeneratorTest {
    QuestionGenerator questionGenerator;
    DeterministicQuestionGenerator deterministicGenerator;


    @BeforeEach
    void setUp() throws Exception {
        questionGenerator = new QuestionGenerator();
    }

    @Test
    void questionIntTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(3);
        Question question = deterministicGenerator.getQuestion('+', 0, 10);
        assertEquals("3 + 3", question.getQuestion());
        assertEquals("6", question.getAnswer());
    }

    @Test
    void questionDoubleTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(2.3);
        Question question = deterministicGenerator.getQuestion('+', 0D, 10D);
        assertEquals("2.3 + 2.3", question.getQuestion());
        assertEquals("4.6", question.getAnswer());
    }

    @Test
    void doubleResultIsRoundedTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(2.5);
        Question question = deterministicGenerator.getQuestion('+', 0D, 10D);
        assertEquals("2.5 + 2.5", question.getQuestion());
        assertEquals("5", question.getAnswer());
        assertNotEquals("5.0", question.getAnswer());
    }

    @Test
    void isRandomTest() {
        Question question = questionGenerator.getQuestion('-', 0, 10);
        String task = question.getQuestion();
        String answer = question.getAnswer();
        questionGenerator.getQuestion('-', 0, 10);
        assertNotEquals(task, question.getAnswer());
        assertNotEquals(answer, question.getQuestion());
    }

    @Test
    void isInRangeTest() {
        Question question = questionGenerator.getQuestion('-', 0, 100);
        String task = question.getQuestion();
        String[] split = task.split(" ");
        int num1 = Integer.parseInt(split[0]);
        int num2 = Integer.parseInt(split[2]);
        assert (num1 > 0);
        assert (num1 < 100);
        assert (num2 > 0);
        assert (num2 < 100);
    }

    @Test
    void correctResultTest() {
        Question question = questionGenerator.getQuestion('*', 0, 100);
        String task = question.getQuestion();
        String answer = question.getAnswer();
        String[] split = task.split(" ");
        int num1 = Integer.parseInt(split[0]);
        int num2 = Integer.parseInt(split[2]);
        int expectedResult = num1 * num2;
        int actualResult = Integer.parseInt(answer);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void invalidOperation() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            questionGenerator.getQuestion('&', 0, 100);
        });
        String expectedMessage = "Operation is invalid";
        String actualMessage = exception.getMessage();
        assertEquals(actualMessage,expectedMessage);
    }
}
