package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import java.io.*;
import java.security.InvalidParameterException;

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
     */
    public User load(String name) throws UserConverter.UserConversionException, UserIoException {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(getFile()));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                User fileUser = UserConverter.toObject(line);
                if (fileUser.getName().equals(name)) {
                    return fileUser;
                }
            }
        } catch (IOException e) {
            throw new UserIoException(e);
        }
        return new User(name);
    }

    /**
     * Stores the highscore and the name of the given user-object in the users-file.
     * If the file does not exist. It will be created.
     * If the users-file already contains a user with the given name, his highscore will be updated.
     *
     * @param user the user-object, that should be persisted (must not be null)
     */
    public void store(User user) throws UserIoException {
        boolean updated = false;
        File file = null;
        try {
            file = getFile();
        } catch (IOException e) {
            throw new UserIoException(e);
        }
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
        } catch (UserConverter.UserConversionException | IOException e) {
            // if a UserConversionException is catched, something with the users in the file is wrong
            // because of this we throw a UserIoException to
            throw new UserIoException(e);
        }

        //noinspection ResultOfMethodCallIgnored
        file.delete();
        //noinspection ResultOfMethodCallIgnored
        tmpFile.renameTo(file);
    }

    private File getFile() throws IOException {
        File file = new File(this.filePath);
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile(); // does nothing, if file does not already exist
        return file;
    }

    private static void writeUser(BufferedWriter writer, User fileUser)
            throws IOException, UserConverter.UserConversionException {
        writer.write(UserConverter.toString(fileUser) + "\n");
    }

    public static class InvalidFileException extends Exception {}
}
