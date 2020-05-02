package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.view.CliDisplay;
import ch.zhaw.it.pm2.professor.view.Display;
import ch.zhaw.it.pm2.professor.view.User;

public class Game {
    Display display;
    User user;
    Parser parser;
    House house;

    public Game() {
        display = new CliDisplay();
        parser = new Parser();
        house = new House();
    }

    public void run() {
        startGame();
    }

    public void startGame() {
        display.welcomeMessage(house);
        display.requestUsername(house);
        display.seeHouse(house);

    }

}