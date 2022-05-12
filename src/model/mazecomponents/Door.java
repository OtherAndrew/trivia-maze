package model.mazecomponents;

import static model.mazecomponents.State.*;

/**
 * Door is a class that represents a maze door. Door connects two Rooms and
 * can be unlocked or locked.
 */
public class Door {

    /**
     * The first room the door connects to.
     */
    private final Room myRoom1;
    /**
     * The direction that myRoom1 is in relative to the door.
     */
    private final Direction myDirection1;
    /**
     * The second room the door connects to.
     */
    private final Room myRoom2;
    /**
     * The direction that myRoom2 is in relative to the door.
     */
    private final Direction myDirection2;

    /**
     * The door's state.
     */
    private State myState;

    /**
     * Constructs a door.
     * @param theRoom1 the first room to connect
     * @param theDirection1 the direction the first room is in
     * @param theRoom2 the second room to connect
     * @param theDirection2 the direction the second room is in
     */
    public Door(final Room theRoom1, final Direction theDirection1,
                final Room theRoom2, final Direction theDirection2) {
        myState = WALL;
        myRoom1 = theRoom1;
        myDirection1 = theDirection1;
        addDoor(myRoom1, myDirection1);
        myRoom2 = theRoom2;
        myDirection2 = theDirection2;
        addDoor(myRoom2, myDirection2);
    }

    /**
     * Adds this door to the room in the direction specified.
     * @param theRoom the room to add this door to.
     * @param theDirection the side to add the door on.
     */
    private void addDoor(final Room theRoom,
                         final Direction theDirection) {
        theRoom.addDoor(theDirection,this);
    }

    /**
     * Determines if this door connects to a room.
     * @param theRoom the room to check for connection
     * @return if theRoom connects to this door.
     */
    public boolean connects(final Room theRoom) {
        return myRoom1.equals(theRoom) || myRoom2.equals(theRoom);
    }

    /**
     * Gets the direction of this door in a room.
     * @param theRoom the room to check.
     * @return the wall this door is on.
     */
    public Direction getDirection(final Room theRoom) {
        Direction direction;
        if (myRoom1.equals(theRoom)) {
            direction = myDirection1;
        } else {
            direction = myDirection2;
        }
        return direction;
    }

    /**
     * Retrieves the first room.
     * @return the first room.
     */
    public Room getRoom1() {
        return myRoom1;
    }

    /**
     * Retrieves the second room.
     * @return the second room.
     */
    public Room getRoom2() {
        return myRoom2;
    }

    /**
     * Retrieves both rooms connected to this door.
     * @return both doors connected to this door.
     */
    public Pair<Room> getRooms() {
        return new Pair<>(myRoom1, myRoom2);
    }

    /**
     * Gets this door's state.
     * @return the door's state.
     */
    public State getState() {
        return myState;
    }

    /**
     * Closes the door.
     */
    public void close() {
        myState = CLOSED;
    }

    /**
     * Opens the door.
     */
    public void open() {
        myState = OPEN;
    }

    /**
     * Locks the door.
     */
    public void lock() {
        myState = LOCKED;
    }

}
