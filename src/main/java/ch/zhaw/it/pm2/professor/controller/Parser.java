package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.view.CliDisplay;

/**
 * The class Parser creates a DisplayIO. It reads the user input and transfers it into a command. The class
 * itself returns a Command object.
 */
public class Parser {
    CliDisplay displayIO;

    public Parser() {
        displayIO = new CliDisplay();
    }

}
