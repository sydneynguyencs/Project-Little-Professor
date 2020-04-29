package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;

import java.util.Iterator;
import java.util.List;

/**
 * The class Parser creates a DisplayIO. It reads the user input and transfers it into a command. The class
 * itself returns a Config.Command object.
 */
public class Parser {

    public Parser() {
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
}