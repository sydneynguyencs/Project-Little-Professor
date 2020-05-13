package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.model.Level;

import java.util.List;

public interface LevelSource {

    /**
     * This method gets all existing levels as a list.
     * @return a list containing all levels
     */
    List<Level> getLevels();

}
