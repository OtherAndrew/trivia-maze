package model.mazecomponents;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static model.mazecomponents.Symbol.*;
import static org.junit.jupiter.api.Assertions.*;

class PathTest {

    private Path myTestPath;
    private List<Room> myTestRooms;
    private List<Door> myTestDoors;

    @BeforeEach
    void setUp() {
        myTestRooms = new LinkedList<>();
        myTestDoors = new LinkedList<>();
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

    @Test
    void mark() {
        for (Room r : myTestRooms) {
            assertEquals(UNVISITED, r.toChar());
        }
        for (Door d : myTestDoors) {
            assertEquals(UNDISCOVERED_SYMBOL, d.toChar());
        }
        myTestPath.mark();
        for (Room r : myTestRooms) {
            assertEquals(PATH, r.toChar());
        }
        for (Door d : myTestDoors) {
            assertEquals(PATH, d.toChar());
        }
    }
}