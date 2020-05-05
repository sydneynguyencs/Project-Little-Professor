package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;

import java.util.List;

import static ch.zhaw.it.pm2.professor.model.Config.MAX_CHARS_USERNAME;
import static ch.zhaw.it.pm2.professor.model.Config.MIN_CHARS_USERNAME;

/**
 * The class Parser creates a DisplayIO. It reads the user input and transfers it into a command. The class
 * itself returns a Config.Command object.
 */
public class Parser {

    public Config.Command parseInput(List<Config.Command> acceptedCommands, String input) throws InvalidInputException {
        if (input == null) {
            throw new NullPointerException("The input must not be null.");
        }
        for (Config.Command command : acceptedCommands) {
            if (command.toString().equals(input.trim())) {
                return command;
            }
        }
        throw new InvalidInputException("This Input is invalid, please provide a valid command.");
    }

    public String parseName(String input) throws InvalidInputException {
        if (input == null) {
            throw new NullPointerException("The input must not be null.");
        }
        if (input.trim().length() < MIN_CHARS_USERNAME) {
            throw new InvalidInputException("Please choose at least 4 chars for your username.");
        }
        if (input.trim().length() > MAX_CHARS_USERNAME) {
            throw new InvalidInputException("The username has to many chars, please provide an username " +
                    "with 14 chars maximum.");
        }
        return input.trim();
    }
}