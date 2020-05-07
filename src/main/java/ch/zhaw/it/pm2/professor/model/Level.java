package ch.zhaw.it.pm2.professor.model;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String name;
    private int[] domain;
    private Room[] rooms;
    private List<Config.Command> validCommandsList;

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
        System.out.println("OK LEVEL Valid Command List");
        validCommandsList = new ArrayList<>();
        validCommandsList.add(Config.Command.HELP);
        validCommandsList.add(Config.Command.QUIT);
        for(int i = 0; i < rooms.length; i++) {
            validCommandsList.add(rooms[i].getCommand());
        }
        return validCommandsList;
    }

}