package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.view.CliDisplay;
import ch.zhaw.it.pm2.professor.view.Display;
import ch.zhaw.it.pm2.professor.view.User;
import java.util.TimerTask;

public class Game extends TimerTask {
    House house;
    Display display;
    User user;

    public Game() {
        display = new CliDisplay();
        house = new House();
    }

    @Override
    public void run() {
        startGame();
    }

    public void startGame() {
        display.welcomeMessage(house);
        display.requestUsername(house);
        display.seeHouse(house);

    }

}