package ch.zhaw.it.pm2.professor.exception;

public class InvalidInputException extends Exception {
    private String key;

    public InvalidInputException(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String toString() {
        return "This input is invalid.";
    }
}
