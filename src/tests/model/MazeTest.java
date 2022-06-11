package model;

import controller.TriviaMaze;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    void attemptMove() {
    }

    @Test
    void respond() {
    }

    @Test
    void atGoal() {
    }

    @Test
    void atStart() {
    }

    @Test
    void gameLoss() {
    }

    @Test
    void atRoom() {
    }

    @Test
    void getGoalLocation() {
    }

    @Test
    void getStartLocation() {
    }

    @Test
    void getPlayerLocation() {
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

    @Test
    void getRoomVisitedNum() {
    }

    @Test
    void testGetRoomVisitedNum() {
    }

    @Test
    void getDoorStateNum() {
    }

    @Test
    void setAllDoors() {
    }

    @Test
    void toCharArray() {
    }

    @Test
    void testToString() {
    }

    @Test
    void playerRoomToCharArray() {
    }

    @Test
    void playerRoomToString() {
    }
}