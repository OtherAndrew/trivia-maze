package model;

import controller.TriviaMaze;
import model.mazecomponents.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    private Maze myTestMaze;
    private int myDim;

    @BeforeEach
    void setUp() {
        Random r = new Random();
        myDim = r.nextInt(6) + 4;
        myTestMaze = new Maze(new TriviaMaze(), myDim, myDim);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void build() {
    }

    @Test
    void attemptMove() {
    }

    @Test
    void respond() {
    }

    @Test
    void atGoal() {
    }

    @Test
    void gameLoss() {
    }

    @Test
    void getGoalLocation() {
        int playerRow = myTestMaze.getPlayerLocation().getRow();
        int playerCol = myTestMaze.getPlayerLocation().getCol();
        int goalRow = myTestMaze.getGoalLocation().getRow();
        int goalCol = myTestMaze.getGoalLocation().getCol();
        assertTrue(goalRow == 0 || goalRow == myDim - 1
                || goalCol == 0 || goalCol == myDim - 1);
        assertTrue(Math.abs(playerRow - goalRow) >= myDim / 2
            || Math.abs(playerCol - goalCol) >= myDim / 2);
    }

    @Test
    void getPlayerLocation() {
        int playerRow = myTestMaze.getPlayerLocation().getRow();
        int playerCol = myTestMaze.getPlayerLocation().getCol();
        int goalRow = myTestMaze.getGoalLocation().getRow();
        int goalCol = myTestMaze.getGoalLocation().getCol();
        assertTrue(playerRow == 0 || playerRow == myDim - 1
                || playerCol == 0 || playerCol == myDim - 1);
        assertTrue(Math.abs(playerRow - goalRow) >= myDim / 2
                || Math.abs(playerCol - goalCol) >= myDim / 2);
    }

    @Test
    void getRoomVisitedNum() {
        assertEquals(1, myTestMaze.getRoomVisitedNum(true));
        assertEquals(myDim * myDim - 1, myTestMaze.getRoomVisitedNum(false));
    }

    @Test
    void getDoorStateNum() {
        setAllDoors();
    }

    @Test
    void setAllDoors() {
        myTestMaze.setAllDoors(State.UNDISCOVERED);
        int numDoors = myTestMaze.getDoorStateNum(State.UNDISCOVERED);
        assertEquals(numDoors, myTestMaze.getDoorStateNum(State.UNDISCOVERED));
        for (State s : State.values()) {
            myTestMaze.setAllDoors(s);
            assertEquals(numDoors, myTestMaze.getDoorStateNum(s));
        }
    }

    @Test
    void toCharArray() {
        assertEquals(myDim * 2 + 1 , myTestMaze.toCharArray().length);
        assertEquals(myDim * 2 + 1, myTestMaze.toCharArray()[0].length);
    }

    @Test
    void testToString() {
    }

    @Test
    void generateDummy() {
    }

    @Test
    void save() {
    }

    @Test
    void testSave() {
    }

    @Test
    void load() {
    }

    @Test
    void testLoad() {
    }
}