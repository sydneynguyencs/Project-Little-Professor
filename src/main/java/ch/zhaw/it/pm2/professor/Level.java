package ch.zhaw.it.pm2.professor;

import java.util.List;

public class Level {

    private String name;
    private int[] domain;
    private Room[] rooms;

    public Level(String name, int[] domain, Room[] rooms) {
        this.name = name;
        this.domain = domain;
        this.rooms = rooms;
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

    public String getName() {
        return name;
    }

    public int[] getDomain() {
        return domain;
    }

    public Room[] getRooms() {
        return rooms;
    }

}