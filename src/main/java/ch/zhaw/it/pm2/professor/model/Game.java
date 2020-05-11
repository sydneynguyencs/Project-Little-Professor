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

public class Game extends TimerTask implements House.TimeInterface, Display.GameEndListener {
    private final House house;
    private final Display display;
    private User user;
    private final UserIo userIo;
    private int time;
    private boolean started = false;
    private Level currentLevel;
    private final LevelSource levelSource;
    private int levelCount = 0;
    private int oldScore;

    public Game() throws IOException {
        this.house = new House(this);
        this.display = new CliDisplay(this);
        this.userIo = new UserIo();
        levelSource = new LevelFactory();
        currentLevel = levelSource.getLevels().get(levelCount); //erstes Level aus der Liste
        time = (currentLevel.getRooms().length-1) * Config.NUMBER_OF_QUESTIONS_PER_ROOM * 5;
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
        updateAndShowHouse();
        this.started = true;
        doUserCommand();
    }

    private void updateAndShowHouse() throws IOException {
        this.house.changeState(House.State.HALLWAY);
        this.house.setUsername(this.user.getName());
        this.house.setHighscore(user.getHighscore());
        this.house.setScore(user.getScore());
        this.house.setTime(this.time);
        this.house.setLevel(currentLevel);
        this.display.showHouse(this.house, currentLevel);
    }

    private void doUserCommand() throws IOException, UserIoException {
        if(time >= 0) {
            onGameEnd();
        }
        updateAndShowHouse();
        this.house.changeState(House.State.HALLWAY);

        Config.Command command = this.display.navigate(currentLevel);
        if(command == null) {
            doUserCommand();
        }
        if (command == Config.Command.HELP) {
            this.display.helpMessage();
            doUserCommand();
        }
        moveIntoRoom(command);
    }

    private void moveIntoRoom(Config.Command command) throws IOException, UserIoException {
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
        //TODO: set house with completed room.
        //ROOM_LOOK_COMPLETED ins haus einbinden
        if(allRoomsCompleted()) {
            if (levelSuccessful()) {
                updateLevel();
            } else {
                this.display.levelNotSuccessfullMessage();
                resetRooms();
            }
        }

        doUserCommand();
    }

    private void updateLevel() {
        levelCount++;
        currentLevel = levelSource.getLevels().get(levelCount);
        oldScore = user.getScore();
        time = (currentLevel.getRooms().length-1) * Config.NUMBER_OF_QUESTIONS_PER_ROOM * 10;
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