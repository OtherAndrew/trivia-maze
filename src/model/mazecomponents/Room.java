package model.mazecomponents;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

import static model.mazecomponents.Direction.*;

/**
 * Room is a class that represents a space in the maze that the player can
 * occupy. Each room has a wall or door on each of its four sides.
 */
public class Room implements Serializable {

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

    private boolean myVisited;

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

    public int getDoorCount() {
        return myDoors.size();
    }

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

    public void visit() {
        myVisited = true;
    }

    public boolean isVisited() {
        return myVisited;
    }

    @Override
    public String toString() {
        final String out;
        if (myVisited) {
            out = "░";
        } else {
            out = " ";
        }
        return out;
    }
}
