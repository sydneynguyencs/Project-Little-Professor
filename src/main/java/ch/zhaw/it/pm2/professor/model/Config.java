package ch.zhaw.it.pm2.professor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class specifies the most important and basic parameters of the game
 * Little Professor.
 * The class provides definitions such as for the type of operations,
 * available rooms and domain per level,
 */
public class Config {

    public final static String USER_FILE_PATH = "/src/main/resources/users.txt";
    public final static String USER_TEST_FILE_PATH = "/src/test/resources/users_test.txt";
    public static final int NUMBER_OF_OPERATIONS = 4;
    public static final int NUMBER_OF_QUESTIONS_PER_ROOM = 5;
    public static final int MAX_CHARS_USERNAME = 12;
    public static final int MIN_CHARS_USERNAME = 4;
    public static final int TIMER_INTERVAL_MILLIS = 1000;
    public static final String[] ROOM_LOOK = {
            "################" ,
            "#              #" ,
            "#              #" ,
            "#              #" ,
            "################"
    };

    /**
     * Representation for all valid command for the game.
     */
    public enum Command {
        GO("go"),
        QUIT("quit"),
        HELP("help"),
        UNKNOWN("?"),
        LEFT("left", Room.ROOM_LEFT),
        RIGHT("right", Room.ROOM_RIGHT),
        UP("up", Room.ROOM_UP),
        DOWN("down", Room.ROOM_DOWN);

        private String command;
        private Room room;


        /**
         * Initialize with according command.
         * @param command   the command as String.
         */
        Command(String command) {
            this.command = command;
        }

        /**
         * Initialize with according command.
         * @param command   the command as String.
         * @param room   the room as Room.
         */
        Command(String command, Room room) {
            this.command = command;
            this.room = room;
        }

        /**
         * @return  the command as String
         */
        @Override
        public String toString() {
            return command;
        }

        public static List<Command> getCommandList() {
            List<Command> commands = new ArrayList<>();
            for(int i = 0; i < 8; i++)
                commands.add(Command.values()[i]);
            return commands;
        }

        public Room getRoom() {
            return room;
        }
    }

    /**
    * This {@link Enum} specifies the available operation types in the game.
    */
    public enum Operation {
        ADDITION("+"),
        SUBTRACTION("-"),
        MULTIPLICATION("*"),
        DIVISION("/");

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

    public static int getMaxCharsUsername() {
        return MAX_CHARS_USERNAME;
    }
}
