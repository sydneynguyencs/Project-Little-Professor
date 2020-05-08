package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.model.Question;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class QuestionGenerator {
    static Question question;
    ScriptEngineManager sem;
    ScriptEngine engine;
    final int PLACES = 2;
    boolean hasDouble;

    public QuestionGenerator(boolean hasDouble) {
        question = new Question();
        sem = new ScriptEngineManager();
        engine = sem.getEngineByName("JavaScript");
        this.hasDouble = hasDouble;
    }

    protected int getRandomInt(int start, int end) {
        return start + (int) (new Random().nextFloat() * (end - start));
    }

    protected Double getRandomDouble(int start, int end) {
        double randomDouble = start + new Random().nextDouble() * (end - start);
        BigDecimal bd = BigDecimal.valueOf(randomDouble);
        bd = bd.setScale(PLACES, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public String getQuestion(char operation, int lowerBound, int upperBound) {
        if(!checkOperator(operation)){
            throw new IllegalArgumentException("Operation is invalid");
        }
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
            question.setAnswer((Double) engine.eval(question.getQuestion()));
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return question.getQuestion();
    }

    protected boolean checkOperator(char operation) {
        if (operation =='+'||operation =='-'||operation =='*'||operation =='/'){
            return true;
        }
        return false;
    }
}