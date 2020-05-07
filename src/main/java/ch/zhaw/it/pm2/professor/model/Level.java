package ch.zhaw.it.pm2.professor.model;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String name;
    private int[] domain;
    private Room[] rooms;

    public Level(String name, int[] domain, Room[] rooms) {
        this.name = name;
        this.domain = domain;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public int[] getDomain() {
        return domain;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public List<Config.Command> getValidCommandsList() {
        List<Config.Command> validCommandsList = new ArrayList<>();
        for (int i = 1; i < rooms.length; i++) {
            validCommandsList.add(rooms[i].getCommand());
        }
        validCommandsList.add(Config.Command.HELP);
        validCommandsList.add(Config.Command.QUIT);
        return validCommandsList;
    }

}