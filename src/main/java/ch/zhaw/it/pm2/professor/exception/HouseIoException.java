package ch.zhaw.it.pm2.professor.exception;

public class HouseIoException extends Exception{

    public HouseIoException(Throwable wrappedException) {
        super("Something with the house-file is wrong.", wrappedException);
    }

}
