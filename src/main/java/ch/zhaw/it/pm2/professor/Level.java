package ch.zhaw.it.pm2.professor;

import ch.zhaw.it.pm2.professor.model.Room;

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

}