package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.UserIoEncryptionException;
import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class UserIo {

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
                String decryptedLine = cryptString(line, Cipher.DECRYPT_MODE);
                System.out.println(decryptedLine);
                User fileUser = UserConverter.toObject(decryptedLine);
                if (fileUser.getName().equals(name)) {
                    System.out.println("username: " + fileUser.getName());
                    System.out.println("score: " + fileUser.getScore());
                    System.out.println("highscore: " + fileUser.getHighscore());
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

    private static String cryptString(String line, int cryptMode) throws UserIoEncryptionException {
        try {
            byte[] bytes = Base64.getDecoder().decode(makeBase64(line));
            Cipher c = Cipher.getInstance(Config.ENCRYPTION_TYPE);
            SecretKeySpec k = new SecretKeySpec(Config.SECRET_KEY, Config.ENCRYPTION_TYPE);
            c.init(cryptMode, k);
            byte[] cryptedBytes = c.doFinal(bytes);
            return new String(Base64.getEncoder().encode(cryptedBytes));
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new UserIoEncryptionException(e);
        }
    }

    private static String makeBase64(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length()%4 != 0) {
            stringBuilder.append('=');
        }
        return stringBuilder.toString();
    }

    private File getFile() throws IOException {
        File file = new File(this.filePath);
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs(); // create resources-folder if it does not exist
        file.createNewFile(); // does nothing, if file does not already exist
        return file;
    }

    private static void writeUser(BufferedWriter writer, User fileUser) throws IOException, UserConverter.UserConversionException, UserIoEncryptionException {
        String encryptedUser = cryptString(UserConverter.toString(fileUser), Cipher.ENCRYPT_MODE);
        writer.write(encryptedUser + "\n");
    }
}


