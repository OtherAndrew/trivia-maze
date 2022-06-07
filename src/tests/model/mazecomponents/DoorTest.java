package tests.model.mazecomponents;

import model.mazecomponents.Direction;
import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.mazecomponents.State;
import org.junit.jupiter.api.*;

import static model.mazecomponents.State.*;
import static model.mazecomponents.Symbol.*;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DoorTest {

    /**
     * The first room;.
     */
    private Room myRoom1;
    /**
     * The second room.
     */
    private Room myRoom2;
    /**
     * The door.
     */
    private Door myDoor;

    @BeforeAll
    void setUp() {
        myRoom1 = new Room(0, 0);
        myRoom2 = new Room(0, 1);
        myDoor = new Door(myRoom1, Direction.WEST, myRoom2, Direction.EAST);
    }

    @AfterAll
    void tearDown() {
        myDoor = null;
        myRoom1 = null;
        myRoom2 = null;
    }

    // TODO
    @Test
    void addToRoomsTest() {
    }

    @Test
    void getOtherSideTest() {
        Room result = myDoor.getOtherSide(myRoom1);
        assertSame(myRoom2, result);
        result = myDoor.getOtherSide(myRoom2);
        assertSame(myRoom1, result);
    }

    @Test
    void getRoom1Test() {
        Room result = myDoor.getRoom1();
        assertSame(myRoom1, result);
    }

    @Test
    void getRoom2Test() {
        Room result = myDoor.getRoom2();
        assertSame(myRoom2, result);
    }

    @Test
    void getStateTest() {
        State result = myDoor.getState();
        assertSame(UNDISCOVERED, result);
    }

    @Test
    void setStateTest() {
        myDoor.setState(OPENED);
        assertSame(OPENED, myDoor.getState());
        myDoor.setState(CLOSED);
        assertSame(CLOSED, myDoor.getState());
        myDoor.setState(LOCKED);
        assertSame(LOCKED, myDoor.getState());
        myDoor.setState(UNDISCOVERED);
        assertSame(UNDISCOVERED, myDoor.getState());
    }

    @Test
    void testToString() {
        myDoor.setState(UNDISCOVERED);
        String result = myDoor.toString();
        assertEquals(String.valueOf(UNDISCOVERED_SYMBOL), result);
        myDoor.setState(CLOSED);
        result = myDoor.toString();
        assertEquals(String.valueOf(CLOSED_SYMBOL), result);
        myDoor.setState(OPENED);
        result = myDoor.toString();
        assertEquals(String.valueOf(OPENED_SYMBOL), result);
        myDoor.setState(LOCKED);
        result = myDoor.toString();
        assertEquals(String.valueOf(LOCKED_SYMBOL), result);
    }

    @Test
    void toChar() {
        myDoor.setState(UNDISCOVERED);
        char result = myDoor.toChar();
        assertEquals(UNDISCOVERED_SYMBOL, result);
        myDoor.setState(CLOSED);
        result = myDoor.toChar();
        assertEquals(CLOSED_SYMBOL, result);
        myDoor.setState(OPENED);
        result = myDoor.toChar();
        assertEquals(OPENED_SYMBOL, result);
        myDoor.setState(LOCKED);
        result = myDoor.toChar();
        assertEquals(LOCKED_SYMBOL, result);
    }
}