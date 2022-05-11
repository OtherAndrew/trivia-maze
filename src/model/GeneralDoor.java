package model;

import static model.State.WALL;

public class Door {

    private State myState;
    private final Room myRoom1;
    private final Direction myDirection1;
    private final Room myRoom2;
    private final Direction myDirection2;

    public Door(final Room theRoom1, final Direction theDirection1,
                final Room theRoom2, final Direction theDirection2) {
        myState = WALL;
        myRoom1 = theRoom1;
        myDirection1 = theDirection1;
        establishDoorRoomConnection(myRoom1, myDirection1);
        myRoom2 = theRoom2;
        myDirection2 = theDirection2;
        establishDoorRoomConnection(myRoom2, myDirection2);
    }

    private void establishDoorRoomConnection(final Room theRoom,
                                             final Direction theDirection) {
        theRoom.addDoor(theDirection,this);
    }

    public boolean connects(final Room theRoom) {
        return myRoom1.equals(theRoom) | myRoom2.equals(theRoom);
    }

    public Direction getDirection(final Room theRoom) {
        Direction direction;
        if (myRoom1.equals(theRoom)) {
            direction = myDirection1;
        } else {
            direction = myDirection2;
        }
        return direction;
    }

    public Room getRoom1() {
        return myRoom1;
    }

    public Room getRoom2() {
        return myRoom2;
    }

    public State getState() {
        return myState;
    }

    public void setState(final State theState) {
        myState = theState;
    }
}
