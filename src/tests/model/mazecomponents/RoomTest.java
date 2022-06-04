package tests.model.mazecomponents;

import model.mazecomponents.Room;
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

    @BeforeEach
    void setUp() {
        Random r = new Random();
        myRow = r.nextInt(6) + 4;
        myCol = r.nextInt(6) + 4;
        myTestRoom = new Room(myRow, myCol);
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
    }

    @Test
    void getAllDoors() {
    }

    @Test
    void hasDoor() {
    }

    @Test
    void getDoor() {
    }

    @Test
    void addDoor() {
    }

    @Test
    void getDoorState() {
    }

    @Test
    void setDoorState() {
    }

    @Test
    void visit() {
    }

    @Test
    void isVisited() {
        assertFalse(myTestRoom.isVisited());
        myTestRoom.visit();
        assertTrue(myTestRoom.isVisited());
    }

    @Test
    void testToString() {
    }

    @Test
    void toChar() {
    }
}