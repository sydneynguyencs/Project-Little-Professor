package ch.zhaw.it.pm2.professor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

public class House {
    private String[] house;
    private State state;
    private TimeInterface timeSource;

    private static final String USER_FIELD = "%USER________%";
    private static final String TIME_FIELD = "%TIME%";
    private static final String HIGHSCORE_FIELD = "%HIGHSCORE%";
    private static final String SCORE_FIELD = "%SCORE%";
    private static final String LEVEL_FIELD = "%LEVEL%";
    private static final int LINES_EMPTYHOUSE = 21;

    public House(TimeInterface timeSource) throws IOException {
        this.state = State.ENTRANCE;
        this.timeSource = timeSource;
        init();
    }

    public State getState() {
        return this.state;
    }

    public void init() throws IOException {
        this.house = new String[LINES_EMPTYHOUSE];
        File file = new File(this.state.getFilePath());
        if (!file.canRead() || !file.isFile()) {
            throw new FileNotFoundException("The file can not be found in your system." +
                    "Please check if the State-files exists on your computer.");
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

    public String printLevel(Level level) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : printLevelAsArray(level)) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String[] printLevelAsArray(Level level) {
        if (this.state == State.HALLWAY) {
            setTimeInMatrix(getTime());
            addRoomsToMatrix(level);
        }
        return this.house;
    }

    private void addRoomsToMatrix(Level level) {
        for(Room room : level.getRooms()) {
            room.addToHouse(this.house);
        }
    }

    public void setUsername(String name) {
        replaceField(USER_FIELD, name);
    }

    public void setTime(int time) {
        replaceField(TIME_FIELD, Integer.valueOf(time).toString());
    }

    private void setTimeInMatrix(int time) {
        this.replaceField(TIME_FIELD, String.valueOf(time));
    }

    private int getTime() {
        if (this.timeSource == null) {
            throw new NullPointerException();
        }
        try {
            return this.timeSource.getTime();
        } catch (Exception e) {
            throw new RuntimeException("This should not be possible.");
        }
    }

    public void setHighscore(int highscore) {
        this.replaceField(HIGHSCORE_FIELD, String.valueOf(highscore));
    }

    public void setScore(int score) {
        this.replaceField(SCORE_FIELD, String.valueOf(score));
    }

    public void setLevel(Level level) {
        this.replaceField(LEVEL_FIELD, level.getName());
    }

    public void replaceField(String field, String value) {
        for (int i = 0; i < this.house.length; i++) {
            while (value.length() < field.length()) {
                value = value + " ";
            }
            house[i] = house[i].replace(field, value);
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

    public interface TimeInterface {
        int getTime();
    }
}