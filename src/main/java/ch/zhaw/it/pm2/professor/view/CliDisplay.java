package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.model.House;
import ch.zhaw.it.pm2.professor.model.Level;
import ch.zhaw.it.pm2.professor.model.Room;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.List;


/**
 * Prints the output to the terminal.
 * The class Display could be replaced by a GUI if wanted.
 * <p>
 * All the methods in this class are getting called from another class, so this class only represents the IO.
 */
public class CliDisplay implements Display {
    private TextIO textIO;
    private TextTerminal<?> terminal;
    private Parser parser;
    private GameEndListener gameEndListener;
    private DebugSuccessListener debugSuccessListener; // is only temporary because the game can't be ended at the moment
    private DebugFailListener debugFailListener; // is only temporary because the game can't be ended at the moment

    /**
     * Constructor of the class DisplayIO. It initializes the Terminal, TextIO and a Config-Object.
     */
    public CliDisplay(GameEndListener gameEndListener, DebugSuccessListener debugSuccessListener, DebugFailListener debugFailListener) {
        this.textIO = TextIoFactory.getTextIO();
        this.terminal = textIO.getTextTerminal();
        this.parser = new Parser();
        this.gameEndListener = gameEndListener;
        this.debugSuccessListener = debugSuccessListener;
        this.debugFailListener = debugFailListener;
    }

    public void messageUserForInput() {
        terminal.println("Please choose a valid input.");
    }

    @Override
    public void showHouse(House house, Level level) {
        terminal.println(house.printLevel(level));
    }

    public void welcomeMessage(House house) {
        terminal.println("The little Professor will help you to train your math skills while playing.");
    }

    public String requestUsername() {
        terminal.println("Please enter your username.\nBetween " + Config.MIN_CHARS_USERNAME + " - " + Config.MAX_CHARS_USERNAME + " characters");
        String username = getNextUserInput();
        try {
            parser.parseName(username);
        } catch (InvalidInputException e) {
            invalidInputMessage();
        }
        return username;
    }

    public void seeTheHighscores() {
        terminal.print("To see the highscores from all the users, please press Y.");
    }

    public void displayHighscores() {
        terminal.print("This are the highscores.");
    }

    public Config.Command navigate(Level level) {
        Config.Command command = null;
        terminal.println("You are in the Hallway right now. Type any of the following commands to enter a room.\n");
        terminal.println("LEFT: left\nUP: up\nRIGHT: right\nDOWN: down\nHELP: help\nQUIT: quit\n");
        String input = getNextUserInput();
        try {
            command = this.parser.parseInput(level.getValidCommandsList(), input.toLowerCase());
        } catch (InvalidInputException e) {
            invalidInputMessage();
        }
        return command;
    }

    public String getCommandSelectionString(List<Config.Command> commands) {
        return null;
    }

    public void invalidInputMessage() {
        terminal.print("The given input is invalid. Please enter one of the proposed commands.\n");
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
        terminal.print("\nEnter \"quit\" to quit.\n");
        String userInput = textIO.newStringInputReader().read();
        checkForQuitCommand(userInput);
        checkForGameEnd(userInput);
        return userInput;
    }

    private void checkForQuitCommand(String userInput) {
        Config.Command[] quitCommandList = {Config.Command.QUIT};
        try {
            this.parser.parseInput(quitCommandList, userInput);
            exitApplication();
        } catch (InvalidInputException e) {
            // so we don't quit
        }
    }

    /**
     * Can be deleted, when game can be finished
     */
    private void checkForGameEnd(String userInput) {
        Config.Command[] successCommandList = {Config.Command.DEBUG_SUCCESS};
        try {
            Config.Command command = this.parser.parseInput(successCommandList, userInput);
            if (command == Config.Command.DEBUG_FAIL) {
                this.debugFailListener.onGameFailed();
            } else if (command == Config.Command.DEBUG_SUCCESS){
                this.debugSuccessListener.onGameSuccess();
            }
        } catch (InvalidInputException e) {
            // so we don't end the game
        }
    }

    /**
     * If this method gets called, the user gets informed that the Application gets closed after 5 seconds.
     */
    private void exitApplication() {
        terminal.println("\nThank you for playing racetrack today. The Application closes in 5 seconds and your highscore will be saved. Goodbye.");
        try {
            this.gameEndListener.onGameEnd();
        } catch (UserIoException e) {
            terminal.println("Game could not be ended, because user could not be saved. Check if everything is right with the user-files");
            e.printStackTrace();
        }
    }

    @Override
    public void selectedRoomMessage(Room room, Level level) {
        terminal.println("\nYou entered the room with the mission to solve questions of the operation " + room.getOperation().toString() +
                ".\nFinish before the time runs out!");
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
    public String askQuestionsMessage(Room room, Level level) {
        terminal.println("Solve: " + level.getQuestion(room));
        terminal.print("Your answer:");
        return textIO.newStringInputReader().read();
    }

    @Override
    public void showAnwser(Room room, Level level) {
        terminal.println("Solve: " + level.getAnwser(room));
    }

    @Override
    public void showRoom(Room room, Level level) {
        terminal.println(room.toString());
    }

    @Override
    public void updateLevelMessage(Level level) {
        terminal.println("__________________________________________________\n");
        terminal.println("You finished this level successfully. Welcome to level " + level.getName());
    }

    @Override
    public void gameEndNotification(boolean success, int score) {
        terminal.println("__________________________________________________\n");
        if (success) {
            terminal.println("Congratulations! You finished the game successfully with the following score: " + score);
        } else {
            terminal.println("Unfortunately you could not successfully complete the game with a score of " + score + ".");
        }
    }

    @Override
    public void newPersonalHighscoreNotification(int highscore) {
        terminal.println("__________________________________________________\n");
        terminal.println("YOU ACHIEVED A NEW PERSONAL HIGHSCORE: " + highscore);
    }

    public void printPromt(String promt) {
        terminal.print(promt);
    }

    public interface DebugSuccessListener {
        void onGameSuccess();
    }

    public interface DebugFailListener {
        void onGameFailed();
    }
}
