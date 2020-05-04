package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.view.UserIo;

import java.io.IOException;
import java.util.Timer;

public class Launcher {

    public static void main(String[] args) throws IOException, UserIo.InvalidFileException {
        Timer timer = new Timer();
        Game game = new Game();
        game.start();
        timer.scheduleAtFixedRate(new Game(), 0, Config.TIMER_INTERVAL_MILLIS);
    }
}