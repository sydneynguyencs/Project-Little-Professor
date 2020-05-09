package ch.zhaw.it.pm2.professor.exception;

public class UserIoException extends Exception{

    public UserIoException(Throwable wrappedException) {
        super("Something with the user-file is wrong.", wrappedException);
    }

}
