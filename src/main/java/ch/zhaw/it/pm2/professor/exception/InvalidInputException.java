package ch.zhaw.it.pm2.professor.exception;

public class InvalidInputException extends Exception {

    public InvalidInputException() {
    }

    public String toString() {
        return "This input is invalid.";
    }
}
