package ch.zhaw.it.pm2.professor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class House {
    String house;


    public String loadHouse(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        StringBuilder emptyHouse = new StringBuilder();
        if (!file.canRead() || !file.isFile()) {
            throw new FileNotFoundException();
        }
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(fileName));
            String row;
            while ((row = in.readLine()) != null) {
                emptyHouse.append(row)
                          .append("\n");
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

        return emptyHouse.toString();
    }

    public void setUsername(String name) {
        while (name.length() < Config.MAX_CHARS_USERNAME) {
            name = name + " ";
        }
        house = house.replace("______________", name);
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
