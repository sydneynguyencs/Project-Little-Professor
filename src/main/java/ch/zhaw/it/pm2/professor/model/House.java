package ch.zhaw.it.pm2.professor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class House {

    public String getEmptyHouse(String fileName) throws FileNotFoundException {
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
}
