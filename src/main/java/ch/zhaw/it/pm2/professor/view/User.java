package ch.zhaw.it.pm2.professor.view;

public class User {

    private String name;
    private int score;
    private int highscore;
    private static final int NONE = 0;

    public User(String name, int score, int highscore) {
        this.name = name;
        this.score = score;
        this.highscore = highscore;
    }

    public User(String name) {
        this.name = name;
        this.score = NONE;
        this.highscore = NONE;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return name.equals(user.name);
    }
}
