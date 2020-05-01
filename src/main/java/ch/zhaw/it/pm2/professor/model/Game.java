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
    Parser parser;
    int time = 0;
    boolean running = false;

    public Game() {
        display = new CliDisplay();
        house = new House();
    }

    @Override
    public void run() {
        if (!this.running) {
            startGame();
        } else {
            update();
        }
    }

    private void update() {
        time++;
        System.out.println("Update the Game (time: " + this.time + ")");
    }

    public void startGame() {
        display.welcomeMessage(house);
        display.requestUsername(house);
        display.seeHouse(house);
        this.running = true;
    }

}