package model;

import controller.TriviaMaze;
import model.mazecomponents.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    private Maze myTestMaze;

    @BeforeEach
    void setUp() {
        myTestMaze = new Maze(new TriviaMaze(), 4, 4);
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
        assertTrue(goalRow == 0 || goalRow == 3
                || goalCol == 0 || goalCol == 3);
        assertTrue(Math.abs(playerRow - goalRow) >= 2
            || Math.abs(playerCol - goalCol) >= 2);
    }

    @Test
    void getPlayerLocation() {
        int playerRow = myTestMaze.getPlayerLocation().getRow();
        int playerCol = myTestMaze.getPlayerLocation().getCol();
        int goalRow = myTestMaze.getGoalLocation().getRow();
        int goalCol = myTestMaze.getGoalLocation().getCol();
        assertTrue(playerRow == 0 || playerRow == 3
                || playerCol == 0 || playerCol == 3);
        assertTrue(Math.abs(playerRow - goalRow) >= 2
                || Math.abs(playerCol - goalCol) >= 2);
    }

    @Test
    void getRoomVisitedNum() {
        assertEquals(1, myTestMaze.getRoomVisitedNum(true));
        assertEquals(15, myTestMaze.getRoomVisitedNum(false));
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