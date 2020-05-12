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
    /**
     * Messeage the user for a valid user input.
     */
    @Override
    public void messageUserForInput() {
        this.terminal.println("Please choose a valid input.");
    }
    /**
     * Method show house prints out a house to the terminal.
     * @param house a house object
     * @param level a level object
     */
    @Override
    public void showHouse(House house, Level level) {
        this.terminal.println(house.printLevel(level));
    }
    /**
     * Takes a house object and prints the welcome messeage to the terminal.
     * @param house a house object
     */
    @Override
    public void welcomeMessage(House house) {
        this.terminal.println("The little Professor will help you to train your math skills while playing.");
    }

    /**
     * Messeage the user to ask for the username. The input is then passed over to the parser which checks
     * if the input is valid or not.
     * @return if the input is valid, the username as String gets returned
     */
    @Override
    public String requestUsername() {
        this.terminal.println("Please enter your username.\nBetween " + Config.MIN_CHARS_USERNAME + " - " + Config.MAX_CHARS_USERNAME + " characters");
        String username = getNextUserInput();
        try {
            this.parser.parseName(username);
        } catch (InvalidInputException e) {
            invalidInputMessage();
        }
        return username;
    }
    /**
     * Method that returns a Config.Command from a specific level, the user can navigate with the given
     * commands.
     * @param level the actuel level-object
     * @return  the command where the user wants to go as a Config.Command object
     */
    @Override
    public Config.Command navigate(Level level) {
        Config.Command command = null;
        this.terminal.println("You are in the Hallway right now. Type any of the following commands to enter a room.\n");
        this.terminal.println("LEFT: left\nUP: up\nRIGHT: right\nDOWN: down\nHELP: help\nQUIT: quit\n");
        String input = getNextUserInput();
        try {
            command = this.parser.parseInput(level.getValidCommandsList(), input.toLowerCase());
        } catch (InvalidInputException e) {
            invalidInputMessage();
        }
        return command;
    }
    /**
     * Informs the user, when an input is invalid.
     */
    @Override
    public void invalidInputMessage() {
        this.terminal.print("The given input is invalid. Please enter one of the proposed commands.\n");
    }
    /**
     * Informs the user when the time is up.
     */
    @Override
    public void timeIsUp() {
        this.terminal.print("\nThe time is up.\nYour score will be written to the highscore file and the game " +
                "will end here.\n");
    }
    /**
     * Informs the user when the level is completed. The user can choose to keep on playing or leaving
     * the application.
     */
    @Override
    public void levelComplete() {
        this.terminal.print("Congratulations, you completed this level.\nWould you like to go to the next level " +
                "or would you like to exit the game?\nPlease choose N for next level or E for exit.");
    }
    /**
     * Method produces a String to ask for the next user input.
     * @return  the user input which the user gives in
     */
    @Override
    public String getNextUserInput() {
        this.terminal.print("\nEnter \"quit\" to quit.\n");
        String userInput = this.textIO.newStringInputReader().read();
        checkForQuitCommand(userInput);
        checkForGameEnd(userInput);
        return userInput;
    }
    /**
     * The method checks if the user types in quit. He can do this after every turn.
     * Calls exitApplication-method if the user wants to quit the game.
     * @param userInput the userInput to quit the game
     */
    @Override
    public void checkForQuitCommand(String userInput) {
        Config.Command[] quitCommandList = {Config.Command.QUIT};
        try {
            this.parser.parseInput(quitCommandList, userInput);
            exitApplication();
        } catch (InvalidInputException e) {
            // so we don't quit
        }
    }

    /**
     * TODO Can be deleted, when game can be finished
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
        this.terminal.println("\nThank you for playing little-professor today. The Application closes in 5 seconds and your highscore will be saved. Goodbye.");
        try {
            this.gameEndListener.onGameEnd();
        } catch (UserIoException e) {
            this.terminal.println("Game could not be ended, because user could not be saved. Check if everything is right with the user-files");
            e.printStackTrace();
        }
    }

    /**
     * Room selecting method. Parameters are Room and Level-objects.
     * The user can then choose a valid Room from this Level.
     * @param room  a room inside this level
     * @param level the actual level
     */
    @Override
    public void selectedRoomMessage(Room room, Level level) {
        this.terminal.println("\nYou entered the room with the mission to solve questions of the operation " + room.getOperation().toString() +
                ".\nFinish before the time runs out!");
    }

    /**
     * Producing a help-message to give some information to the user.
     */
    @Override
    public void helpMessage() {
        this.terminal.println("Move into a room to start the question set and gain enough points to win this level.\nWatch out for the timer!\nTo quit Little Professor type \"quit\"");
    }

    /**
     * Ask the user a question and read the answer/input from the terminal.
     * @param room the room in which the user is
     * @param level the level in which the user is
     * @return the answer the user types in
     */
    @Override
    public String askQuestionsMessage(Room room, Level level) {
        this.terminal.println("Solve: " + level.getQuestion(room));
        this.terminal.print("Your answer:");
        return this.textIO.newStringInputReader().read();
    }

    /**
     * Show the answer of the question.
     * @param room the room in which the user is
     * @param level the level in which the user is
     */
    @Override
    public void showAnwser(Room room, Level level) {
        this.terminal.println("Solution: " + level.getAnwser(room));
    }

    /**
     * Prints the room.
     * @param room a valid room inside the level
     * @param level the actual level
     */
    @Override
    public void showRoom(Room room, Level level) {
        this.terminal.println(room.toString());
    }

    /**
     * Update the level. If the user finished a level this message gets printed.
     * @param level the next level
     */
    @Override
    public void updateLevelMessage(Level level) {
        this.terminal.println("_______________________________________________________________________________________________\n");
        this.terminal.println("You finished this level successfully. Welcome to level " + level.getName());
        this.terminal.println("The timer is reset. \nTry to gain " + (level.getRooms().length-1)*4 + " additional points to get to the next level.\n");
    }

    /**
     * Notify the user if he finished the game or didn't complete the game and show the score to the user.
     * @param success if completed, this will be true
     * @param score   the score the player reached during playing
     */
    @Override
    public void gameEndNotification(boolean success, int score) {
        this.terminal.println("_______________________________________________________________________________________________\n");
        if (success) {
            this.terminal.println("Congratulations! You finished the game successfully with the following score: " + score);
        } else {
            this.terminal.println("Unfortunately you could not successfully complete the game with a score of " + score + ".");
        }
    }

    /**
     * Messeage the user if he/she doesn't reaches the end of the level because of not reached
     * the passmark.
     */
    @Override
    public void levelNotSuccessfullMessage() {
        this.terminal.println("\nYou did not collect enough points to pass this level.\n");
    }

    /**
     * If a user reaches a new personal highscore, this message gets promted out.
     * @param highscore the int value of the personal highscore
     */
    @Override
    public void newPersonalHighscoreNotification(int highscore) {
        this.terminal.println("_______________________________________________________________________________________________\n");
        this.terminal.println("YOU ACHIEVED A NEW PERSONAL HIGHSCORE: " + highscore);
    }

    /**
     * A message asking the user if he wants to play again after finishing.
     */
    @Override
    public void playAgainMessage() {
        Config.Command command = null;
        Config.Command[] yesCommandList = {Config.Command.YES};
        while (command == null) {
            terminal.println("_______________________________________________________________________________________________\n");
            terminal.println("Would you like to play again? ('y' for yes)");
            String input = getNextUserInput();
            try {
                command = this.parser.parseInput(yesCommandList, input);
            } catch (InvalidInputException e) {
                // ask again
            }
        }
    }

    /**
     * TODO Bleibt das hier oder kann das gelöscht werden wenn das Spiel fertig ist?
     */
    public interface DebugSuccessListener {
        void onGameSuccess();
    }

    public interface DebugFailListener {
        void onGameFailed();
    }
}
