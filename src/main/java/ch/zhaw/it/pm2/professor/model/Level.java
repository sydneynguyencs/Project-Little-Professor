package ch.zhaw.it.pm2.professor.model;

public class Level {

    private int name;
    private int[] domain;
    private Room[] rooms;

    public Level(int name, int[] domain, Room[] rooms) {
        this.name = name;
        this.domain = domain;
        this.rooms = rooms;
    }

    public int getName() {
        return name;
    }

    public int[] getDomain() {
        return domain;
    }

    public Room[] getRooms() {
        return rooms;
    }

}