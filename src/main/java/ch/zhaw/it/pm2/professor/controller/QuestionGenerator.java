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

    public QuestionGenerator(){
        question = new Question();
        sem = new ScriptEngineManager();
        engine = sem.getEngineByName("JavaScript");
    }

    protected int getRandomInt(int start, int end) {
        return start + (int) (new Random().nextFloat() * (end - start));
    }

    protected double getRandomDouble(double start, double end) {
        double randomDouble = start + new Random().nextDouble() * (end - start);
        BigDecimal bd = BigDecimal.valueOf(randomDouble);
        bd = bd.setScale(PLACES, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public Question getQuestion(char operation, int lowerBound, int upperBound) {
        if(!checkOperator(operation)){
            throw new IllegalArgumentException("Operation is invalid");
        }
        int num1 = getRandomInt(lowerBound, upperBound);
        int num2 = getRandomInt(lowerBound, upperBound);
        question.setQuestion(num1 + " " + operation + " " + num2);
        try {
            question.setAnswer( engine.eval(question.getQuestion()).toString());
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return question;
    }

    public Question getQuestion(char operation, double lowerBound, double upperBound) {
        if(!checkOperator(operation)){
            throw new IllegalArgumentException("Operation is invalid");
        }
        double num1 = getRandomDouble(lowerBound, upperBound);
        double num2 = getRandomDouble(lowerBound, upperBound);
        question.setQuestion(num1 + " " + operation + " " + num2);
        try {
            String answer = engine.eval(question.getQuestion()).toString();
            if(answer.endsWith(".0")){
                answer=answer.replace(".0","");
            }
            question.setAnswer( answer);
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return question;
    }

    protected boolean checkOperator(char operation) {
        if (operation =='+'||operation =='-'||operation =='*'||operation =='/'){
            return true;
        }
        return false;
    }
}