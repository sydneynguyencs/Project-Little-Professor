package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.controller.LevelFactory;
import ch.zhaw.it.pm2.professor.controller.LevelSource;
import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.view.CliDisplay;
import ch.zhaw.it.pm2.professor.view.Display;
import ch.zhaw.it.pm2.professor.view.User;
import ch.zhaw.it.pm2.professor.view.UserIo;

import java.io.IOException;
import java.util.TimerTask;

public class Game extends TimerTask {
    House house;
    Display display;
    User user;
    Parser parser;
    UserIo userIo;
    Level currentLevel;
    LevelSource levelSource;
    int levelCount = 0;
    int time = 10;
    boolean started = false;
    String username;

    public Game() throws IOException {
        this.house = new House();
        this.display = new CliDisplay();
        this.parser = new Parser();
        this.userIo = new UserIo();
        levelSource = new LevelFactory();
        currentLevel = levelSource.getLevels().get(levelCount); //erstes Level aus der Liste
    }

    @Override
    public void run() {
        update();
    }

    private void update() {
        if (this.started) {
            this.time--;
            this.house.setTime(this.time);
            this.display.showHouse(this.house, currentLevel);
        }
    }

    public void start() throws IOException, UserIo.InvalidFileException {
        this.display.showHouse(this.house, currentLevel);
        this.display.welcomeMessage(house);
        username = display.requestUsername();
        this.user = userIo.load(username);
        updateHouse();
        this.started = true;
        doUserCommand();
    }

    private void updateHouse() throws IOException {
        this.house.changeState(House.State.HALLWAY);
        this.house.setUsername(username);
        this.house.setHighscore(user.getHighscore());
        this.house.setScore(user.getScore());
        this.house.setTime(this.time);
        this.house.setLevel(currentLevel);
    }

    private void doUserCommand() throws IOException {
        updateHouse();
        this.display.showHouse(this.house, currentLevel);
        this.house.changeState(House.State.HALLWAY);

        Config.Command command = this.display.navigate(currentLevel);
        if(command == null) {
            doUserCommand();
        }
        switch(command) {
            case HELP:
                this.display.helpMessage();
                doUserCommand();
            case QUIT:
                this.display.quitMessage();
            default:
                moveIntoRoom(command);
        }
    }

    private void moveIntoRoom(Config.Command command) throws IOException {
        Room room = null;
        for(int i = 0; i < currentLevel.getRooms().length; i++) {
            if(currentLevel.getRooms()[i].getCommand() == command) {
                room = currentLevel.getRooms()[i];
            }
        }
        assert false;

        this.display.selectedRoomMessage(room, currentLevel);
        this.display.showRoom(room, currentLevel);
        startQuestionSet(room);

        //mark room as completed

        //if all rooms of current level completed && !timeUp: go to next level currentLevel++
        updateLevel();
        doUserCommand();
    }

    private void updateLevel() throws IOException {
        levelCount++;
        currentLevel = levelSource.getLevels().get(levelCount);
        this.display.updateLevelMessage(currentLevel);
    }

    private void startQuestionSet(Room room) {
        this.display.askQuestionsMessage();
        //update points
    }

}