package ch.zhaw.it.pm2.professor.exception;

public class HouseIOException extends Exception{
    public HouseIOException(Throwable wrappedException) {
        super("Something with the house-file is wrong.", wrappedException);
    }
}
