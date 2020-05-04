package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.model.House;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;


/**
 * Prints the output to the terminal.
 * The class Display could be replaced by a GUI if wanted.
 * <p>
 * All the methods in this class are getting called from another class, so this class only represents the IO.
 */
public class CliDisplay implements Display {
    TextIO textIO;
    TextTerminal<?> terminal;
    Parser parser;

    /**
     * Constructor of the class DisplayIO. It initializes the Terminal, TextIO and a Config-Object.
     */
    public CliDisplay() {
        textIO = TextIoFactory.getTextIO();
        terminal = textIO.getTextTerminal();
        this.parser = new Parser();
    }

    public void messageUserForInput() {
        terminal.println("Please choose a valid input.");
    }

    public void welcomeMessage(House house) {
        try {
            house.loadHouse("house/entrance.txt");
            terminal.println(house.toString(false)); // toString(false) shouldn't be needed instead an entrance-class would be cool
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        terminal.println("The little Professor will help you to train your math skills while playing.");
    }

    public String requestUsername(House house) {
        try {
            house.loadHouse("house/empty-house.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        terminal.println("Please enter your username.\nBetween 1 - 14 characters");
        String username = this.textIO.newStringInputReader().read();
        try {
            parser.parseName(username);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        house.setUsername(username);
        return username;
    }

    public void seeHouse(House house) {
        terminal.println(house.toString());
    }

    public void seeTheHighscores() {
        terminal.print("To see the highscores from all the users, please press Y.");
    }

    public void displayHighscores() {
        terminal.print("This are the highscores.");
    }

    public void navigate() {
        boolean ok = false;
        while (!ok) {
            terminal.print("To navigate inside the house, user the following keys:\n\n" +
                    "W for UP\n" +
                    "A for Left *** D for Right\n" +
                    "X for Down");
            String input = "bla";
            try {
                this.parser.parseInput(Arrays.asList(Config.Command.values()), getNextUserInput());
                ok = true;
            } catch (InvalidInputException e) {
                invalidInputMessage();
            }
        }
    }

    public String getCommandSelectionString(List<Config.Command> commands) {
        return null;
    }

    public void invalidInputMessage() {
        // print invalid input message
    }

    public void timeIsUp() {
        terminal.print("The time is up.\nYour score will be written to the highscore file and the game " +
                "will end here.");
    }

    public void levelComplete() {
        terminal.print("Congratulations, you completed this level.\nWould you like to go to the next level " +
                "or would you like to exit the game?\nPlease choose N for next level or E for exit.");
    }

    public String getNextUserInput() {
        return textIO.newStringInputReader().read();
    }

    public void printPromt(String promt) {
        terminal.print(promt);
    }
}
