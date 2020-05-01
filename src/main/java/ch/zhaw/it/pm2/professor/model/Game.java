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
    int time = 0;

    public Game() {
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
        this.time++;
        System.out.println("Update the Game (time: " + this.time + ")");
    }

    public void start() throws IOException, UserIo.InvalidFileException {
        this.display.welcomeMessage(house);
        this.user = userIo.load(display.requestUsername(house));
        this.display.seeHouse(house);
    }

}