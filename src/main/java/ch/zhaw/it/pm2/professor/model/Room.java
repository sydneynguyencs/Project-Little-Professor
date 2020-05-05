package ch.zhaw.it.pm2.professor.model;

import java.awt.*;

/**
 * Specifies the available rooms in the game. Some room types
 * provide an operation, other do not.
 */
public enum Room {
    ROOM_LEFT(Config.Operation.ADDITION, new Point(0, 5)),
    ROOM_RIGHT(Config.Operation.SUBTRACTION, new Point(18, 0)),
    ROOM_UP(Config.Operation.MULTIPLICATION, new Point(36, 5)),
    ROOM_DOWN(Config.Operation.DIVISION, new Point(18, 10)),
    HALLWAY("--", new Point(18, 5));

    private Config.Operation operation = null;
    private final String name;
    private final Point position;

    Room(Config.Operation operation, Point position) {
        this(operation.toString(), position);
        this.operation = operation;
    }

    Room(String name, Point position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public String toString() {
        return "\n################\n#              #\n#       "+ operation.toString() +"      #\n#              #\n################\n";
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
        for (int i = 0; i < Config.ROOM_LOOK.length; i++) {
            String houseLine = house[this.position.y + i];
            String before = houseLine.substring(0, this.position.x);
            String after = houseLine.substring(this.position.x + Config.ROOM_LOOK[0].length());
            String replacement = Config.ROOM_LOOK[i];
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
}
