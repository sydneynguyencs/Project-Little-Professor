package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.model.Question;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * This class creates questions. The operation, range and if it has double numbers, can be defined.
 * The question and answer are saved in a static Question-object once it's generated.
 */
public class QuestionGenerator {
    protected static Question question;
    ScriptEngineManager sem;
    protected ScriptEngine engine;
    final int PLACES = 2;
    protected boolean hasDouble;

    /**
     * Every QuestionGenerator-instance has to be defined if it has double numbers or not.
     *
     * @param hasDouble will the questions include double numbers
     */
    public QuestionGenerator(boolean hasDouble) {
        question = new Question();
        sem = new ScriptEngineManager();
        engine = sem.getEngineByName("JavaScript");
        this.hasDouble = hasDouble;
    }

    /**
     * Receive a random integer number of a given range. The bounds are inclusive.
     *
     * @param start of the range
     * @param end   of the range
     * @return a random integer
     */
    protected int getRandomInt(int start, int end) {
        return start + (int) (new Random().nextFloat() * (end - start));
    }

    /**
     * Receive a random double number of a given range rounded to one decimal place. The bounds are inclusive.
     *
     * @param start of the range
     * @param end   of the range
     * @return a random double
     */
    protected double getRandomDouble(int start, int end) {
        double randomDouble = start + new Random().nextDouble() * (end - start);
        BigDecimal bd = BigDecimal.valueOf(randomDouble);
        bd = bd.setScale(PLACES, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * Generate a question with the given operation and range. If the generator can use doubles, then questions will
     * randomly switch between integers and doubles.
     *
     * @param operation  for the question, valid are +, -, * and /
     * @param lowerBound of the range
     * @param upperBound of the range
     * @return question as a String
     * @throws IllegalArgumentException if the given operation is invalid
     */

    public String getQuestion(char operation, int lowerBound, int upperBound) {
        if (!(operation == '+' || operation == '-' || operation == '*' || operation == '/')) {
            throw new IllegalArgumentException("Operation is invalid");
        }
        //if hasDouble is true then questions will randomly switch between integers and doubles
        int choose = 0;
        if (hasDouble) {
            choose = getRandomInt(0, 1);
        }
        switch (choose) {
            case 0:
                int num1 = getRandomInt(lowerBound, upperBound);
                int num2 = getRandomInt(lowerBound, upperBound);
                question.setQuestion(num1 + " " + operation + " " + num2);
                break;
            case 1:
                double num3 = getRandomDouble(lowerBound, upperBound);
                double num4 = getRandomDouble(lowerBound, upperBound);
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

    /**
     * This method will return the answer of the question as a String.
     *
     * @return answer as a String
     */
    public String getAnswer() {
        return question.getAnswer();
    }
}