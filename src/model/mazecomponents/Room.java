package model.mazecomponents;

import java.io.Serial;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * Room is a class that represents a space in the maze that the player can
 * occupy. Each room may be connected by at most four doors.
 */
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 7855965211246841891L;
    /**
     * Representation for unvisited room.
     */
    public static final char UNVISITED_SYMBOL = ' ';
    /**
     * Representation for visited room.
     */
    public static final char VISITED_SYMBOL = '.';
    /**
     * X-coordinate.
     */
    private final int myX;
    /**
     * Y-coordinate.
     */
    private final int myY;
    /**
     * The doors in each cardinal direction.
     */
    private final Map<Direction, Door> myDoors;
    /**
     * If the room has been visited.
     */
    private boolean myVisited;
    /**
     * The room's character representation.
     */
    private char mySymbol;

    /**
     * Constructor for a Room instance.
     *
     * @param theX the Room's x-coordinate.
     * @param theY the Room's y-coordinate.
     */
    public Room(final int theX, final int theY) {
        myX = theX;
        myY = theY;
        myDoors = new EnumMap<>(Direction.class);
        myVisited = false;
        mySymbol = UNVISITED_SYMBOL;
    }

    /**
     * Gets this Room's x-coordinate.
     *
     * @return an integer representing the Room's x-coordinate.
     */
    public int getX() {
        return myX;
    }

    /**
     * Gets this Room's y-coordinate.
     *
     * @return an integer representing the Room's y-coordinate.
     */
    public int getY() {
        return myY;
    }

    /**
     * Gets the number of doors that connect to this room.
     *
     * @return the number of doors connecting to this room.
     */
    public int getDoorCount() {
        return myDoors.size();
    }

    /**
     * Returns all doors connected to this room.
     *
     * @return a map of Direction -> Door
     */
    public Map<Direction, Door> getAllDoors() {
        return myDoors;
    }

    /**
     * Adds a Door at the specified direction.
     *
     * @param theDirection the direction to add the Door at.
     * @param theDoor      the Door to add.
     */
    public void addDoor(final Direction theDirection, final Door theDoor) {
        myDoors.put(theDirection, theDoor);
    }

    /**
     * Determines if a Door exists in the direction specified.
     *
     * @param theDirection the direction to check in.
     * @return if a Door exists in the direction.
     */
    public boolean hasDoor(final Direction theDirection) {
        return myDoors.containsKey(theDirection);
    }

    /**
     * Checks if the state of the Door matches the state given.
     *
     * @param theDirection the direction the door is in.
     * @param theState     the state to check for.
     * @return if the Door state matches the given state.
     */
    public boolean checkDoorState(final Direction theDirection,
                                  final State theState) {
        return getDoorState(theDirection) == theState;
    }

    /**
     * Gets the state of the door in the direction.
     *
     * @param theDirection the direction the door is in.
     * @return the state of the specified door.
     */
    public State getDoorState(final Direction theDirection) {
        return getDoor(theDirection).getState();
    }

    /**
     * Gets the door in the direction.
     *
     * @param theDirection the direction the door is in.
     * @return the door in the specified direction.
     */
    public Door getDoor(final Direction theDirection) {
        return myDoors.get(theDirection);
    }

    /**
     * Visits this room.
     */
    public void visit() {
        myVisited = true;
        mySymbol = VISITED_SYMBOL;
    }

    /**
     * Determines if this room has been visited.
     *
     * @return if the room has been visited.
     */
    public boolean isVisited() {
        return myVisited;
    }

    /**
     * Returns the String representation of this room.
     *
     * @return the String representation of this room.
     */
    @Override
    public String toString() {
        return String.valueOf(mySymbol);
    }

    /**
     * Returns the character representation of this room.
     *
     * @return the character representation of this room.
     */
    public char toChar() {
        return mySymbol;
    }
}
