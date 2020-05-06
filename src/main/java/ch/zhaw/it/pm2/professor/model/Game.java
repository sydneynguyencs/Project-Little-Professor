package ch.zhaw.it.pm2.professor.model;

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
    int time = 10;
    boolean started = false;
    int currentLevel = 1;

    public Game() throws IOException {
        this.house = new House();
        this.display = new CliDisplay();
        this.parser = new Parser();
        this.userIo = new UserIo();
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
        this.house.setTime(this.time);
        this.house.setLevel(currentLevel);
        this.display.showHouse(this.house);
        this.started = true;
    }

}