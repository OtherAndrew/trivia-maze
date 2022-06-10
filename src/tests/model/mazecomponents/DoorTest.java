package model.mazecomponents;

import org.junit.jupiter.api.*;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;
import static model.mazecomponents.Symbol.*;
import static org.junit.jupiter.api.Assertions.*;

class DoorTest {

    private Room myRoom1;
    private Room myRoom2;
    private Door myDoor;

    @BeforeEach
    void setUp() {
        myRoom1 = new Room(0, 0);
        myRoom2 = new Room(0, 1);
        myDoor = new Door(myRoom1, WEST, myRoom2, EAST);
    }

    @Test
    void addToRoomsTest() {
        myDoor.addToRooms();
        assertSame(myDoor, myRoom1.getDoor(WEST));
        assertSame(myDoor, myRoom2.getDoor(EAST));
    }

    @Test
    void getOtherSideTest() {
        assertSame(myRoom1, myDoor.getOtherSide(myRoom2));
        assertSame(myRoom2, myDoor.getOtherSide(myRoom1));
    }

    @Test
    void getOtherSideTest_UnrelatedRoom() {
        assertNull(myDoor.getOtherSide(new Room(2, 2)));
    }

    @Test
    void getRoom1Test() {
        assertSame(myRoom1, myDoor.getRoom1());
    }

    @Test
    void getRoom2Test() {
        assertSame(myRoom2, myDoor.getRoom2());
    }

    @Test
    void getStateTest() {
        assertEquals(UNDISCOVERED, myDoor.getState());
    }

    @Test
    void setStateTest() {
        myDoor.setState(CLOSED);
        assertEquals(CLOSED, myDoor.getState());
        assertEquals(CLOSED_SYMBOL, myDoor.toChar());
        myDoor.setState(LOCKED);
        assertEquals(LOCKED, myDoor.getState());
        assertEquals(LOCKED_SYMBOL, myDoor.toChar());
        myDoor.setState(OPENED);
        assertEquals(OPENED, myDoor.getState());
        assertEquals(OPENED_SYMBOL, myDoor.toChar());
        myDoor.setState(UNDISCOVERED);
        assertEquals(UNDISCOVERED, myDoor.getState());
        assertEquals(UNDISCOVERED_SYMBOL, myDoor.toChar());
    }

    @Test
    void setPathSymbol() {
        myDoor.setPathSymbol();
        assertEquals(PATH, myDoor.toChar());
    }

    @Test
    void setPathSymbol_Discovered() {
        myDoor.setState(LOCKED);
        myDoor.setPathSymbol();
        assertNotEquals(PATH, myDoor.toChar());
    }

    @Test
    void testToString() {
        assertEquals(String.valueOf(UNDISCOVERED_SYMBOL), myDoor.toString());
    }

    @Test
    void toChar() {
        assertEquals(UNDISCOVERED_SYMBOL, myDoor.toChar());
    }
}