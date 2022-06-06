package tests.model.mazecomponents;

import model.mazecomponents.Direction;
import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.mazecomponents.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoomTest {

    private Room myTestRoom;
    private int myRow;
    private int myCol;
    private Door myNorthDoor, mySouthDoor, myEastDoor, myWestDoor;

    private void addDoorsAll() {
        myTestRoom.addDoor(Direction.NORTH, myNorthDoor);
        myTestRoom.addDoor(Direction.SOUTH, mySouthDoor);
        myTestRoom.addDoor(Direction.EAST, myEastDoor);
        myTestRoom.addDoor(Direction.WEST, myWestDoor);
    }

    @BeforeEach
    void setUp() {
        Random r = new Random();
        myRow = r.nextInt(6) + 4;
        myCol = r.nextInt(6) + 4;
        myTestRoom = new Room(myRow, myCol);

        myNorthDoor = new Door(myTestRoom, Direction.NORTH, myTestRoom, Direction.SOUTH);
        mySouthDoor = new Door(myTestRoom, Direction.SOUTH, myTestRoom, Direction.NORTH);
        myEastDoor = new Door(myTestRoom, Direction.EAST, myTestRoom, Direction.WEST);
        myWestDoor = new Door(myTestRoom, Direction.WEST, myTestRoom, Direction.EAST);
    }

    @AfterEach
    void tearDown() {
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
        assertFalse(myTestRoom.hasDoor(Direction.NORTH));
        assertFalse(myTestRoom.hasDoor(Direction.SOUTH));
        assertFalse(myTestRoom.hasDoor(Direction.EAST));
        assertFalse(myTestRoom.hasDoor(Direction.WEST));
        addDoorsAll();
        assertTrue(myTestRoom.hasDoor(Direction.NORTH));
        assertTrue(myTestRoom.hasDoor(Direction.SOUTH));
        assertTrue(myTestRoom.hasDoor(Direction.EAST));
        assertTrue(myTestRoom.hasDoor(Direction.WEST));
    }

    @Test
    void getDoor() {
        assertNull(myTestRoom.getDoor(Direction.NORTH));
        assertNull(myTestRoom.getDoor(Direction.SOUTH));
        assertNull(myTestRoom.getDoor(Direction.EAST));
        assertNull(myTestRoom.getDoor(Direction.WEST));
        addDoorsAll();
    }

    @Test
    void addDoor() {
        assertFalse(myTestRoom.hasDoor(Direction.NORTH));
        assertFalse(myTestRoom.hasDoor(Direction.SOUTH));
        assertFalse(myTestRoom.hasDoor(Direction.EAST));
        assertFalse(myTestRoom.hasDoor(Direction.WEST));
        addDoorsAll();
        assertTrue(myTestRoom.hasDoor(Direction.NORTH));
        assertTrue(myTestRoom.hasDoor(Direction.SOUTH));
        assertTrue(myTestRoom.hasDoor(Direction.EAST));
        assertTrue(myTestRoom.hasDoor(Direction.WEST));
    }

    @Test
    void getDoorState() {
        assertNull(myTestRoom.getDoorState(Direction.NORTH));
        assertNull(myTestRoom.getDoorState(Direction.SOUTH));
        assertNull(myTestRoom.getDoorState(Direction.EAST));
        assertNull(myTestRoom.getDoorState(Direction.WEST));
        addDoorsAll();
        assertEquals(State.UNDISCOVERED, myTestRoom.getDoorState(Direction.NORTH));
        assertEquals(State.UNDISCOVERED, myTestRoom.getDoorState(Direction.SOUTH));
        assertEquals(State.UNDISCOVERED, myTestRoom.getDoorState(Direction.EAST));
        assertEquals(State.UNDISCOVERED, myTestRoom.getDoorState(Direction.WEST));
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
        assertFalse(myTestRoom.isVisited());
        myTestRoom.visit();
        assertTrue(myTestRoom.isVisited());
    }

    @Test
    void isVisited() {
        assertFalse(myTestRoom.isVisited());
        myTestRoom.visit();
        assertTrue(myTestRoom.isVisited());
    }

    @Test
    void testToString() {
        assertEquals(String.valueOf(Room.UNVISITED_SYMBOL), myTestRoom.toString());
        myTestRoom.visit();
        assertEquals(String.valueOf(Room.VISITED_SYMBOL), myTestRoom.toString());
    }

    @Test
    void toChar() {
        assertEquals(Room.UNVISITED_SYMBOL, myTestRoom.toChar());
        myTestRoom.visit();
        assertEquals(Room.VISITED_SYMBOL, myTestRoom.toChar());
    }
}