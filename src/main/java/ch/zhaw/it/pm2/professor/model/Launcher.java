package ch.zhaw.it.pm2.professor.model;

import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Launcher {

    private static final Logger LOGGER = Logger.getLogger(Launcher.class.getCanonicalName());

    public static void main(String[] args) throws UserIoException, UserConverter.UserConversionException {
        initLogger();
        Timer timer = new Timer();
        Game game = new Game();
        timer.scheduleAtFixedRate(game, 0, Config.TIMER_INTERVAL_MILLIS);
        game.start();
    }

    public static void initLogger() {
        // Initialize LogManager: must only be done once at application startup
        try {
            InputStream config = Launcher.class.getClassLoader().getResourceAsStream("./log.properties");
            LogManager.getLogManager().readConfiguration(config);
        } catch (IOException e) {
            LOGGER.log(Level.CONFIG,"No log.properties", e);
        }
    }
}