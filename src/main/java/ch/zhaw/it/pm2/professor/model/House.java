package ch.zhaw.it.pm2.professor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class House {
    String[] house;

    public void loadHouse(String fileName) throws FileNotFoundException {
        this.house = new String[17];
        File file = new File(fileName);
        if (!file.canRead() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String row;
            int line = 0;
            while ((row = in.readLine()) != null) {
                this.house[line] = row;
                line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

    public String toString() {
        return toString(true);
    }

    public String toString(boolean withRooms) {
        if (withRooms) {
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
}
