package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;

import java.util.List;

import static ch.zhaw.it.pm2.professor.model.Config.MAX_CHARS_USERNAME;

/**
 * The class Parser creates a DisplayIO. It reads the user input and transfers it into a command. The class
 * itself returns a Config.Command object.
 */
public class Parser {

    public Config.Command parseInput(List<Config.Command> acceptedCommands, String input) throws InvalidInputException {
        for (Config.Command command : acceptedCommands) {
            if (command.toString().equals(input)) {
                return command;
            }
        }
        throw new InvalidInputException("This Input is invalid.");
    }

    public String parseName(String input) throws InvalidInputException {
        if (input.length() > MAX_CHARS_USERNAME) {
            throw new InvalidInputException("This Input is invalid.");
        }
        return input;
    }
}