package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.controller.LevelFactory;
import ch.zhaw.it.pm2.professor.controller.QuestionGenerator;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private String name;
    private LevelFactory.Difficulty difficulty;
    private Room[] rooms;
    private QuestionGenerator generator;

    public Level(String name, LevelFactory.Difficulty difficulty, Room[] rooms) {
        this.name = name;
        this.difficulty = difficulty;
        this.rooms = rooms;
        this.generator = new QuestionGenerator(difficulty.hasDoubleNumbers());
    }

    public String getName() {
        return name;
    }

    public LevelFactory.Difficulty getDifficulty() {
        return difficulty;
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

    public String getQuestion(Room room) {
        return generator.getQuestion(room.getOperation().toString().charAt(0), difficulty.getLowerbound(), difficulty.getUpperbound());
    }

    public String getAnwser(Room room) {
        return generator.getAnswer();
    }

}