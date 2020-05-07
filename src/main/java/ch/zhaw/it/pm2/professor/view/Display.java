package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.model.House;
import ch.zhaw.it.pm2.professor.model.Level;
import ch.zhaw.it.pm2.professor.model.Room;

/**
 * We use an Interface for our prototype.
 * At later stages it would be easier to replace the DisplayIO, which in the prototype is with berxyTextIO,
 * to a JavaFX-View (or another View).
 */
public interface Display {
    public void messageUserForInput();

    public void showHouse(House house, Level level);

    public void welcomeMessage(House house);

    public String requestUsername();

    public void seeTheHighscores();

    public void displayHighscores();

    public Config.Command navigate(Level level);

    public void timeIsUp();

    public void levelComplete();

    public String getNextUserInput();

    public void selectedRoomMessage(Room room, Level level);

    public void helpMessage();

    public void quitMessage();

    public void askQuestionsMessage();

    public void showRoom(Room room, Level level);
}
