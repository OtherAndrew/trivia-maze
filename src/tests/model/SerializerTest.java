package model;

import controller.TriviaMaze;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import view.Game;

import java.io.File;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SerializerTest {

    private static final Random RANDOM = new Random();
    private static final TriviaMaze CONTROLLER = new TriviaMaze();
    private static final Maze myMaze = randomMaze();
    private static final File myFile = new File("testRes/testSave.ser");

    // Credit to Baeldung
    private static boolean deleteDirectory(File deleteDirectory) {
        final File[] contents = deleteDirectory.listFiles();
        if (contents != null) for (File file : contents) deleteDirectory(file);
        return deleteDirectory.delete();
    }

    private static int randomNum() {
        return RANDOM.nextInt(7) + 4;
    }

    private static Maze randomMaze() {
        return new Maze(CONTROLLER, randomNum(), randomNum());
    }

    @BeforeAll
    static void setUp() {
        new Game(CONTROLLER);
        new File("testRes").mkdir();
    }

    @AfterAll
    static void tearDown() {
        deleteDirectory(new File("testRes"));
    }

    @Test
    void save() {
        myMaze.save(myFile);
        assertTrue(myFile.exists());
    }

    @Test
    void save_WithoutSer() {
        final File testFile = new File("testRes/testSave1");
        myMaze.save(testFile);
        assertTrue(new File(testFile + ".ser").exists());
    }

    @Test
    void save_Null() {
        assertDoesNotThrow(() -> myMaze.save(null));
    }

    @Test
    void load() {
        save();
        final Maze testMaze = randomMaze();
        testMaze.load(myFile);
        assertArrayEquals(testMaze.toCharArray(), myMaze.toCharArray());
    }

    @Test
    void load_Null() {
        save();
        assertDoesNotThrow(() -> myMaze.load(null));
    }
}