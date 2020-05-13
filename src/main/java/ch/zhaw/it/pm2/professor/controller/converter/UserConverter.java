package ch.zhaw.it.pm2.professor.controller.converter;

import ch.zhaw.it.pm2.professor.model.User;

/**
 * This class is used to convert user-objects to a string and strings to user-objects.
 * The usage of this class is to serialize user-objects. Persisting user-highscores can be achieved with this.
 */
public class UserConverter {

    public static String toString(User user) throws UserConversionException {
        if (user == null) {
            throw new UserConversionException("User mustn't be null.");
        }
        return user.getName() + '+' + user.getHighscore();
    }

    public static User toObject(String user) throws UserConversionException {
        if (user == null) {
            throw new UserConversionException("User mustn't be null.");
        }

        String[] userArray = user.split("\\+");
        if (userArray.length != 2) {
            throw new UserConversionException("To many user-attributes in user-string: " + user);
        }
        return new User(userArray[0], 0, Integer.parseInt(userArray[1]));
    }

    public static class UserConversionException extends Exception {
        public UserConversionException(String message) {
            super(message);
        }
    }
}
