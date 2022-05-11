package model;

import java.util.EnumMap;
import java.util.Map;

public class Room {
    private final int myX;
    private final int myY;
    private final Map<Direction, Door> myDoors;

    public Room(final int theX, final int theY) {
        myX = theX;
        myY = theY;
        myDoors = new EnumMap<>(Direction.class);
    }

    public void addDoor(final Direction theDirection, final Door theDoor) {
        myDoors.put(theDirection, theDoor);
    }

    public boolean hasDoor(final Direction theDirection) {
        return myDoors.containsKey(theDirection);
    }

    public boolean checkDoorState(final Direction theDirection,
                                  final State theState) {
        return myDoors.get(theDirection).getState() == theState;
    }
}
