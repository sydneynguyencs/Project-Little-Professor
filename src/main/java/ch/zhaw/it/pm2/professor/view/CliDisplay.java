package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.model.House;
import ch.zhaw.it.pm2.professor.model.Room;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Arrays;
import java.util.List;


/**
 * Prints the output to the terminal.
 * The class Display could be replaced by a GUI if wanted.
 * <p>
 * All the methods in this class are getting called from another class, so this class only represents the IO.
 */
public class CliDisplay implements Display {
    private TextIO textIO;
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

    public void showHouse(House house) {
        terminal.println(house.toString()); // toString(false) shouldn't be needed instead an entrance-class would be cool
    }

    public void welcomeMessage(House house) {
        terminal.println("The little Professor will help you to train your math skills while playing.");
    }

    public String requestUsername() {
        terminal.println("Please enter your username.\nBetween 1 - 14 characters");
        String username = this.textIO.newStringInputReader().read();
        try {
            parser.parseName(username);
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
        return username;
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

    @Override
    public Config.Command requestCommand() throws InvalidInputException {
        terminal.println("Your are in the Hallway right now. Type any of the following commands to enter a room.\n");
        terminal.println("LEFT: left\nUP: up\nRIGHT: right\nDOWN: down\nHELP: help\nQUIT: quit\n");
        Config.Command command = parser.parseInput(Config.Command.getCommandList(), this.textIO.newStringInputReader().read());
        return command;
        //edge case: in case of an invalid command. does game stop?
    }

    @Override
    public void selectedRoomMessage(Config.Command command) {
        terminal.println("\nYou entered the room with the mission to solve questions of the operation "+ command.getRoom().getOperation().toString() + ".\nFinish before the time runs out!");
    }

    @Override
    public void helpMessage() {
        terminal.println("Move into a room to start the question set and gain enough points to win this level.\nWatch out for the timer!\nTo quit Little Professor type \"quit\"");
    }

    @Override
    public void quitMessage() {
        terminal.println("Thanks for playing!\nSee you soon to improve your math skills.");
    }

    @Override
    public void askQuestionsMessage() {
        terminal.println("Solve: ");
        terminal.print("Your answer: ");
        String answer = textIO.newStringInputReader().read();
        terminal.println();
        //return the  answer to check in game class?
    }

    @Override
    public void showRoom(Room room) {
        terminal.println(room.toString());
    }


    public void printPromt(String promt) {
        terminal.print(promt);
    }
}
