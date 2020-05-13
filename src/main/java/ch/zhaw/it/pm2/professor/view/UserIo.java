package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.UserIoEncryptionException;
import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserIo {

    private static final Logger LOGGER = Logger.getLogger(UserIo.class.getCanonicalName());

    private final String filePath;

    public UserIo() {
        this.filePath = Config.USER_FILE_PATH;
    }

    public UserIo(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the user with the given user-name from the users-file.
     * If the file does not exist, it will be created.
     *
     * @param name of the user, that should be loaded
     * @return an object, null if the user was not found
     * @throws UserConverter.UserConversionException if something with the name is wrong
     * @throws UserIoException if something with the user-file is wrong
     */
    public User load(String name) throws UserConverter.UserConversionException, UserIoException {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(getFile()));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                User fileUser = UserConverter.toObject(line);
                if (fileUser.getName().equals(name)) {
                    logLoadedUser(fileUser);
                    return fileUser;
                }
            }
        } catch (IOException e) {
            throw new UserIoException(e);
        }
        return new User(name);
    }

    public void logLoadedUser(User user) {
        LOGGER.info(String.format("A user was loaded (name: %s, score: %s, highscore: %s).",
                user.getName(), user.getScore(), user.getHighscore()));
    }

    /**
     * Stores the highscore and the name of the given user-object in the users-file.
     * If the file does not exist. It will be created.
     * If the users-file already contains a user with the given name, his highscore will be updated.
     *
     * @param user the user-object, that should be persisted (must not be null)
     * @throws UserIoException if something with the user-file is wrong
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
        file.getParentFile().mkdirs(); // create resources-folder if it does not exist
        file.createNewFile(); // does nothing, if file does not already exist
        return file;
    }

    private static void writeUser(BufferedWriter writer, User fileUser) throws IOException, UserConverter.UserConversionException, UserIoEncryptionException {
        writer.write(UserConverter.toString(fileUser) + "\n");
    }
}


