package ch.zhaw.it.pm2.professor;

/**
 * This class specifies the most important and basic parameters of the game
 * Little Professor.
 * The class provides definitions such as for the type of operations,
 * available rooms and domain per level,
 */
public class Config {

    public final static String USER_FILE_PATH = "./users.txt";
    public final static String USER_TEST_FILE_PATH = "./users_test.txt";
    public static final int NUMBER_OF_OPERATIONS = 4;
    public static final int NUMBER_OF_ROOMS = 4;
    public static final int NUMBER_OF_QUESTIONS_PER_ROOM = 5;

    //public static final int TIMER = 2;

    /**
     * Representation for all valid command for the game.
     */
    public enum Command {
        GO("go"),
        QUIT("quit"),
        HELP("help"),
        UNKNOWN("?"),
        LEFT("left"),
        RIGHT("right"),
        UP("up"),
        DOWN("down");

        private String command;

        /**
         * Initialize with according command.
         * @param command   the command as String.
         */
        Command(String command) {
            this.command = command;
        }

        /**
         * @return  the command as String
         */
        @Override
        public String toString() {
            return command;
        }
    }

}
