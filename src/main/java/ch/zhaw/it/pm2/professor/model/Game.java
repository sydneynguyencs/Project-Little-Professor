package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.controller.Parser;
import ch.zhaw.it.pm2.professor.view.CliDisplay;
import ch.zhaw.it.pm2.professor.view.Display;
import ch.zhaw.it.pm2.professor.view.User;
import ch.zhaw.it.pm2.professor.view.UserIo;

import java.io.IOException;
import java.util.TimerTask;

public class Game extends TimerTask {
    House house;
    Display display;
    User user;
    Parser parser;
    UserIo userIo;
    int time = 10;
    boolean started = false;

    public Game() throws IOException {
        this.house = new House();
        this.display = new CliDisplay();
        this.parser = new Parser();
        this.userIo = new UserIo();
    }

    @Override
    public void run() {
        update();
    }

    private void update() {
        if (this.started) {
            this.time--;
            System.out.println("Time: " + this.time);
            this.display.showHouse(this.house);
        }
    }

    public void start() throws IOException, UserIo.InvalidFileException {
        this.display.showHouse(this.house);
        this.display.welcomeMessage(house);
        String username = display.requestUsername();
        this.house.changeState(House.State.HALLWAY);
        this.user = userIo.load(username);
        this.house.setUsername(username);
        this.display.showHouse(this.house);
        this.started = true;
        doNextMove();
    }

    private void doNextMove() {
        Config.Command command = this.display.navigate(); //
        if (command == Config.Command.HELP) {
            this.display.helpMessage(); //can only be reached when typing help twice
            doNextMove();
        } else if (command == Config.Command.QUIT) {
            this.display.quitMessage();
        } else {
            moveUser(command);
        }
    }

    private void moveUser(Config.Command command) {
        Room currRoom = command.getRoom();//put user here.
        this.display.selectedRoomMessage(command);
        //put user into room and print room
        this.display.showRoom(command.getRoom());
        //todo: start question set
        this.display.askQuestionsMessage();
        //todo: print updated house (with completed room)
        //room enum complete/not available ROOM_LOOK_COMPLETED addHouse() adaptieren
        //can be done in doNextMove()
        doNextMove();
    }

}