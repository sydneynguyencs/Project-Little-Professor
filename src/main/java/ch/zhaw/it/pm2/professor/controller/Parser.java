package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;

import java.util.Iterator;
import java.util.List;

import static ch.zhaw.it.pm2.professor.model.Config.MAX_CHARS_USERNAME;

import java.util.Scanner;

/**
 * The class Parser creates a DisplayIO. It reads the user input and transfers it into a command. The class
 * itself returns a Config.Command object.
 */
public class Parser {
    private Scanner scanner;
    public Parser() {
        scanner = new Scanner(System.in);
    }

    public Config.Command parseInput(List<Config.Command> acceptedCommands, String input) throws InvalidInputException {
        String command = "not found yet";
        boolean commandInList = false;
            Iterator iterator = acceptedCommands.iterator();
            while (iterator.hasNext()) {
                String temporaryCommand = String.valueOf(iterator.next());
                if (temporaryCommand.equalsIgnoreCase(input)) {
                    command = temporaryCommand;
                    commandInList = true;
                }
            }

        if (!commandInList) {
            throw new InvalidInputException();
        }
        return Config.Command.getCommand(command);
    }

    public String parseName() throws InvalidInputException {
        String input = scanner.next();
        if (input.length() < MAX_CHARS_USERNAME) {
            throw new InvalidInputException();
        }
        return input;
    }
}