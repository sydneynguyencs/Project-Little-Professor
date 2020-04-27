package ch.zhaw.it.pm2.professor;



/**
 * This class specifies the most important and basic parameters of the game
 * Little Professor.
 * The class provides definitions such as for the type of operations
 * available rooms per level.
 */
public class Config {

    public final static String USER_FILE_PATH = "./users.txt";
    public static final int NUMBER_OF_OPERATIONS = 4;
    public static final int NUMBER_OF_ROOMS = 4;
    public static final int NUMBER_OF_LEVELS = 3;
    public static final int LEVEL_ONE = 10; //[0,10] as domain for question difficulty
    public static final int LEVEL_TWO = 100;
    public static final int LEVEL_THREE = 1000;
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
         * @param operation   the command as String.
         */
         Operation(String operation) {
            this.operation = operation;
        }

        /**
         * @return  the command as String
         */
        @Override
        public String toString() {
            return operation;
        }

        /**
         * This {@link Enum} specifies the available rooms in the game. Some room types
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

            private Room(Operation operation) {
                this(operation.toString());
                this.operation = operation;
            }

            private Room(String room) {
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

    }

}
