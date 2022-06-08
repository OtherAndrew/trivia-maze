package model;

import model.mazecomponents.Door;
import model.mazecomponents.Room;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class Path implements Serializable {

    @Serial
    private static final long serialVersionUID = 3248167422892849651L;

    private final List<Room> myRooms;
    private final List<Door> myDoors;

    public Path(final List<Room> theRooms, final List<Door> theDoors) {
        myRooms = theRooms;
        myDoors = theDoors;
    }

    public void mark() {
        for (Room room : myRooms) room.setPathSymbol();
        for (Door door : myDoors) door.setPathSymbol();
    }
}
