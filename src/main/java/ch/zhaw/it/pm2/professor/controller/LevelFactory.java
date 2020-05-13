package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.model.Level;
import ch.zhaw.it.pm2.professor.model.Room;

import java.util.ArrayList;
import java.util.List;

public class LevelFactory implements LevelSource {
    private List<Level> levelList;

    public LevelFactory() {
        this.levelList = new ArrayList<>();

        Room[] roomsAdditionSubtraction = {Room.HALLWAY, Room.ROOM_LEFT, Room.ROOM_UP};
        Room[] roomsMultiplicationDivision = {Room.HALLWAY, Room.ROOM_RIGHT, Room.ROOM_DOWN};
        Room[] roomsAll = {Room.HALLWAY, Room.ROOM_LEFT, Room.ROOM_RIGHT, Room.ROOM_UP, Room.ROOM_DOWN};

        levelList.add(new Level("1", Difficulty.BEGINNER, roomsAdditionSubtraction));
        levelList.add(new Level("2", Difficulty.INTERMEDIATE, roomsAdditionSubtraction));
        levelList.add(new Level("3", Difficulty.BEGINNER, roomsMultiplicationDivision));
        levelList.add(new Level("4", Difficulty.INTERMEDIATE, roomsMultiplicationDivision));
        levelList.add(new Level("5", Difficulty.INTERMEDIATE, roomsAll));
        levelList.add(new Level("6", Difficulty.ADVANCED, roomsAll));
    }

    @Override
    public List<Level> getLevels() {
        return levelList;
    }

    public enum Difficulty {
        BEGINNER(0, 10, false),
        INTERMEDIATE(0, 20, true),
        ADVANCED(-20, 20, true);

        private int lowerbound;
        private int upperbound;
        private boolean hasDoubleNumbers;

        Difficulty(int lowerbound, int upperbound, boolean hasDoubleNumbers) {
            this.lowerbound = lowerbound;
            this.upperbound = upperbound;
            this.hasDoubleNumbers = hasDoubleNumbers;
        }

        public int getLowerbound() {
            return lowerbound;
        }

        public int getUpperbound() {
            return upperbound;
        }

        public boolean hasDoubleNumbers() {
            return hasDoubleNumbers;
        }
    }
}