package ch.zhaw.it.pm2.professor.view;

import ch.zhaw.it.pm2.professor.exception.UserIoEncryptionException;
import ch.zhaw.it.pm2.professor.exception.UserIoException;
import ch.zhaw.it.pm2.professor.model.Config;
import ch.zhaw.it.pm2.professor.view.converter.UserConverter;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Logger;

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
                String decryptedLine = decryptString(line);
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

    private static String encryptString(String line) throws UserIoEncryptionException {
        try {
            LOGGER.fine("String to encrypt: " + line);
            line = addWorkaroundSufix(line);
            LOGGER.fine("String with workaround-sufix to encrypt: " + line);
            String base64String = toBase64(line);
            LOGGER.fine("String to encrypt in base-64: " + base64String);
            byte[] bytes = Base64.getDecoder().decode(base64String);
            Cipher c = Cipher.getInstance(Config.ENCRYPTION_TYPE);
            byte[] keyBytes = Base64.getDecoder().decode(Config.SECRET_KEY);
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, Config.ENCRYPTION_TYPE);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = c.doFinal(bytes);
            String encryptedBase64String = Base64.getEncoder().encodeToString(encryptedBytes);
            LOGGER.fine("Encrypted string in base-64: " + encryptedBase64String);
            return encryptedBase64String;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new UserIoEncryptionException(e);
        }
    }

    private static String decryptString(String line) throws UserIoEncryptionException {
        try {
            LOGGER.fine("String to decrypt in base-64: " + line);
            byte[] bytes = Base64.getDecoder().decode(line);
            Cipher c = Cipher.getInstance(Config.ENCRYPTION_TYPE);
            byte[] keyBytes = Base64.getDecoder().decode(Config.SECRET_KEY);
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, Config.ENCRYPTION_TYPE);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = c.doFinal(bytes);
            String decryptedBase64String = Base64.getEncoder().encodeToString(decryptedBytes);
            LOGGER.fine("Decrypted string in base-64: " + decryptedBase64String);
            String decryptedString = fromBase64(decryptedBase64String);
            LOGGER.fine("Decrypted string with workaround-sufix: " + decryptedString);
            decryptedString = removeWorkaroundSufix(decryptedString);
            LOGGER.fine("Decrypted string: " + decryptedString);
            return decryptedString;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new UserIoEncryptionException(e);
        }
    }

    private static String addWorkaroundSufix(String string) {
        return string + 'x';
    }

    private static String removeWorkaroundSufix(String string) {
        return string.substring(0, string.length() - 1);
    }

    private static String toBase64(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        while (stringBuilder.length()%4 != 0) {
            stringBuilder.append('=');
        }
        return stringBuilder.toString();
    }

    private static String fromBase64(String base64String) {
        for (int i = base64String.length() - 1; i >= 0; i--) {
            if (base64String.charAt(i) != '=') {
                return base64String.substring(0, i + 1);
            }
        }
        return null;
    }

    private File getFile() throws IOException {
        File file = new File(this.filePath);
        //noinspection ResultOfMethodCallIgnored
        file.getParentFile().mkdirs(); // create resources-folder if it does not exist
        file.createNewFile(); // does nothing, if file does not already exist
        return file;
    }

    private static void writeUser(BufferedWriter writer, User fileUser) throws IOException, UserConverter.UserConversionException, UserIoEncryptionException {
        String encryptedUser = encryptString(UserConverter.toString(fileUser));
        writer.write(encryptedUser + "\n");
    }
}


