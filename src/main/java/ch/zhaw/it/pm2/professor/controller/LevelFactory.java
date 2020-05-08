package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.model.Level;
import ch.zhaw.it.pm2.professor.model.Room;

import java.util.ArrayList;
import java.util.List;

public class LevelFactory implements LevelSource {
    private List<Level> levelList;

    public LevelFactory() {
        this.levelList = new ArrayList<>();
        int[] domainBeginner = {0, 10};
        int[] domainIntermediate = {10, 100};
        int[] domainAdvanced = {0, 1000};

        Room[] roomsAdditionSubtraction = {Room.HALLWAY, Room.ROOM_LEFT, Room.ROOM_UP};
        Room[] roomsMultiplicationDivision = {Room.HALLWAY, Room.ROOM_RIGHT, Room.ROOM_DOWN};
        Room[] roomsAll = {Room.HALLWAY, Room.ROOM_LEFT, Room.ROOM_RIGHT, Room.ROOM_UP, Room.ROOM_DOWN};

        levelList.add(new Level("1", domainBeginner, roomsAdditionSubtraction));
        levelList.add(new Level("2", domainIntermediate, roomsAdditionSubtraction));
        levelList.add(new Level("3", domainBeginner, roomsMultiplicationDivision));
        levelList.add(new Level("4", domainIntermediate, roomsMultiplicationDivision));
        levelList.add(new Level("5", domainIntermediate, roomsAll));
        levelList.add(new Level("6", domainAdvanced, roomsAll));
    }

    @Override
    public List<Level> getLevels() {
        return levelList;
    }
}