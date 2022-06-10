package model.mazecomponents;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    private Path myTestPath;
    private List<Room> myTestRooms;
    private List<Door> myTestDoors;

    @BeforeEach
    void setUp() {
        myTestRooms = new ArrayList<>();
        myTestDoors = new ArrayList<>();
        Random r = new Random();
        int i = 0;
        myTestRooms.add(i, new Room(0, 0));
        while (i < 10) {
            i++;
            myTestRooms.add(i, new Room(0, 0));
            myTestDoors.add(new Door(myTestRooms.get(i - 1), Direction.WEST,
                    myTestRooms.get(i), Direction.EAST));
        }
        myTestPath = new Path(myTestRooms, myTestDoors);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void mark() {
        for (Room r : myTestRooms) {
            assertEquals(Symbol.UNVISITED, r.toChar());
        }
        for (Door d : myTestDoors) {
            assertEquals(Symbol.UNDISCOVERED_SYMBOL, d.toChar());
        }
        myTestPath.mark();
        for (Room r : myTestRooms) {
            assertEquals(Symbol.PATH, r.toChar());
        }
        for (Door d : myTestDoors) {
            assertEquals(Symbol.PATH, d.toChar());
        }
    }
}