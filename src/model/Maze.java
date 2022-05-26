package model;

import model.mazecomponents.*;
import model.questions.Question;
import model.questions.QuestionFactory;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;

/**
 * Maze is a class that represents a maze with doors.
 */
public class Maze implements Serializable {

    @Serial
    private static final long serialVersionUID = 6708702333356795697L;
    /**
     * Represents the goal position.
     */
    public static final char GOAL_SYMBOL = '!';
    /**
     * Represents the player position.
     */
    public static final char PLAYER_SYMBOL = '+';
    /**
     * Represents maze walls.
     */
    public static final char WALL = '█';
    /**
     * Number of rows.
     */
    private int myHeight;
    /**
     * Number of columns.
     */
    private int myWidth;
    /**
     * The rooms in the maze.
     */
    private Room[][] myRooms;
    /**
     * Doors with corresponding question.
     */
    private Map<Door, Question> myQuestionMap;
    /**
     * The player's location.
     */
    private Room myPlayerLocation;
    /**
     * The goal location.
     */
    private Room myGoalLocation;

    /**
     * Constructs a maze of arbitrary size greater than 3 x 3.
     *
     * @param theRows the number of rows the maze should have.
     * @param theCols the number of columns the maze should have.
     */
    public Maze(final int theRows, final int theCols) {
        myHeight = theRows;
        myWidth = theCols;
        myRooms = generateRoomMatrix(theRows, theCols);
        myQuestionMap = new HashMap<>();
        myPlayerLocation = chooseRandomRoom();
        myPlayerLocation.visit();
        myGoalLocation = chooseExit();
        generateMaze(generatePossibleDoors());
    }

    /**
     * Generates a Room matrix from a set of dimensions.
     *
     * @param theRows the desired number of rows
     * @param theCols the desired number of columns
     * @return a room matrix with the specified number of rows and columns.
     */
    private Room[][] generateRoomMatrix(final int theRows, final int theCols) {
        Room[][] roomMatrix = new Room[theRows][theCols];
        for (int row = 0; row < roomMatrix.length; row++) {
            for (int col = 0; col < roomMatrix[row].length; col++) {
                roomMatrix[row][col] = new Room(row, col);
            }
        }
        return roomMatrix;
    }

    /**
     * Chooses a random room from the maze.
     *
     * @return a random room from the maze.
     */
    private Room chooseRandomRoom() {
        final Random rand = new Random();
        final int row, col;
        if (rand.nextBoolean()) {
            row = rand.nextInt(myHeight - 2) + 1;
            if (rand.nextBoolean()) {
                col = 0;
            } else {
                col = myWidth - 1;
            }
        } else {
            col = rand.nextInt(myWidth);
            if (rand.nextBoolean()) {
                row = 0;
            } else {
                row = myHeight - 1;
            }
        }
        return myRooms[row][col];
    }

    /**
     * Picks a random room from the maze that is at least half a maze
     * length/width away from the entrance.
     *
     * @return a room that is at least half a maze length/width away from the
     * given room
     */
    private Room chooseExit() {
        Room exit;
        do { exit = chooseRandomRoom();
        } while ((Math.abs(myPlayerLocation.getRow() - exit.getRow()) <= myHeight / 2)
                && (Math.abs(myPlayerLocation.getCol() - exit.getCol()) <= myWidth / 2));
        return exit;
    }

    /**
     * Generates a list of doors for every possible door position.
     *
     * @return a list of doors for every possible door position.
     */
    private LinkedList<Door> generatePossibleDoors() {
        final LinkedList<Door> doors = new LinkedList<>();
        for (int row = 0; row < myHeight; row++) {
            for (int col = 0; col < myWidth; col++) {
                if (row + 1 < myHeight) {
                    doors.push(new Door(myRooms[row][col], SOUTH,
                            myRooms[row + 1][col], NORTH));
                }
                if (col + 1 < myWidth) {
                    doors.push(new Door(myRooms[row][col], EAST,
                            myRooms[row][col + 1], WEST));
                }
            }
        }
        Collections.shuffle(doors);
        return doors;
    }

    /**
     * Generates a randomized maze.
     *
     * @param theDoors the set of doors to join into a maze.
     */
    private void generateMaze(final LinkedList<Door> theDoors) {
        final QuestionFactory qf = new QuestionFactory();
        final HashMapDisjointSet djSet = new HashMapDisjointSet(myRooms);
        while (djSet.getSize() > 1) {
            final Door door = theDoors.pop();
            final Room room1 = door.getRoom1();
            final Room room2 = door.getRoom2();
            if (!djSet.find(room1).equals(djSet.find(room2))) {
                djSet.join(room1, room2);
                door.addToRooms();
                // myQuestionMap.put(door, qf.createQuestion());
            }
        }
        qf.cleanUp();
    }

    public void attemptMove(final Direction theDirection) {
        State doorState = myPlayerLocation.getDoorState(theDirection);
        if (doorState == CLOSED) {
            getQuestion(theDirection);
            // Tell GUI to display the question and answer choices
            // enable listeners for input which will indirectly call response
        } else if (doorState == OPEN) {
            move(theDirection);
            myPlayerLocation.visit();
        }
    }

    private void move(final Direction theDirection) {
        myPlayerLocation = myPlayerLocation.getOtherSide(theDirection);
    }

    public void response(final Direction theDirection,
                         final String theResponse) {
        if (getQuestion(theDirection).checkAnswer(theResponse)) {
            myPlayerLocation.setDoorState(theDirection, OPEN);
            attemptMove(theDirection);
            atGoal();
        } else {
            myPlayerLocation.setDoorState(theDirection, LOCKED);
            gameLoss();
        }
    }

