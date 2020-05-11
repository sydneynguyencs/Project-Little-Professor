package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;
import javax.script.ScriptException;

public class DeterministicQuestionGenerator extends QuestionGenerator {
    private int intNumber;
    private double doubleNumber;

    public DeterministicQuestionGenerator(boolean hasDouble, int intNumber) {
        super(hasDouble);
        this.intNumber = intNumber;
    }

    public DeterministicQuestionGenerator(boolean hasDouble, double doubleNumber) {
        super(hasDouble);
        this.doubleNumber = doubleNumber;
    }

    @Override
    protected int getRandomInt(int start, int end) {
        return intNumber;
    }

    @Override
    protected double getRandomDouble(int start, int end) {
        return doubleNumber;
    }


    public String getQuestion(char operation, int lowerBound, int upperBound) {
        if(!checkOperator(operation)){
            throw new IllegalArgumentException("Operation is invalid");
        }
        int choose=0;
        if (hasDouble) {
            choose = 1;
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
            if(answer.endsWith(".0")){
                answer=answer.replace(".0","");
            }
            question.setAnswer( answer);

        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        return question.getQuestion();
    }
}