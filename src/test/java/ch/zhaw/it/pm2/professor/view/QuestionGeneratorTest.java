package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QuestionGeneratorTest {
    QuestionGenerator questionGenerator;
    DeterministicQuestionGenerator deterministicGenerator;

    @Test
    void questionIntTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(false, 3);
        String question = deterministicGenerator.getQuestion('+', 0, 10);
        String answer = deterministicGenerator.getAnswer();
        assertEquals("3 + 3", question);
        assertEquals("6", answer);
    }

    @Test
    void questionDoubleTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(true, 2.3);
        String question = deterministicGenerator.getQuestion('+', 0, 10);
        String answer = deterministicGenerator.getAnswer();
        assertEquals("2.3 + 2.3", question);
        assertEquals("4.6", answer);
    }

    @Test
    void doubleResultIsRoundedTest() {
        deterministicGenerator = new DeterministicQuestionGenerator(true, 2.5);
        String question = deterministicGenerator.getQuestion('+', 0, 10);
        String answer = deterministicGenerator.getAnswer();
        assertEquals("2.5 + 2.5", question);
        assertEquals("5", answer);
        assertNotEquals("5.0", answer);
    }

    @Test
    void isRandomTest() {
        String question = questionGenerator.getQuestion('-', 0, 10);
        String question2 = questionGenerator.getQuestion('-', 0, 10);
        assertNotEquals(question, question2);
    }

    @Test
    void isInRangeTest() {
        String question = questionGenerator.getQuestion('-', 0, 100);
        String[] split = question.split(" ");
        int num1 = Integer.parseInt(split[0]);
        int num2 = Integer.parseInt(split[2]);
        assert (num1 >= 0);
        assert (num1 <= 100);
        assert (num2 >= 0);
        assert (num2 <= 100);
    }

    @Test
    void correctResultTest() {
        String question = questionGenerator.getQuestion('*', 0, 100);
        String answer = questionGenerator.getAnswer();
        String[] split = question.split(" ");
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
        assertEquals(actualMessage, expectedMessage);
    }
}
