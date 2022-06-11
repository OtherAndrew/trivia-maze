package model.mazecomponents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;
import static model.mazecomponents.Symbol.*;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room myTestRoom, myTestRoom2;
    private int myRow;
    private int myCol;
    private Door myNorthDoor, mySouthDoor, myEastDoor, myWestDoor;

    private void addDoorsAll() {
        myTestRoom.addDoor(NORTH, myNorthDoor);
        myTestRoom.addDoor(SOUTH, mySouthDoor);
        myTestRoom.addDoor(EAST, myEastDoor);
        myTestRoom.addDoor(WEST, myWestDoor);
    }

    @BeforeEach
    void setUp() {
        final Random r = new Random();
        myRow = r.nextInt(7) + 4;
        myCol = r.nextInt(7) + 4;
        myTestRoom = new Room(myRow, myCol);
        myTestRoom2 = new Room(0, 0);

        myNorthDoor = new Door(myTestRoom, NORTH, myTestRoom2, SOUTH);
        mySouthDoor = new Door(myTestRoom, SOUTH, myTestRoom2, NORTH);
        myEastDoor = new Door(myTestRoom, EAST, myTestRoom2, WEST);
        myWestDoor = new Door(myTestRoom, WEST, myTestRoom2, EAST);
    }

    @Test
    void constructorNegativeArgs() {
        assertThrowsExactly(IllegalArgumentException.class, () -> new Room(-1, 0));
        assertThrowsExactly(IllegalArgumentException.class, () -> new Room(0, -1));
    }

    @Test
    void getRow() {
        assertEquals(myRow, myTestRoom.getRow());
    }

    @Test
    void getCol() {
        assertEquals(myCol, myTestRoom.getCol());
    }

    @Test
    void getOtherSide() {
        assertNull(myTestRoom.getOtherSide(NORTH));
        assertNull(myTestRoom.getOtherSide(SOUTH));
        assertNull(myTestRoom.getOtherSide(EAST));
        assertNull(myTestRoom.getOtherSide(WEST));
        addDoorsAll();
        assertEquals(myTestRoom2, myTestRoom.getOtherSide(NORTH));
        assertEquals(myTestRoom2, myTestRoom.getOtherSide(SOUTH));
        assertEquals(myTestRoom2, myTestRoom.getOtherSide(EAST));
        assertEquals(myTestRoom2, myTestRoom.getOtherSide(WEST));
    }

    @Test
    void getDoorCount() {
        assertEquals(0, myTestRoom.getDoorCount());
        addDoorsAll();
        assertEquals(4, myTestRoom.getDoorCount());
    }

    @Test
    void getAllDoors() {
        assertTrue(myTestRoom.getAllDoors().isEmpty());
        addDoorsAll();
        assertFalse(myTestRoom.getAllDoors().isEmpty());
        assertEquals(4, myTestRoom.getAllDoors().size());
    }

    @Test
    void hasDoor() {
        assertFalse(myTestRoom.hasDoor(NORTH));
        assertFalse(myTestRoom.hasDoor(SOUTH));
        assertFalse(myTestRoom.hasDoor(EAST));
        assertFalse(myTestRoom.hasDoor(WEST));
        addDoorsAll();
        assertTrue(myTestRoom.hasDoor(NORTH));
        assertTrue(myTestRoom.hasDoor(SOUTH));
        assertTrue(myTestRoom.hasDoor(EAST));
        assertTrue(myTestRoom.hasDoor(WEST));
    }

    @Test
    void getDoor() {
        assertNull(myTestRoom.getDoor(NORTH));
        assertNull(myTestRoom.getDoor(SOUTH));
        assertNull(myTestRoom.getDoor(EAST));
        assertNull(myTestRoom.getDoor(WEST));
        addDoorsAll();
    }

    @Test
    void addDoor() {
        assertFalse(myTestRoom.hasDoor(NORTH));
        assertFalse(myTestRoom.hasDoor(SOUTH));
        assertFalse(myTestRoom.hasDoor(EAST));
        assertFalse(myTestRoom.hasDoor(WEST));
        addDoorsAll();
        assertTrue(myTestRoom.hasDoor(NORTH));
        assertTrue(myTestRoom.hasDoor(SOUTH));
        assertTrue(myTestRoom.hasDoor(EAST));
        assertTrue(myTestRoom.hasDoor(WEST));
    }

    @Test
    void getDoorState() {
        assertNull(myTestRoom.getDoorState(NORTH));
        assertNull(myTestRoom.getDoorState(SOUTH));
        assertNull(myTestRoom.getDoorState(EAST));
        assertNull(myTestRoom.getDoorState(WEST));
        addDoorsAll();
        assertEquals(UNDISCOVERED, myTestRoom.getDoorState(NORTH));
        assertEquals(UNDISCOVERED, myTestRoom.getDoorState(SOUTH));
        assertEquals(UNDISCOVERED, myTestRoom.getDoorState(EAST));
        assertEquals(UNDISCOVERED, myTestRoom.getDoorState(WEST));
    }

    @Test
    void setDoorState() {
        addDoorsAll();
        for (State s : State.values()) {
            for (Direction d : Direction.values()) {
                myTestRoom.setDoorState(d, s);
                assertEquals(s, myTestRoom.getDoorState(d));
            }
        }
    }

    @Test
    void visit() {
        addDoorsAll();
        assertFalse(myTestRoom.isVisited());
        for (Door door : myTestRoom.getAllDoors()) {
            assertEquals(UNDISCOVERED, door.getState());
        }
        myTestRoom.setDoorState(NORTH, LOCKED);
        assertEquals(LOCKED, myTestRoom.getDoorState(NORTH));
        myTestRoom.visit();
        assertTrue(myTestRoom.isVisited());
        assertEquals(LOCKED, myTestRoom.getDoorState(NORTH));
        assertEquals(CLOSED, myTestRoom.getDoorState(EAST));
        assertEquals(CLOSED, myTestRoom.getDoorState(SOUTH));
        assertEquals(CLOSED, myTestRoom.getDoorState(WEST));
    }

    @Test
    void isVisited() {
        assertFalse(myTestRoom.isVisited());
        myTestRoom.visit();
        assertTrue(myTestRoom.isVisited());
    }

    @Test
    void setPathSymbol() {
        assertEquals(UNVISITED, myTestRoom.toChar());
        myTestRoom.setPathSymbol();
        assertEquals(PATH, myTestRoom.toChar());
        myTestRoom.visit();
        myTestRoom.setPathSymbol();
        assertNotEquals(PATH, myTestRoom.toChar());
    }

    @Test
    void testToString() {
        assertEquals(String.valueOf(UNVISITED), myTestRoom.toString());
        myTestRoom.visit();
        assertEquals(String.valueOf(VISITED), myTestRoom.toString());
    }

    @Test
    void toChar() {
        assertEquals(UNVISITED, myTestRoom.toChar());
        myTestRoom.visit();
        assertEquals(VISITED, myTestRoom.toChar());
    }
}