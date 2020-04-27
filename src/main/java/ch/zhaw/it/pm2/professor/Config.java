package ch.zhaw.it.pm2.professor;



/**
 * This class specifies the most important and basic parameters of the game
 * Little Professor.
 * The class provides definitions such as for the type of operations,
 * available rooms and domain per level,
 */
public class Config {

    public final static String USER_FILE_PATH = "./users.txt";
    public static final int NUMBER_OF_OPERATIONS = 4;
    public static final int NUMBER_OF_ROOMS = 4;
    public static final int NUMBER_OF_LEVELS = 3;
    public static final int NUMBER_OF_QUESTIONS_PER_ROOM = 5;

    //public static final int TIMER = 2;

    // Player ID - wollen wir den Players 4 vorangefertigte Playerprofle zur Auswahl geben?
    // erreichte punkte könnten soz. gespeichert werden
    public enum Player {    }

    /**
     * Representation for all valid command for the game.
     */
    public enum Command {
        GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), LEFT("left"), RIGHT("right"), UP("up"), DOWN("down");

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

    /**
    * This {@link Enum} specifies the available operation types in the game.
    */
    public enum Operation {
        ADDITION("+"), SUBTRACTION("-"), MULTIPLICATION("*"), DIVISION("/");
        private String operation;

        /**
         * Initialize with according command.
         *
         * @param operation the command as String.
         */
        Operation(String operation) {
            this.operation = operation;
        }

        /**
         * @return the command as String
         */
        @Override
        public String toString() {
            return operation;
        }
    }

    /**
     * Specifies the available rooms in the game. Some room types
     * provide an operation, other do not.
     */
    public enum Room {

        ROOM_LEFT(Operation.ADDITION),
        ROOM_RIGHT(Operation.SUBTRACTION),
        ROOM_UP(Operation.MULTIPLICATION),
        ROOM_DOWN(Operation.DIVISION),
        HALLWAY("--");

        private Operation operation = null;
        private String room;

        Room(Operation operation) {
            this(operation.toString());
            this.operation = operation;
        }

        Room(String room) {
            this.room = room;
        }

        @Override
        public String toString() {
            return this.room;
        }

        /**
         * Returns the {@link Operation} that this land provides or null,
         * if it does not provide any.
         *
         * @return the {@link Operation} or null
         */
        public Operation getOperation() {
            return operation;
        }
    }

    /**
     * Representation for all Levels for the game.
     */
    public enum Level {

        BEGINNER("Beginner", 10), INTERMEDIATE("Intermediate", 100), ADVANCED("Advanced", 1000);
        private String level;
        private int domain;

        /**
         * Initialize with according level.
         * @param level   the level as String.
         */
        Level(String level, int domain) {
            this.level = level;
            this.domain = domain;
        }

        /**
         * @return  the level as String
         */
        @Override
        public String toString() {
            return level;
        }

        /**
         * Upper domain for questions difficulty
         * Returns the number domain that this level provides.
         * (Bsp. Additionen mit Ergebnissen [0, 10] in Level 1)
         *
         * @return the domain
         */
        public int getDomain() {
            return domain;
        }
    }

}
