package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.InvalidInputException;
import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.model.House;
import ch.zhaw.it.pm2.professor.model.Level;
import ch.zhaw.it.pm2.professor.model.Room;

import java.io.IOException;

/**
 * We use an Interface for our prototype.
 * At later stages it would be easier to replace the DisplayIO, which in the prototype is with berxyTextIO,
 * to a JavaFX-View (or another View).
 */
public interface Display {

    void showHouse(House house, Level level);

    void welcomeMessage(House house);

    String requestUsername();

    Config.Command navigate(Level level);

    void timeIsUp();

    void levelComplete();

    String getNextUserInput() throws InvalidInputException;

    void selectedRoomMessage(Room room, Level level);

    void helpMessage();

    String askQuestionsMessage(Room room, Level level);

    void showAnwser(Room room, Level level);

    void showRoom(Room room, Level level);

    void updateLevelMessage(Level level);

    void gameEndNotification(boolean success, int score);

    void newPersonalHighscoreNotification(int highscore);

    void playAgainMessage();

    void levelNotSuccessfullMessage();

    interface GameEndListener {
        void onGameEnd() throws UserIoException;
    }
}
