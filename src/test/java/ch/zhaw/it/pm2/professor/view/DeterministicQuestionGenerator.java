package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;

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
}
