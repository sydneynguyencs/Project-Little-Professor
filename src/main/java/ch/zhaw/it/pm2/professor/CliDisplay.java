package ch.zhaw.it.pm2.professor;

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
    TextIO textIO;
    TextTerminal<?> terminal;

    /**
     * Constructor of the class DisplayIO. It initializes the Terminal, TextIO and a Config-Object.
     */
    public CliDisplay() {
        textIO = TextIoFactory.getTextIO();
        terminal = textIO.getTextTerminal();
    }

    public void messageUserForInput() {
        terminal.print("Please choose a valid input.");
    }

    public void welcomeMessage() {
        terminal.print("Welcome to the Little Professor Game.\nThe little Professor will " +
                "help you to train your math skills while playing.");
    }

    public void seeTheHighscores() {
        terminal.print("To see the highscores from all the users, please press Y.");
    }

    public void displayHighscores() {
        terminal.print("This are the highscores.");
    }

    public void navigate() {
        terminal.print("To navigate inside the house, user the following keys:\n\n" +
                "W for UP\n" +
                "A for Left *** D for Right\n" +
                "X for Down");
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
}
