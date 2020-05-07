package ch.zhaw.it.pm2.professor.model;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String name;
    private int[] domain;
    private Room[] rooms;
    private List<Config.Command> commands;

    public Level(String name, int[] domain, Room[] rooms) {
        this.name = name;
        this.domain = domain;
        this.rooms = rooms;
        commands = new ArrayList<>();
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

    public List <Config.Command> getCommands() {
        for(int i = 0; i < rooms.length; i++) {
            commands.add(rooms[i].getCommand());
        }
        return commands; }

}