package ch.zhaw.it.pm2.prefessor;

import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;

public class DeterministicQuestionGenerator extends QuestionGenerator {
private int intNumber;
private double doubleNumber;

    public DeterministicQuestionGenerator(int intNumber){
        this.intNumber = intNumber;
    }

    public DeterministicQuestionGenerator(double doubleNumber){
        this.doubleNumber = doubleNumber;
    }

    @Override
    protected int getRandomInt(int start, int end) {
        return intNumber;
    }

    @Override
    protected double getRandomDouble(double start, double end) {
        return doubleNumber;
    }
}
