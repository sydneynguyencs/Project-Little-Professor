package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.UserIOException;
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

    void messageUserForInput();

    void showHouse(House house, Level level);

    void welcomeMessage();

    String requestUsername();

    Config.Command navigate(Level level);

    void invalidInputMessage();

    void timeIsUp();

    void levelComplete();

    String getNextUserInput();

    void checkForQuitCommand(String userInput);

    void selectedRoomMessage(Room room);

    void helpMessage();

    String askQuestionsMessage(Room room, Level level);

    void showAnswer(Room room, Level level);

    void showRoom(Room room);

    void updateLevelMessage(Level level);

    void gameEndNotification(boolean success, int score);

    void newPersonalHighscoreNotification(int highscore);

    void playAgainMessage();

    void levelNotSuccessfulMessage();

    interface GameEndListener {
        void onGameEnd() throws UserIOException;
    }
}
