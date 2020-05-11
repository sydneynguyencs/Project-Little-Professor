package ch.zhaw.it.pm2.professor.model;

import java.awt.*;

/**
 * Specifies the available rooms in the game. Some room types
 * provide an operation, other do not.
 */
public enum Room {
    ROOM_LEFT(Config.Operation.ADDITION, new Point(5, 8), Config.Command.LEFT),
    ROOM_UP(Config.Operation.SUBTRACTION, new Point(23, 3), Config.Command.UP),
    ROOM_RIGHT(Config.Operation.MULTIPLICATION, new Point(41, 8), Config.Command.RIGHT),
    ROOM_DOWN(Config.Operation.DIVISION, new Point(23, 13), Config.Command.DOWN),
    HALLWAY("--", new Point(23, 8));

    private Config.Operation operation = null;
    private final String name;
    private final Point position;
    private Config.Command command;
    private Boolean completed;

    Room(Config.Operation operation, Point position, Config.Command command) {
        this(operation.toString(), position);
        this.operation = operation;
        this.command = command;
        this.completed = false;
    }

    Room(String name, Point position) {
        this.name = name;
        this.position = position;
        completed = false;
    }

    @Override
    public String toString() {
        return "\n################\n#              #\n#       "+ operation.toString() +"      #\n#              #\n################\n";
    }

    public Config.Command getCommand() {
        return command;
    }

    /**
     * Returns the {@link Config.Operation} that this land provides or null,
     * if it does not provide any.
     *
     * @return the {@link Config.Operation} or null
     */
    public Config.Operation getOperation() {
        return operation;
    }

    public void addToHouse(String[] house) {
        String[] roomDepiction = Config.ROOM_LOOK;
        if (completed) {
            roomDepiction = Config.ROOM_LOOK_COMPLETED;
        }
        for (int i = 0; i < roomDepiction.length; i++) {
            String houseLine = house[this.position.y + i];
            String before = houseLine.substring(0, this.position.x);
            String after = houseLine.substring(this.position.x + roomDepiction[0].length());
            String replacement = roomDepiction[i];
            replacement = addNameToLine(replacement, i);
            house[this.position.y + i] = before + replacement + after;
        }
    }

    private String addNameToLine(String line, int lineIndex) {
        if (lineIndex == 2) {
            String before = line.substring(0, 6);
            String after = line.substring(this.name.length() + 6);
            return before + this.name + after;
        }
        return line;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean isCompleted() {
        return completed;
    }
}