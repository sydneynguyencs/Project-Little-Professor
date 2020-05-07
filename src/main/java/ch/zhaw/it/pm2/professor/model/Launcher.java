package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import java.io.IOException;
import java.util.Timer;

public class Launcher {

    public static void main(String[] args) throws IOException, UserIoException, UserConverter.UserConversionException {
        Timer timer = new Timer();
        Game game = new Game();
        timer.scheduleAtFixedRate(game, 0, Config.TIMER_INTERVAL_MILLIS);
        game.start();
    }
}