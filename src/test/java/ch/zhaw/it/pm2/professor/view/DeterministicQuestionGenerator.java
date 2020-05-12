package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.LevelFactory;
import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;
import javax.script.ScriptException;

/**
 * This class extends the QuestionGenerator and overrides it's random methods, so that it can be tested.
 */
public class DeterministicQuestionGenerator extends QuestionGenerator {
    private int intNumber;
    private double doubleNumber;

    /**
     * This constructor allows a specific integer number to be saved for later on the random number.
     *
     * @param intNumber to be used as the value of the random number
     * @param hasDouble will the questions include double numbers
     */
    public DeterministicQuestionGenerator(boolean hasDouble, int intNumber, LevelFactory.Difficulty difficulty) {
        super(hasDouble, difficulty);
        this.intNumber = intNumber;
    }

    /**
     * This constructor allows a specific integer number to be saved for later on the random number.
     *
     * @param doubleNumber to be used as the value of the random number
     * @param hasDouble    will the questions include double numbers
     */
    public DeterministicQuestionGenerator(boolean hasDouble, double doubleNumber, LevelFactory.Difficulty difficulty) {
        super(hasDouble, difficulty);
        this.doubleNumber = doubleNumber;
    }

    /**
     * Overrides the method, so that the given integer number from the constructor is returned
     *
     * @param start of the range
     * @param end   of the range
     * @return intNumber specified
     */
    @Override
    protected int getRandomInt(int start, int end) {
        return intNumber;
    }

    /**
     * Overrides the method, so that the given double number from the constructor is returned
     *
     * @param start of the range
     * @param end   of the range
     * @return doubleNumber specified
     */
    @Override
    protected double getRandomDouble(int start, int end) {
        return doubleNumber;
    }

    /**
     * Generate a question with the given operation and range. If the generator can use doubles, then questions will
     * be with double numbers.
     *
     * @param operation  for the question, valid are +, -, * and /
     * @param lowerBound of the range
     * @param upperBound of the range
     * @return question as a String
     * @throws IllegalArgumentException if the given operation is invalid
     */
    @Override
    public String getQuestion(String operation, int lowerBound, int upperBound) {
        int num1, num2;
        double num3, num4;
        if (!checkOperator(operation)) {
            throw new IllegalArgumentException("Operation is invalid");
        }
        int choose = 0;
        //if doubles are allowed questions will only have double numbers
        if (hasDouble) {
            choose = 1;
        }
        switch (choose) {
            case 0:
                do {
                    num1 = getRandomInt(lowerBound, upperBound);
                    num2 = getRandomInt(lowerBound, upperBound);
                    //check if the result of a subtraction is negative
                } while (!subtractionCheckForBeginner(num1, num2, operation) || !divisionCheck(num1, num2, operation));
                question.setQuestion(num1 + " " + operation + " " + num2);
                break;
            case 1:
                do {
                    num3 = getRandomDouble(lowerBound, upperBound);
                    num4 = getRandomDouble(lowerBound, upperBound);
                } while (!divisionCheck(num3, num4, operation));
                question.setQuestion(num3 + " " + operation + " " + num4);
        }
        try {
            String answer = engine.eval(question.getQuestion()).toString();
            //if the answer of a question with double numbers result in a full number, then the ending will be removed
            if (answer.endsWith(".0")) {
                answer = answer.replace(".0", "");
            }
            question.setAnswer(answer);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return question.getQuestion();
    }
}