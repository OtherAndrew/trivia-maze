package model;

import model.mazecomponents.State;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BFSRunnerTest {

    private Maze myTestMaze;

    @BeforeEach
    void setUp() {
        myTestMaze = new Maze(4, 4);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findPath() {
        assertTrue(BFSRunner.findPath(myTestMaze).isPresent());
        myTestMaze.setAllDoors(State.LOCKED);
        assertFalse(BFSRunner.findPath(myTestMaze).isPresent());
    }
}