    private Question getQuestion(final Direction theDirection) {
        return myQuestionMap.get(myPlayerLocation.getDoor(theDirection));
    }

    /**
     * Gets the room where the goal is located.
     *
     * @return the goal location.
     */
    public Room getGoalLocation() {
        return myGoalLocation;
    }

    /**
     * Gets the room where the player is located.
     *
     * @return the player location.
     */
    public Room getPlayerLocation() {
        return myPlayerLocation;
    }

    /**
     * Checks if player has reached the goal room.
     *
     */
    public void atGoal() {
        if (myPlayerLocation == myGoalLocation) {
            endGame(true);
        }
    }

    /**
     * Determines if there is no longer a viable path to the goal, meaning
     * the game is lost.
     *
     */
    public void gameLoss() {
        if (BFSRunner.findPath(this).isEmpty()) {
            endGame(false);
        }
    }

    private void endGame(final boolean theWin) {
        // Tells GUI to show end of game frames
    }

    public static class Memento implements Serializable {

        @Serial
        private static final long serialVersionUID = -4739895132858153478L;

        /**
         * The rooms in the maze.
         */
        private final Room[][] mySavedRooms;
        /**
         * Doors with corresponding question.
         */
        private final Map<Door, Question> mySavedQuestionMap;
        /**
         * The player's location.
         */
        private final Room mySavedPlayerLocation;
        /**
         * The goal location.
         */
        private final Room mySavedGoalLocation;

        private Memento(final Room[][] theRooms,
                        final Map<Door, Question> theQuestionMap,
                        final Room thePlayerLocation,
                        final Room theGoalLocation) {
            mySavedRooms = theRooms;
            mySavedQuestionMap = theQuestionMap;
            mySavedPlayerLocation = thePlayerLocation;
            mySavedGoalLocation = theGoalLocation;
        }
    }

    public void quickSave() {
        save(new File("saves/quickSave.ser"));
    }

    public void save(final File theSaveFile) {
        Serializer.save(new Memento(myRooms, myQuestionMap,
                myPlayerLocation, myGoalLocation), theSaveFile);
    }

    public void quickLoad() {
        load(new File("saves/quickSave.ser"));
    }

    public void load(final File theSaveFile) {
        Serializer.load(theSaveFile).ifPresent(this::restore);
    }

    private void restore(final Memento theMemento) {
        myRooms = theMemento.mySavedRooms;
        myHeight = myRooms.length;
        myWidth = myRooms[0].length;
        myQuestionMap = theMemento.mySavedQuestionMap;
        myPlayerLocation = theMemento.mySavedPlayerLocation;
        myGoalLocation = theMemento.mySavedGoalLocation;
    }

    /**
     * Determines the number of rooms visited by the player.
     * @return the number of rooms visited by the player.
     */
    public int getNumVisited() {
        int numVisited = 0;
        for (Room[] row : myRooms) {
            for (Room room : row) {
                if (room.isVisited()) {
                    numVisited++;
                }
            }
        }
        return numVisited;
    }

    public int getStateNum(final State theState) {
        int numMatching = 0;
        for (Door door : myQuestionMap.keySet()) {
            if (door.getState() == theState) {
                numMatching++;
            }
        }
        return numMatching;
    }

    /**
     * Returns a String representation of the maze.
     *
     * @return a String representation of the maze.
     */
    @Override
    public String toString() {
        final char[][] out = generateWallMatrix();
        for (int row = 1, mazeRow = 0; row < out.length - 1;
                row += 2, mazeRow++) {
            for (int col = 1, mazeCol = 0; col < out[row].length - 1;
                    col += 2, mazeCol++) {
                final Room currentRoom = myRooms[mazeRow][mazeCol];
                if (currentRoom.equals(myPlayerLocation)) {
                    out[row][col] = PLAYER_SYMBOL;
                } else if (currentRoom.equals(myGoalLocation)) {
                    out[row][col] = GOAL_SYMBOL;
                } else {
                    out[row][col] = currentRoom.toChar();
                }
                if (currentRoom.hasDoor(EAST)) {
                    out[row][col + 1] = currentRoom.getDoor(EAST).toChar();
                }
                if (currentRoom.hasDoor(SOUTH)) {
                    out[row + 1][col] = currentRoom.getDoor(SOUTH).toChar();
                }
            }
        }
        return concatenateMatrix(out);
    }

    /**
     * Constructs a matrix of walls based on the size of the maze.
     *
     * @return a matrix of walls based on the size of the maze.
     */
    private char[][] generateWallMatrix() {
        final char[][] mazeFrame = new char[myHeight * 2 + 1][myWidth * 2 + 1];
        for (char[] row : mazeFrame) {
            Arrays.fill(row, WALL);
        }
        return mazeFrame;
    }

    /**
     * Gets a string concatenation of the input matrix.
     *
     * @param theMatrix a character matrix.
     * @return the concatenation of the input matrix.
     */
    private String concatenateMatrix(final char[][] theMatrix) {
        final StringJoiner sj = new StringJoiner("\n");
        for (char[] row : theMatrix) {
            sj.add(String.valueOf(row));
        }
        return sj.toString();
    }

    // FOR TESTING
    public static void main(final String[] theArgs) {
        Random r = new Random();
        Maze maze = new Maze(r.nextInt(8)+3, r.nextInt(8)+3);
        System.out.println(maze);
//        maze.quickSave();
        maze.quickLoad();
        System.out.println(maze);
    }
}
