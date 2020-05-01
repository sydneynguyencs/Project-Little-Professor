package ch.zhaw.it.pm2.professor.model;

import java.util.Timer;

public class Launcher {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Game(), 0, Config.TIMER_INTERVAL_MILLIS);
    }
}