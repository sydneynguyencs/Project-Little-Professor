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
    int time = 10;
    boolean started = false;
    //int currentLevel = 1;

    public Game() throws IOException {
        this.house = new House();
        this.display = new CliDisplay();
        this.parser = new Parser();
        this.userIo = new UserIo();
        levelSource = new LevelFactory();
        currentLevel = levelSource.getLevels().get(0); //erstes Level aus der Liste
    }

    @Override
    public void run() {
        update();
    }

    private void update() {
        if (this.started) {
            this.time--;
            this.house.setTime(this.time);
            this.display.showHouse(this.house);
        }
    }

    public void start() throws IOException, UserIo.InvalidFileException {
        this.display.showHouse(this.house);
        this.display.welcomeMessage(house);
        String username = display.requestUsername();
        this.house.changeState(House.State.HALLWAY);
        this.user = userIo.load(username);
        this.house.setUsername(username);
        this.house.setHighscore(user.getHighscore());
        this.house.setScore(user.getScore());
        this.house.setTime(this.time);
        this.house.setLevel(currentLevel);
        this.display.showHouse(this.house, currentLevel);
        this.started = true;
        doNextMove(currentLevel);
    }

    private void doNextMove(Level currentLevel) {
        Config.Command command = this.display.navigate(currentLevel); //
        switch(command) {
            case HELP:
                this.display.helpMessage();
                doNextMove(currentLevel);
            case QUIT:
                this.display.quitMessage();
            default:
                moveUser(command);
        }
    }

    private void moveUser(Config.Command command) {
        this.display.selectedRoomMessage(command);
        //put user into room and print room
        this.display.showRoom(command.getRoom());
        //todo: start question set
        this.display.askQuestionsMessage();
        //todo: print updated house (with completed room)
        //room enum complete/not available ROOM_LOOK_COMPLETED addHouse() adaptieren
        //can be done in doNextMove()
        this.display.showHouse(house, currentLevel);
        doNextMove(currentLevel);
    }

}