package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.model.House;
import ch.zhaw.it.pm2.professor.model.Room;

/**
 * We use an Interface for our prototype.
 * At later stages it would be easier to replace the DisplayIO, which in the prototype is with berxyTextIO,
 * to a JavaFX-View (or another View).
 */
public interface Display {
    public void messageUserForInput();

    public void showHouse(House house);

    public void welcomeMessage(House house);

    public String requestUsername();

    public void seeTheHighscores();

    public void displayHighscores();

    public void navigate();

    public void timeIsUp();

    public void levelComplete();

    public String getNextUserInput();

    public Config.Command requestCommand() throws InvalidInputException;

    public void selectedRoomMessage(Config.Command command);

    public void helpMessage();

    public void quitMessage();

    public void askQuestionsMessage();

    public void showRoom(Room room);
}
