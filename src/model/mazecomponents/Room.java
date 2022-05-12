package model.mazecomponents;

import model.Direction;

import java.util.EnumMap;
import java.util.Map;

/**
 * Room is a class that represents a space in the maze that the player can
 * occupy. Each room has a wall or door on each of its four sides.
 */
public class Room {
    /**
     * Relative location of this Room to top left Room.
     */
    private final Location myLocation;
    /**
     * The doors in each cardinal direction.
     */
    private final Map<Direction, Door> myDoors;

    /**
     * Constructs a Room at a Location.
     * @param theLocation the relative location of the room.
     */
    public Room(final Location theLocation) {
        myLocation = new Location(theLocation);
        myDoors = new EnumMap<>(Direction.class);
    }

    /**
     * Constructs a Room from X and Y coordinates.
     * @param theX the X coordinate.
     * @param theY the Y coordinate.
     */
    public Room(final int theX, final int theY) {
        this(new Location(theX, theY));
    }

    /**
     * Gets the Room's location.
     * @return the location of the room.
     */
    public Location getLocation() {
        return new Location(myLocation);
    }

    /**
     * Adds a Door at the specified direction.
     * @param theDirection the direction to add the Door at.
     * @param theDoor the Door to add.
     */
    public void addDoor(final Direction theDirection, final Door theDoor) {
        myDoors.put(theDirection, theDoor);
    }

    /**
     * Determines if a Door exists in the direction specified.
     * @param theDirection the direction to check in.
     * @return if a Door exists in the direction.
     */
    public boolean hasDoor(final Direction theDirection) {
        return myDoors.containsKey(theDirection);
    }

    /**
     * Checks if the state of the Door matches the state given.
     * @param theDirection the direction the door is in.
     * @param theState the state to check for.
     * @return if the Door state matches the given state.
     */
    public boolean checkDoorState(final Direction theDirection,
                                  final State theState) {
        return myDoors.get(theDirection).getState() == theState;
    }
}
