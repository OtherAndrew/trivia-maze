package model.mazecomponents;

import java.io.Serial;
import java.io.Serializable;

import static model.mazecomponents.State.*;
import static model.mazecomponents.Symbol.*;

/**
 * Door is a class that represents a maze door. Door connects two Rooms and
 * can be unlocked or locked.
 */
public class Door implements Serializable {

    @Serial
    private static final long serialVersionUID = -6572203977734228079L;

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
     * The door's character representation.
     */
    private char mySymbol;

    /**
     * Constructs a door.
     *
     * @param theRoom1      the first room to connect
     * @param theDirection1 the direction the first room is in
     * @param theRoom2      the second room to connect
     * @param theDirection2 the direction the second room is in
     */
    public Door(final Room theRoom1, final Direction theDirection1,
                final Room theRoom2, final Direction theDirection2) {
        setState(UNDISCOVERED);
        myRoom1 = theRoom1;
        myDirection1 = theDirection1;
        myRoom2 = theRoom2;
        myDirection2 = theDirection2;
    }

    /**
     * Adds this door to the rooms in the directions specified.
     */
    public void addToRooms() {
        myRoom1.addDoor(myDirection1, this);
        myRoom2.addDoor(myDirection2, this);
    }

    public Room getOtherSide(final Room theRoom) {
        Room result = null;
        if (myRoom1.equals(theRoom)) result = myRoom2;
        else if (myRoom2.equals(theRoom)) result = myRoom1;
        return result;
    }

    /**
     * Retrieves the first room.
     *
     * @return the first room.
     */
    public Room getRoom1() {
        return myRoom1;
    }

    /**
     * Retrieves the second room.
     *
     * @return the second room.
     */
    public Room getRoom2() {
        return myRoom2;
    }

    /**
     * Gets this door's state.
     *
     * @return the door's state.
     */
    public State getState() {
        return myState;
    }

    /**
     * Sets this Door's State.
     *
     * @param theState the Door's new State.
     */
    public void setState(final State theState) {
        myState = theState;
        switch (myState) {
            case CLOSED -> mySymbol = CLOSED_SYMBOL;
            case LOCKED -> mySymbol = LOCKED_SYMBOL;
            case OPENED -> mySymbol = OPEN_SYMBOL;
            case UNDISCOVERED -> mySymbol = UNDISCOVERED_SYMBOL;
        }
    }

    /**
     * Returns the String representation of this door.
     *
     * @return the String representation of this door.
     */
    @Override
    public String toString() {
        return String.valueOf(mySymbol);
    }

    /**
     * Returns the character representation of this door.
     *
     * @return the character representation of this door.
     */
    public char toChar() {
        return mySymbol;
    }
}
