package model;

import controller.TriviaMaze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Game;

import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SerializerTest {

    private Random myRand;
    private TriviaMaze myTestController;
    private Maze myTestMaze;
    private File myTestFile;


    @BeforeEach
    void setUp() {
        myRand = new Random();
        myTestController = new TriviaMaze();
        myTestMaze = new Maze(myTestController, myRand.nextInt(7) + 4,
                myRand.nextInt(7) + 4);
        final Game gui = new Game(myTestController);
        myTestFile = new File("testRes/testSave.ser");
    }

    @Test
    void save() {
        myTestMaze.save(myTestFile);
        assertTrue(myTestFile.exists());
    }

    @Test
    void save_WithoutSer() {
        final File testFile = new File("testRes/testSave1");
        myTestMaze.save(testFile);
        assertTrue(new File(testFile + ".ser").exists());
    }

    @Test
    void save_Null() {
        assertDoesNotThrow(() -> myTestMaze.save(null));
    }

    @Test
    void load() {
        save();
        final Maze testMaze = new Maze(myTestController, myRand.nextInt(7) + 4,
                myRand.nextInt(7) + 4);
        testMaze.load(myTestFile);
        assertArrayEquals(testMaze.toCharArray(), myTestMaze.toCharArray());
    }

    @Test
    void load_Null() {
        save();
        final char[][] charArray = myTestMaze.toCharArray();
        myTestMaze.load(null);
        assertArrayEquals(charArray, myTestMaze.toCharArray());
    }
}