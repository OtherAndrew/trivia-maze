package model.mazecomponents;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static model.mazecomponents.State.*;
import static model.mazecomponents.Symbol.*;

/**
 * Room is a class that represents a space in the maze that the player can
 * occupy. Each room may be connected by at most four doors.
 */
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 7855965211246841891L;

    /**
     * Row position.
     */
    private final int myRow;
    /**
     * Column position.
     */
    private final int myCol;
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
     * @param theRow the Room's row position.
     * @param theCol the Room's column position.
     */
    public Room(final int theRow, final int theCol) throws IllegalArgumentException {
        if (theRow < 0 || theCol < 0) {
            throw new IllegalArgumentException("Coordinates passed to Room " +
                    "cannot be less than 0 (passed values: " + theRow + ", " + theCol + ")");
        }
        myRow = theRow;
        myCol = theCol;
        myDoors = new EnumMap<>(Direction.class);
        myVisited = false;
        mySymbol = UNVISITED;
    }

    /**
     * Gets this Room's x-coordinate.
     *
     * @return an integer representing the Room's row position.
     */
    public int getRow() {
        return myRow;
    }

    /**
     * Gets this Room's y-coordinate.
     *
     * @return an integer representing the Room's column position.
     */
    public int getCol() {
        return myCol;
    }

    /**
     * Get the room on the other side of the door in a specified direction.
     *
     * @param theDirection  the direction the door is in.
     * @return  the room on the other side of the door if it exists or null.
     */
    public Room getOtherSide(final Direction theDirection) {
        return Optional.ofNullable(getDoor(theDirection))
                .map(door -> door.getOtherSide(this))
                .orElse(null);
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
    public Collection<Door> getAllDoors() {
        return myDoors.values();
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
     * Gets the door in the direction.
     *
     * @param theDirection the direction the door is in.
     * @return the door in the specified direction.
     */
    public Door getDoor(final Direction theDirection) {
        return myDoors.get(theDirection);
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
     * Gets the state of the door in the direction.
     *
     * @param theDirection the direction the door is in.
     * @return the state of the specified door or null if door does not exist.
     */
    public State getDoorState(final Direction theDirection) {
        return Optional.ofNullable(getDoor(theDirection))
                .map(Door::getState)
                .orElse(null);
    }

    public void setDoorState(final Direction theDirection,
                             final State theState) {
        Optional.ofNullable(getDoor(theDirection)).ifPresent(door -> door.setState(theState));
    }

    /**
     * Visits this room.
     */
    public void visit() {
        myVisited = true;
        mySymbol = VISITED;
        for (Door door : myDoors.values()) {
            if (door.getState() == UNDISCOVERED) {
                door.setState(CLOSED);
            }
        }
    }

    /**
     * Determines if this room has been visited.
     *
     * @return if the room has been visited.
     */
    public boolean isVisited() {
        return myVisited;
    }

    public void setPathSymbol() {
        if (!myVisited) {
            mySymbol = PATH;
        }
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
