package ch.zhaw.it.pm2.professor.view.converter;

import ch.zhaw.it.pm2.professor.view.User;

public class UserConverter {

    public static String toString(User user) throws UserConversionException {
        if (user == null) {
            throw new UserConversionException();
        }
        return user.getName() + "\t" + user.getHighscore();
    }

    public static User toObject(String user) throws UserConversionException {
        if (user == null) {
            throw new UserConversionException();
        }

        String[] userArray = user.split("\t");
        if (userArray.length != 2) {
            throw new UserConversionException();
        }
        return new User(userArray[0], 0, Integer.parseInt(userArray[1]));
    }

    public static class UserConversionException extends Exception {};
}
