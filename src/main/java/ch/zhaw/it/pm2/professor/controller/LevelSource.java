package ch.zhaw.it.pm2.professor.controller;

import java.util.List;

public class LevelSource implements LevelFactory {
    public static final int LEVEL_ONE = 1;
    public static final int LEVEL_TWO = 2;
    public static final int LEVEL_THREE = 3;
    public static final int LEVEL_FOUR = 4;

    // in UI: gameMode = Game.LEVEL_ONE


    public LevelSource() {};

    @Override
    public List<Level> getLevels() {
        return null;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getTimer() {
        return 0;
    }

    @Override
    public int getQuestionType() {
        return 0;
    }


}
