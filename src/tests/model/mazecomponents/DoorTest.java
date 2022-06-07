package tests.model.mazecomponents;

import model.mazecomponents.Direction;
import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.mazecomponents.State;
import org.junit.jupiter.api.*;

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
    /**
     * Representation of closed door.
     */
    public static final char CLOSED_SYMBOL = '\\';
    /**
     * Representation of locked door.
     */
    public static final char LOCKED_SYMBOL = 'X';
    /**
     * Representation of open door.
     */
    public static final char OPEN_SYMBOL = 'O';
    /**
     * Representation of undiscovered door.
     */
    public static final char UNDISCOVERED_SYMBOL = '#';

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
        assertSame(State.UNDISCOVERED, result);
    }

    @Test
    void setStateTest() {
        myDoor.setState(State.OPENED);
        assertSame(State.OPENED, myDoor.getState());
        myDoor.setState(State.CLOSED);
        assertSame(State.CLOSED, myDoor.getState());
        myDoor.setState(State.LOCKED);
        assertSame(State.LOCKED, myDoor.getState());
        myDoor.setState(State.UNDISCOVERED);
        assertSame(State.UNDISCOVERED, myDoor.getState());
    }

    @Test
    void testToString() {
        myDoor.setState(State.UNDISCOVERED);
        String result = myDoor.toString();
        assertEquals(String.valueOf(UNDISCOVERED_SYMBOL), result);
        myDoor.setState(State.CLOSED);
        result = myDoor.toString();
        assertEquals(String.valueOf(CLOSED_SYMBOL), result);
        myDoor.setState(State.OPENED);
        result = myDoor.toString();
        assertEquals(String.valueOf(OPEN_SYMBOL), result);
        myDoor.setState(State.LOCKED);
        result = myDoor.toString();
        assertEquals(String.valueOf(LOCKED_SYMBOL), result);
    }

    @Test
    void toChar() {
        myDoor.setState(State.UNDISCOVERED);
        char result = myDoor.toChar();
        assertEquals(UNDISCOVERED_SYMBOL, result);
        myDoor.setState(State.CLOSED);
        result = myDoor.toChar();
        assertEquals(CLOSED_SYMBOL, result);
        myDoor.setState(State.OPENED);
        result = myDoor.toChar();
        assertEquals(OPEN_SYMBOL, result);
        myDoor.setState(State.LOCKED);
        result = myDoor.toChar();
        assertEquals(LOCKED_SYMBOL, result);
    }
}