package model;

/**
 * MazeDoor is a class that represents an impassable wall.
 *
 * @author Andrew Nguyen
 * @version 05/07/2022
 */
public class Wall implements Door {
    /**
     * The wall's state.
     */
    private final State myState;
    /**
     * The rooms on either side of the wall.
     */
    private final Pair<Room> myAdjRooms;

    /**
     * Constructs a wall.
     * @param theAdjRooms the pair of rooms on either side of the wall.
     */
    public Wall(final Pair<Room> theAdjRooms) {
        myState = State.WALL;
        myAdjRooms = theAdjRooms;
    }

    /**
     * @return the wall's state.
     */
    public State knock() {
        return myState;
    }

    /**
     * @return the pair of rooms on either side of the wall.
     */
    public Pair<Room> getMyAdjoiningRooms() {
        return myAdjRooms;
    }
}
