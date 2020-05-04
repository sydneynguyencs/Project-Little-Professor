package ch.zhaw.it.pm2.professor;

import ch.zhaw.it.pm2.professor.model.Room;

import java.util.ArrayList;
import java.util.List;

public class LevelFactory implements LevelSource {
    private static final int NUMBER_OF_LEVELS = 6;
    private List<Level> levelList;

    public LevelFactory() {
        this.levelList = new ArrayList<>();
        int[] domainBeginner = {0,10};
        int[] domainIntermediate = {10, 100};
        int[] domainAdvanced = {0, 1000};

        Room[] roomsAdditionSubtraction = {Room.HALLWAY, Room.ROOM_LEFT, Room.ROOM_RIGHT};
        Room[] roomsMultiplicationDivision = {Room.HALLWAY, Room.ROOM_UP, Room.ROOM_DOWN};
        Room[] roomsAll = {Room.HALLWAY, Room.ROOM_LEFT, Room.ROOM_RIGHT, Room.ROOM_UP, Room.ROOM_DOWN};

        levelList.add(new Level("Level 1", domainBeginner, roomsAdditionSubtraction));
        levelList.add(new Level("Level 2", domainIntermediate, roomsAdditionSubtraction));
        levelList.add(new Level("Level 3", domainBeginner, roomsMultiplicationDivision));
        levelList.add(new Level("Level 4", domainIntermediate, roomsMultiplicationDivision));
        levelList.add(new Level("Level 5", domainIntermediate, roomsAll));
        levelList.add(new Level("Level 6", domainAdvanced, roomsAll));
    }

    @Override
    public List<Level> getLevels() {
        return null;
    }
}