package ch.zhaw.it.pm2.professor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LevelFactory implements LevelSource {

    private static final int NUMBER_OF_LEVELS = 3;
    private List<Level> levelList;

    public LevelFactory() {
        this.levelList = new ArrayList<>();
        levelList.addAll(Arrays.asList(Level.values()).subList(0, NUMBER_OF_LEVELS));
    }

    /**
     * Representation for all Levels for the game.
     */
    public enum Level {
        BEGINNER("Beginner", 10),
        INTERMEDIATE("Intermediate", 100),
        ADVANCED("Advanced", 1000);

        private String level;
        private int domain;

        /**
         * Initialize with according level.
         * @param level   the level as String.
         */
        Level(String level, int domain) {
            this.level = level;
            this.domain = domain;
        }

        /**
         * @return  the level as String
         */
        @Override
        public String toString() {
            return level;
        }

        /**
         * Upper domain for questions difficulty
         * Returns the number domain that this level provides.
         * (Bsp. Additionen mit Ergebnissen [0, 10] in Level 1)
         *
         * @return the domain
         */
        public int getDomain() {
            return domain;
        }
    }

    @Override
    public List<Level> getLevels() {
        return levelList;
    }

}
