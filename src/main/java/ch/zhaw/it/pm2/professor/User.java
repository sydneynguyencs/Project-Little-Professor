package ch.zhaw.it.pm2.professor;

public class User {

    private String name;
    private int score;
    private int highscore;

    public User (String name, int score, int highscore) {
        this.name = name;
        this.score = score;
        this.highscore = highscore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }
}
