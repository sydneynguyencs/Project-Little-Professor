package ch.zhaw.it.pm2.professor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class House {
    private String[] house;
    private State state;

    public House() throws IOException {
        this.state = State.ENTRANCE;
        init();
    }

    public void init() throws IOException {
        this.house = new String[17];
        File file = new File(this.state.getFilePath());
        if (!file.canRead() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(this.state.getFilePath()));
            String row;
            int line = 0;
            while ((row = in.readLine()) != null) {
                this.house[line] = row;
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    public void changeState(State newState) throws IOException {
        this.state = newState;
        init();
    }

    public String toString() {
        if (this.state == State.HALLWAY) {
            addRooms();
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.house.length; i++) {
            stringBuilder.append(this.house[i]);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private void addRooms() {
        for(Room room : Room.values()) {
            room.addToHouse(this.house);
        }
    }

    public void setUsername(String name) {
        for (int i = 0; i < this.house.length; i++) {
            while (name.length() < Config.MAX_CHARS_USERNAME) {
                name = name + " ";
            }
            house[i] = house[i].replace("______________", name);
        }
    }

    public enum State {
        ENTRANCE("./src/main/resources/house/entrance.txt"),
        HALLWAY("./src/main/resources/house/empty-house.txt");

        String filePath;

        State(String filePath) {
            this.filePath = filePath;
        }

        public String getFilePath() {
            return filePath;
        }
    }
}
