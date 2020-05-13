package ch.zhaw.it.pm2.professor.controller;

import ch.zhaw.it.pm2.professor.exception.UserIoEncryptionException;
import ch.zhaw.it.pm2.professor.model.Config;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Logger;

public class EncryptionHandler {

    private static final Logger LOGGER = Logger.getLogger(EncryptionHandler.class.getCanonicalName());

    public static String encryptString(String line) throws UserIoEncryptionException {
        try {
            LOGGER.info("String to encrypt: " + line);
            byte[] bytes = line.getBytes(StandardCharsets.UTF_8);
            Cipher c = Cipher.getInstance(Config.ENCRYPTION_TYPE);
            byte[] keyBytes = Base64.getDecoder().decode(Config.SECRET_KEY);
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, Config.ENCRYPTION_TYPE);
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = c.doFinal(bytes);
            String encryptedBase64String = Base64.getEncoder().encodeToString(encryptedBytes);
            LOGGER.info("Encrypted string in base-64: " + encryptedBase64String);
            return encryptedBase64String;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new UserIoEncryptionException(e);
        }
    }

    public static String decryptString(String line) throws UserIoEncryptionException {
        try {
            LOGGER.info("String to decrypt in base-64: " + line);
            byte[] bytes = Base64.getDecoder().decode(line);
            Cipher c = Cipher.getInstance(Config.ENCRYPTION_TYPE);
            byte[] keyBytes = Base64.getDecoder().decode(Config.SECRET_KEY);
            SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, Config.ENCRYPTION_TYPE);
            c.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = c.doFinal(bytes);
            String decryptedString = new String(decryptedBytes, StandardCharsets.UTF_8);
            LOGGER.info("Decrypted string: " + decryptedString);
            return decryptedString;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new UserIoEncryptionException(e);
        }
    }

}
