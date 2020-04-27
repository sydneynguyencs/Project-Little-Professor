package ch.zhaw.it.pm2.professor;

import ch.zhaw.it.pm2.professor.converter.UserConverter;

import java.io.*;

public class UserIo {

    private String filePath;

    public UserIo() {
        this.filePath = Config.USER_FILE_PATH;
    }

    public UserIo(String filePath) {
        this.filePath = filePath;
    }

    /**
     *
     * Loads the user with the given user-name from the users-file.
     * If the file does not exist, it will be created.
     *
     * @param name of the user, that should be loaded
     * @return an object, null if the user was not found
     * @throws IOException if the users-file can't be read for some reason
     * @throws InvalidFileException if something with the user-file is wrong
     */
    public User load(String name) throws IOException, InvalidFileException {

        File file = new File(this.filePath);

        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                User fileUser = UserConverter.toObject(line);
                if (fileUser.getName().equals(name)) {
                    return fileUser;
                }
            }
        } catch (UserConverter.UserConversionException e) {
            throw new InvalidFileException();
        }

        return null;
    }

    /**
     * Stores the highscore and the name of the given user-object in the users-file.
     * If the file does not exist. It will be created.
     * If the users-file already contains a user with the given name, his highscore will be updated.
     *
     * @param user the user-object, that should be persisted
     * @throws IOException if something on reading or writing to from the user-file goes wrong
     * @throws InvalidFileException if something with the user-file is wrong
     */
    public void store(User user) throws IOException, InvalidFileException {

        boolean updated = false;

        File file = new File(this.filePath);
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile(); // does nothing, if file does not already exist
        File tmpFile = new File(this.filePath + ".tmp");

        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
                BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                User fileUser = UserConverter.toObject(line);
                if (fileUser.getName().equals(user.getName())) {
                    fileUser.setHighscore(user.getHighscore());
                    updated = true;
                }
                writeUser(writer, fileUser);
            }
            if (!updated) {
                writeUser(writer, user);
            }
        } catch (UserConverter.UserConversionException e) {
            throw new InvalidFileException();
        }

        //noinspection ResultOfMethodCallIgnored
        file.delete();
        //noinspection ResultOfMethodCallIgnored
        tmpFile.renameTo(file);
    }

    private static void writeUser(BufferedWriter writer, User fileUser)
            throws IOException, UserConverter.UserConversionException {
        writer.write(UserConverter.toString(fileUser) + "\n");
    }

    public static class InvalidFileException extends Exception {}
}
