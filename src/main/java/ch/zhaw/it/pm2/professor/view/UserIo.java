package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
        File file = getFile();
        User user = null;
        boolean foundUser=false;
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                User fileUser = UserConverter.toObject(line);
                if (fileUser.getName().equals(name)) {
                    user = fileUser;
                    foundUser = true;
                }
            }
            if(foundUser==false){
                user = new User(name);
            }
        } catch (UserConverter.UserConversionException e) {
            throw new InvalidFileException();
        }
        return user;
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
        File file = getFile();
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
