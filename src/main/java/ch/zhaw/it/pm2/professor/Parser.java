package ch.zhaw.it.pm2.professor;

/**
 * The class Parser creates a DisplayIO. It reads the user input and transfers it into a command. The class
 * itself returns a Command object.
 */
public class Parser {
    CliDisplay displayIO;

    public Parser() {
        displayIO = new CliDisplay();
    }

    // TODO sobald Config fertig ist kann ich den next Command entgegennehmen
    /*public Config getNextCommand() {
        Config command;

        displayIO.messageUserForInput();
        String userInput = displayIO.getNextUserInput();

        command =

        return command;
    }*/

}
