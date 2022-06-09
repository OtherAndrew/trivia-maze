package model.mazecomponents;

/**
 * Data class containing symbology for representation.
 */
public final class Symbol {

    // Maze
    /**
     * Represents the goal position.
     */
    public static final char GOAL = '!';
    /**
     * Represents the player position.
     */
    public static final char PLAYER = '@';
    /**
     * Represents the player at goal position.
     */
    public static final char PLAYER_AT_GOAL = 'G';
    /**
     * Represents the player at start position.
     */
    public static final char PLAYER_AT_START = 'S';
    /**
     * Represents the start position.
     */
    public static final char START = '*';
    /**
     * Represents maze walls.
     */
    public static final char WALL = '█';

    // Room
    /**
     * Representation for unvisited room.
     */
    public static final char UNVISITED = ' ';
    /**
     * Representation for visited room.
     */
    public static final char VISITED = '.';
    /**
     * Representation for a room in the path to goal position.
     */
    public static final char PATH = '+';

    // Door
    /**
     * Representation of closed door.
     */
    public static final char CLOSED_SYMBOL = '\\';
    /**
     * Representation of locked door.
     */
    public static final char LOCKED_SYMBOL = 'X';
    /**
     * Representation of open door.
     */
    public static final char OPENED_SYMBOL = 'O';
    /**
     * Representation of undiscovered door.
     */
    public static final char UNDISCOVERED_SYMBOL = '#';

    /**
     * Prevent instantiation.
     */
    private Symbol() {}
}
