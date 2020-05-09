package ch.zhaw.it.pm2.professor.exception;

public class UserIoEncryptionException extends RuntimeException {
    public UserIoEncryptionException(Throwable e) {
        super("Something went wrong with the user-encryption or decryption.", e);
    }
}
