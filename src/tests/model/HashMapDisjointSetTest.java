package model;

import model.mazecomponents.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class HashMapDisjointSetTest {

    private Random myRand;
    private Room[][] myRooms;
    private HashMapDisjointSet myDis;

    private Room getRandomRoom() {
        return myRooms[myRand.nextInt(myRooms.length)][myRand.nextInt(myRooms[0].length)];
    }

    @BeforeEach
    void setUp() {
        myRand = new Random();
        myRooms = new Room[myRand.nextInt(7) + 4][myRand.nextInt(7) + 4];
        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
        myDis = new HashMapDisjointSet(myRooms);
    }

    @Test
    void find() {
        final Room room = getRandomRoom();
        assertSame(room, myDis.find(room));
    }

    @Test
    void join() {
        final Room roomA = getRandomRoom();
        final Room roomB = getRandomRoom();
        myDis.join(roomA, roomB);
        assertEquals(myRooms.length * myRooms[0].length - 1, myDis.getSize());
        assertSame(roomB, myDis.find(roomA));
    }

    @Test
    void getSize() {
        assertEquals(myRooms.length * myRooms[0].length, myDis.getSize());
    }
}