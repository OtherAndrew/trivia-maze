package model;

import model.mazecomponents.Door;
import model.mazecomponents.Room;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Path represents the route through a maze to the goal position.
 */
public class Path implements Serializable {

    /**
     * Class version number.
     */
    @Serial
    private static final long serialVersionUID = 3248167422892849651L;

    /**
     * The rooms in the path.
     */
    private final List<Room> myRooms;
    /**
     * The doors in the path.
     */
    private final List<Door> myDoors;

    /**
     * Creates a path.
     *
     * @param theRooms  the rooms in the path.
     * @param theDoors  the doors in the path.
     */
    public Path(final List<Room> theRooms, final List<Door> theDoors) {
        myRooms = theRooms;
        myDoors = theDoors;
    }

    /**
     * Changes the symbol of the rooms and doors in the path to denote such.
     */
    public void mark() {
        for (Room room : myRooms) room.setPathSymbol();
        for (Door door : myDoors) door.setPathSymbol();
    }
}
