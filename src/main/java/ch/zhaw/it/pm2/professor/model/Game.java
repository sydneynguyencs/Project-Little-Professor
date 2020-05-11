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
    private int time;
    private Level currentLevel;
    private final LevelSource levelSource;
    private int levelCount = 0;
    private boolean gameEnded = false;
    private boolean gameSuccess = false;
    private int oldScore;

    public Game() throws IOException {
        this.house = new House(this);
        this.display = new CliDisplay(this, this, this);
        this.userIo = new UserIo();
        levelSource = new LevelFactory();
        currentLevel = levelSource.getLevels().get(levelCount); //erstes Level aus der Liste
        resetTimer();
    }

    @Override
    public void run() {
        update();
    }

    private void update() {
        this.time--;
        this.house.setTime(this.time);
    }

    public void start() throws UserIoException, UserConverter.UserConversionException, IOException {
        this.display.showHouse(this.house, currentLevel);
        this.display.welcomeMessage(house);
        this.user = userIo.load(display.requestUsername());
        while (true) {
            this.user.setScore(0);
            doUserCommand();
            end();
            this.display.playAgainMessage();
        }
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
        if(time <= 0) {
            this.display.timeIsUp();
            this.display.playAgainMessage();
            resetGame();
        }
        updateHouse();
        this.display.showHouse(this.house, currentLevel);
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

    private void resetGame() {
        user.setScore(0);
        resetTimer();
        resetRooms();
        levelCount = 0;
        this.house.setLevel(currentLevel);
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
        room.setCompleted(true);
        if (allRoomsCompleted()) {
            if (levelCount == levelSource.getLevels().size() - 1) {//final level check
                this.display.gameEndNotification(levelSuccessful(), user.getScore());
                this.display.playAgainMessage();
                resetGame();
            } else {
                if (levelSuccessful()) {
                    updateLevel();
                } else {
                    this.display.levelNotSuccessfullMessage();
                    this.display.playAgainMessage();
                    resetGame();
                }
            }
        }
        if (!this.gameEnded) {
            doUserCommand();
        }
    }

    private void resetTimer() {
        time = (currentLevel.getRooms().length - 1) * Config.NUMBER_OF_QUESTIONS_PER_ROOM * 10;
    }

    private void updateLevel() {
        levelCount++;
        currentLevel = levelSource.getLevels().get(levelCount);
        oldScore = user.getScore();
        resetTimer();
        resetRooms();
        this.display.updateLevelMessage(currentLevel);
    }

    private void resetRooms() {
        //all rooms set to not completed yet
        for (int i = 1; i < currentLevel.getRooms().length; i++) {
            currentLevel.getRooms()[i].setCompleted(false);
        }
    }

    private boolean levelSuccessful() {
        return (user.getScore()-oldScore >= currentLevel.getMinPoints() && time >= 0 && allRoomsCompleted());
    }

    private boolean allRoomsCompleted() {
        boolean allRoomsCompleted = false;
        int count = 0;
        for (int i = 1; i < currentLevel.getRooms().length; i++){
            if (currentLevel.getRooms()[i].isCompleted()) {
                count++;
            }
        }
        if (count == currentLevel.getRooms().length-1) {
            allRoomsCompleted = true;
        }
        return allRoomsCompleted;
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