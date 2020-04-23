package ch.zhaw.it.pm2.professor;

import java.util.List;

public interface LevelFactory {

    public List<Level> getLevels();

    public int getLevel();
    public int getTimer();
    public int getQuestionType();

}
