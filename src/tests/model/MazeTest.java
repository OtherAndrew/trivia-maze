package model;

import controller.TriviaMaze;
import model.mazecomponents.Direction;
import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.mazecomponents.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.Game;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.*;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;
import static org.junit.jupiter.api.Assertions.*;

class MazeTest {

    private final TriviaMaze CONTROLLER = new TriviaMaze();
    private Maze myTestMaze;
    private int myDim;

    @BeforeEach
    void setUp() {
        Random r = new Random();
        myDim = r.nextInt(7) + 4;
        myTestMaze = new Maze(CONTROLLER, myDim, myDim);
        new Game(CONTROLLER);
    }

    @Test
    void constructorOutOfBounds() {
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new Maze(CONTROLLER, 5, 11));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new Maze(CONTROLLER, 5, 3));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new Maze(CONTROLLER, 3, 5));
        assertThrowsExactly(IllegalArgumentException.class,
                () -> new Maze(CONTROLLER, 11, 5));
    }

    @Test
    void build() {
        myTestMaze.build(4, 4);
        int lineBreaks = myTestMaze.toString().split("\n").length;
        assertEquals(9, lineBreaks);
        myTestMaze.build(5, 5);
        lineBreaks = myTestMaze.toString().split("\n").length;
        assertEquals(11, lineBreaks);
    }

    @Test
    void attemptMove() {
        final Room room = myTestMaze.getPlayerLocation();
        final Collection<Door> doors = myTestMaze.getPlayerLocation().getAllDoors();
        for (Door door : doors) assertEquals(CLOSED, door.getState());
        if (room.getDoor(NORTH) != null) myTestMaze.attemptMove(NORTH);
        else if (room.getDoor(EAST) != null) myTestMaze.attemptMove(EAST);
        else if (room.getDoor(SOUTH) != null) myTestMaze.attemptMove(SOUTH);
        else if (room.getDoor(WEST) != null) myTestMaze.attemptMove(WEST);
        CONTROLLER.updateQA();
        for (Door door : doors) door.setState(OPENED);
        if (room.getDoor(NORTH) != null) myTestMaze.attemptMove(NORTH);
        else if (room.getDoor(EAST) != null) myTestMaze.attemptMove(EAST);
        else if (room.getDoor(SOUTH) != null) myTestMaze.attemptMove(SOUTH);
        else if (room.getDoor(WEST) != null) myTestMaze.attemptMove(WEST);
        CONTROLLER.updateQA();
        for (Direction value : Direction.values()) {
            if (room.getDoor(value) == null) {
                myTestMaze.attemptMove(value);
            }
        }
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
        myTestMaze.setAllDoors(OPENED);
        assertEquals(0, myTestMaze.getDoorStateNum(UNDISCOVERED));
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
        final char[] test = myTestMaze.toString().replace("\n","").toCharArray();
        final char[][] charArray = myTestMaze.toCharArray();
        final int row = charArray.length, col = charArray[0].length;
        final char[] convert1D = new char[row * col];
        for (int i = 0, k = 0; i < row; i++) {
            for (int j = 0; j < col; j++, k++) {
                convert1D[k] = charArray[i][j];
            }
        }
        assertArrayEquals(convert1D, test);
    }

    @Test
    void generateDummy() {
        for (char[] row : myTestMaze.generateDummy()) {
            for (char symbol : row) {
                assertTrue("█S# !".indexOf(symbol) != -1);
            }
        }
    }

    @Test
    void save() {
        myTestMaze.save();
        final File save = new File(FileSystemView.getFileSystemView().getDefaultDirectory()
                + "/trivia-maze/quickSave.ser");
        assertTrue(save.exists());
    }

    @Test
    void load() {
        save();
        final String saved = myTestMaze.toString();
        final int dim = new Random().nextInt(7) + 4;
        myTestMaze = new Maze(CONTROLLER, dim, dim);
        assertNotEquals(saved, myTestMaze.toString());
        myTestMaze.load();
        assertEquals(saved, myTestMaze.toString());
    }
}