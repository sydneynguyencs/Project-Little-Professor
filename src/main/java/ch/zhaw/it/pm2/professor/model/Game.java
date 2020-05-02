package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.view.CliDisplay;
import ch.zhaw.it.pm2.professor.view.User;

public class Game {
    CliDisplay display;
    User user;
    Parser parser;

    public Game() {
        display = new CliDisplay();
        parser = new Parser();
    }

    public void run() {
        startGame();
    }

    public void startGame() {
        display.welcomeMessage();
        display.requestUsername();
        display.seeHouse();

    }

}