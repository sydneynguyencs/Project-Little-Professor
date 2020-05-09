package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.controller.LevelFactory;
import ch.zhaw.it.pm2.professor.controller.LevelSource;
import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.view.CliDisplay;
import ch.zhaw.it.pm2.professor.view.Display;
import ch.zhaw.it.pm2.professor.view.User;
import ch.zhaw.it.pm2.professor.view.UserIo;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import java.io.IOException;
import java.util.TimerTask;

public class Game extends TimerTask implements House.TimeInterface, Display.GameEndListener, CliDisplay.DebugSuccessListener, CliDisplay.DebugFailListener {
    private final House house;
    private final Display display;
    private User user;
    private final UserIo userIo;
    private int time = 10;
    private boolean started = false;
    private Level currentLevel;
    private final LevelSource levelSource;
    private int levelCount = 0;
    private boolean gameEnded = false;
    private boolean gameSuccess = false;

    public Game() throws IOException {
        this.house = new House(this);
        this.display = new CliDisplay(this, this, this);
        this.userIo = new UserIo();
        levelSource = new LevelFactory();
        currentLevel = levelSource.getLevels().get(levelCount); //erstes Level aus der Liste
    }

    @Override
    public void run() {
        update();
    }

    private void update() {
        if (this.started) {
            this.time--;
            this.house.setTime(this.time);
        }
    }

    public void start() throws UserIoException, UserConverter.UserConversionException, IOException {
        this.display.showHouse(this.house, currentLevel);
        this.display.welcomeMessage(house);
        this.user = userIo.load(display.requestUsername());
        updateHouse();
        this.started = true;
        doUserCommand();
        end();
        this.userIo.store(this.user);
    }

    /**
     * at the moment it is called when the player enters the debug-command "suc" or "fail"
     * the DebugSuccessListener can be removed, when the rest of the logic is implemented
     */
    private void end() {
        this.display.gameEndNotification(this.gameSuccess, this.user.getScore());
        highscoreCheck();
    }

    public void highscoreCheck() {
        int score = this.user.getScore();
        System.out.println(this.user.getHighscore());
        System.out.println(this.user.getScore());
        if (score > this.user.getHighscore()) {
            this.user.setHighscore(score);
            this.display.newPersonalHighscoreNotification(score);
        }
    }

    private void updateHouse() throws IOException {
        this.house.changeState(House.State.HALLWAY);
        this.house.setUsername(this.user.getName());
        this.house.setHighscore(user.getHighscore());
        this.house.setScore(user.getScore());
        this.house.setTime(this.time);
        this.house.setLevel(currentLevel);
    }

    private void doUserCommand() throws IOException {
        updateHouse();
        this.display.showHouse(this.house, currentLevel);
        this.house.changeState(House.State.HALLWAY);

        Config.Command command = this.display.navigate(currentLevel);
        if(command == null) {
            doUserCommand();
        }
        switch(command) {
            case HELP:
                this.display.helpMessage();
                doUserCommand();
                break;
            case DEBUG_FAIL:
            case DEBUG_SUCCESS: break;
            default:
                moveIntoRoom(command);
        }
    }

    private void moveIntoRoom(Config.Command command) throws IOException {
        Room room = null;
        for (int i = 0; i < currentLevel.getRooms().length; i++) {
            if (currentLevel.getRooms()[i].getCommand() == command) {
                room = currentLevel.getRooms()[i];
            }
        }
        assert false;

        this.display.selectedRoomMessage(room, currentLevel);
        this.display.showRoom(room, currentLevel);
        startQuestionSet(room, currentLevel);
        updateHouse();
        //mark room as completed

        //if all rooms of current level completed && !timeUp: go to next level currentLevel++
        //when the last level has been finished, this.gameEnded should be set to true and this.gameSuccess also
        if (!this.gameEnded) {
            //updateLevel();
            doUserCommand();
        }
    }

    private void updateLevel() throws IOException {
        levelCount++;
        currentLevel = levelSource.getLevels().get(levelCount);
        this.display.updateLevelMessage(currentLevel);
    }

    private void startQuestionSet(Room room, Level level) {
        for (int i = 0; i < Config.NUMBER_OF_QUESTIONS_PER_ROOM; i++) {
            if (this.display.askQuestionsMessage(room, level).equals(level.getAnwser(room))) {
                user.setScore(user.getScore() + 1);
            }
            this.display.showAnwser(room, level);
        }
    }

    @Override
    public void onGameFailed() {
        this.gameEnded = true;
    }

    @Override
    public void onGameSuccess() {
        this.gameEnded = true;
        this.gameSuccess = true;
    }

    public int getTime() {
        return this.time;
    }

    public void onGameEnd() throws UserIoException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println(e.toString());
        }
        if (this.user != null) {
            this.userIo.store(this.user);
        }
        System.exit(0);
    }
}