package ch.zhaw.it.pm2.prefessor;

import ch.zhaw.it.pm2.professor.Config;
import ch.zhaw.it.pm2.professor.User;
import ch.zhaw.it.pm2.professor.UserIo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class UserIoTest {

    private UserIo userIo;

    public UserIoTest() {
        this.userIo = new UserIo(Config.USER_TEST_FILE_PATH);
    }

    /**
     * Tests if the the user-file is created if it does not exist and User.load() is called.
     */
    @Test
    public void noFileStoreTest() throws IOException, UserIo.InvalidFileException {
        deleteUserFile();
        User user = new User("TestUser", 0, 1000);
        this.userIo.store(user);
        assertTrue(getUserFile().exists());
    }

    /**
     * Tests if the the user-file is created if it does not exist and User.store() is called.
     */
    @Test
    public void noFileLoadTest() throws IOException, UserIo.InvalidFileException {
        deleteUserFile();
        User user = new User("TestUser", 0, 1000);
        this.userIo.store(user);
        assertTrue(getUserFile().exists());
    }

    /**
     *  Creates two users stores them, then updates the second one.
     *  After this the stored users are loaded again. Name and highscore should now match with the original objects.
     */
    @Test
    public void storeAndLoadTest() throws IOException, UserIo.InvalidFileException {
        User testUser = new User("Fritz Muster", 20, 1500);
        User testUserTwo = new User("Peter Wurst", 33, 1700);
        userIo.store(testUser);
        userIo.store(testUserTwo);
        testUserTwo.setHighscore(2000);
        userIo.store(testUserTwo);
        assertEquals(testUser, userIo.load(testUser.getName()));
        assertEquals(testUser.getHighscore(), userIo.load(testUser.getName()).getHighscore());
        assertEquals(testUserTwo, userIo.load(testUserTwo.getName()));
        assertEquals(testUserTwo.getHighscore(), userIo.load(testUserTwo.getName()).getHighscore());
    }

    private static void deleteUserFile() {
        File userFile = getUserFile();
        //noinspection ResultOfMethodCallIgnored
        userFile.delete();
    }

    private static File getUserFile() {
        return new File(Config.USER_TEST_FILE_PATH);
    }
}
