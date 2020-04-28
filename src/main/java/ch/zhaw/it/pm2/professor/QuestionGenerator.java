package ch.zhaw.it.pm2.professor;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;
import java.util.Random;

public class QuestionGenerator {
    static Question question;
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine engine = sem.getEngineByName("JavaScript");

    private int getRandomInt(int start, int end) {
        return start + (int) (new Random().nextFloat() * (end - start));
    }

    private Double getRandomDouble(double start, double end) {
        return start + new Random().nextDouble() * (end - start);
    }

    public void getQuestion(char operation, int lowerBound, int upperBound) {
        int num1 = getRandomInt(lowerBound, upperBound);
        int num2 = getRandomInt(lowerBound, upperBound);
        question.setQuestion(num1 + " " + operation + " " + num2);
        try {
            question.setAnswer((Double) engine.eval(question.getQuestion()));
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }

    public void getQuestion(char operation, double lowerBound, double upperBound) {
        double num1 = getRandomDouble(lowerBound, upperBound);
        double num2 = getRandomDouble(lowerBound, upperBound);
        question.setQuestion(num1 + " " + operation + " " + num2);
        try {
            question.setAnswer((Double) engine.eval(question.getQuestion()));
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
    }

}


