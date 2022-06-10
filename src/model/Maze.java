package model;

import controller.TriviaMaze;
import model.mazecomponents.*;
import model.questions.Question;
import model.questions.QuestionFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;
import static model.mazecomponents.Symbol.*;

/**
 * Maze is a class that represents a maze with doors and questions.
 */
public class Maze implements Serializable {

    /**
     * Class version number.
     */
    @Serial
    private static final long serialVersionUID = 6708702333356795697L;

    /**
     * The controller for the maze.
     */
    private TriviaMaze myController;
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
     * The path to the goal location.
     */
    private Path myPath;
    /**
     * Doors with corresponding question.
     */
    private Map<Door, Question> myQuestionMap;
    /**
     * The player's location.
     */
    private Room myPlayerLocation;
    /**
     * The start location.
     */
    private Room myStartLocation;
    /**
     * The goal location.
     */
    private Room myGoalLocation;

    /**
     * Creates a maze.
     *
     * @param theController the controller for the maze.
     * @param theRows       the number of rows.
     * @param theCols       the number of columns.
     * @throws IllegalArgumentException when rows or columns are outside the
     * inclusive range of 4-10.
     */
    public Maze(final TriviaMaze theController, final int theRows,
                final int theCols) throws IllegalArgumentException {
        myController = theController;
        theController.registerModel(this);
        if (theRows < 4 || theRows > 10 || theCols < 4 || theCols > 10) {
            throw new IllegalArgumentException("dimensions passed to Maze " +
                    "cannot be outside the range of 4-10 (passed values: " + theRows + ", " + theCols);
        }
        build(theRows, theCols);
    }

    /**
     * Creates a dummy maze.
     *
     * @param theRows   the number of rows.
     * @param theCols   the number of columns.
     * @throws IllegalArgumentException when rows or columns are outside the
     * inclusive range of 4-10.
     */
    private Maze(final int theRows, final int theCols) throws IllegalArgumentException {
        if (theRows < 4 || theRows > 10 || theCols < 4 || theCols > 10) {
            throw new IllegalArgumentException("dimensions passed to Maze " +
                    "cannot be outside the range of 4-10 (passed values: " + theRows + ", " + theCols);
        }
        build(theRows, theCols);
    }

    /**
     * Builds a maze and its elements.
     *
     * @param theRows   the number of rows.
     * @param theCols   the number of columns.
     */
    public void build(final int theRows, final int theCols)  {
        myHeight = theRows;
        myWidth = theCols;
        myRooms = generateRoomMatrix(theRows, theCols);
        myQuestionMap = new HashMap<>();
        myStartLocation = chooseRandomRoom();
        myPlayerLocation = myStartLocation;
        myGoalLocation = chooseExit();
        generateMaze(generatePossibleDoors());
        BFSRunner.findPath(this).ifPresent(path -> myPath = path);
        myPlayerLocation.visit();
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
     * Chooses a random room from the maze along the outer rim.
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
     * Picks a random room from the maze along the outer rim that is at least
     * half a maze length or width away from the entrance.
     *
     * @return a room that is at least half a maze length/width away from the
     * given room
     */
    private Room chooseExit() {
        Room exit;
        do {
            exit = chooseRandomRoom();
        } while ((Math.abs(myStartLocation.getRow() - exit.getRow()) <= myHeight / 2)
                && (Math.abs(myStartLocation.getCol() - exit.getCol()) <= myWidth / 2));
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
     * Generates a randomized maze via adding doors and assigning questions
     * to those doors.
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
                myQuestionMap.put(door, qf.createQuestion());
            }
        }
        qf.cleanUp();
    }

    /**
     * Attempts to move the player in the given direction through a door.
     *
     * @param theDirection  the direction to move the player.
     */
    public void attemptMove(final Direction theDirection) {
        State doorState = myPlayerLocation.getDoorState(theDirection);
        if (doorState == CLOSED || doorState == LOCKED) {
            final Question question = getQuestion(theDirection);
            myController.updateQA(question.getQuery(), question.getAnswers());
        } else if (doorState == OPENED) {
            move(theDirection);
        } else {
            myController.updateQA();
        }
    }

    /**
     * Moves the player to an adjacent room in the specified direction. If
     * the player is moved to the goal room then the game is won.
     *
     * @param theDirection the direction to move the player.
     */
    private void move(final Direction theDirection) {
        myPlayerLocation = myPlayerLocation.getOtherSide(theDirection);
        myPlayerLocation.visit();
        myController.updateMap(false);
        myController.updateQA();
        atGoal();
    }

    /**
     * Changes the state of a closed door in the direction based on the player's
     * response. If the player's response is correct then the door will be
     * opened and the player is moved to the adjacent room in the direction.
     * If the player's response is incorrect then the door will be locked and
     * the player will remain in place. If the locked door prevents movement to
     * the goal then the game is lost.
     *
     * @param theDirection the direction to move in.
     * @param theResponse the player's response.
     */
    public void respond(final Direction theDirection,
                        final String theResponse) {
        if (myPlayerLocation.getDoorState(theDirection) == CLOSED) {
            if (getQuestion(theDirection).checkAnswer(theResponse.toLowerCase().trim())) {
                myPlayerLocation.setDoorState(theDirection, OPENED);
                move(theDirection);
            } else {
                myPlayerLocation.setDoorState(theDirection, LOCKED);
                myController.updateMap(false);
                myController.updateQA();
                gameLoss();
            }
        }
    }

    /**
     * Gets the question corresponding to the door in the specified direction.
     *
     * @param theDirection the direction the door is in.
     * @return the question corresponding to the door.
     */
    private Question getQuestion(final Direction theDirection) {
        return myQuestionMap.get(myPlayerLocation.getDoor(theDirection));
    }

    /**
     * Checks if player has reached the goal room and triggers the end if so.
     */
    public void atGoal() {
        if (myPlayerLocation == myGoalLocation) {
            myController.endGame(true);
        }
    }

    /**
     * Determines if there is no longer a viable path to the goal and
     * triggers the end if so.
     */
    public void gameLoss() {
        if (BFSRunner.findPath(this).isEmpty()) {
            myPath.mark();
            myController.endGame(false);
        }
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
     * Determines the number of rooms visited or not visited by the player.
     *
     * @param theFindVisited if the method should find the number of visited
     *                       rooms (true) or the number of unvisited rooms
     *                       (false)
     * @return the number of rooms visited by the player.
     */
    public int getRoomVisitedNum(final boolean theFindVisited) {
        int numVisited = 0;
        for (Room[] row : myRooms) {
            for (Room room : row) {
                if (room.isVisited() == theFindVisited) {
                    numVisited++;
                }
            }
        }
        return numVisited;
    }

    /**
     * Determines the number of doors that match the given state.
     *
     * @param theState the state to look for.
     * @return the number of doors that match the state.
     */
    public int getDoorStateNum(final State theState) {
        int numMatching = 0;
        for (Door door : myQuestionMap.keySet()) {
            if (door.getState() == theState) {
                numMatching++;
            }
        }
        return numMatching;
    }

    /**
     * Sets all doors in the maze to the given state.
     *
     * @param theState the desired state for each door.
     */
    public void setAllDoors(final State theState) {
        for (Door door : myQuestionMap.keySet()) {
            door.setState(theState);
        }
    }

    /**
     * Returns a 2D character array representation of the maze.
     *
     * @return a 2D character array representation of the maze.
     */
    public char[][] toCharArray() {
        final char[][] out = generateWallMatrix(myHeight, myWidth);
        for (int row = 1, mazeRow = 0; row < out.length - 1;
                row += 2, mazeRow++) {
            for (int col = 1, mazeCol = 0; col < out[row].length - 1;
                    col += 2, mazeCol++) {
                final Room currentRoom = myRooms[mazeRow][mazeCol];
                if (currentRoom.equals(myPlayerLocation)) {
                    if (currentRoom.equals(myStartLocation)) {
                        out[row][col] = PLAYER_AT_START;
                    } else if (currentRoom.equals(myGoalLocation)) {
                        out[row][col] = PLAYER_AT_GOAL;
                    } else {
                        out[row][col] = PLAYER;
                    }
                } else if (currentRoom.equals(myGoalLocation)) {
                    out[row][col] = GOAL;
                } else if (currentRoom.equals(myStartLocation)) {
                    out[row][col] = START;
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
        return out;
    }

    /**
     * Constructs a matrix of walls based on the size of the maze.
     *
     * @return a matrix of walls based on the size of the maze.
     * @param theHeight the number of rows in the maze.
     * @param theWidth the number of columns in the maze.
     */
    private char[][] generateWallMatrix(final int theHeight, final int theWidth) {
        final char[][] mazeFrame = new char[theHeight * 2 + 1][theWidth * 2 + 1];
        for (char[] row : mazeFrame) {
            Arrays.fill(row, WALL);
        }
        return mazeFrame;
    }

    /**
     * Returns a String representation of the maze.
     *
     * @return a String representation of the maze.
     */
    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner("\n");
        for (char[] row : toCharArray()) {
            sj.add(String.valueOf(row));
        }
        return sj.toString();
    }

    /**
     * Returns a 2D character array representation of a dummy maze.
     *
     * @return a 2D character array representation of a dummy maze.
     */
    public char[][] generateDummy() {
        final int dim = new Random().nextInt(7) + 4;
        final Maze dummyMaze = new Maze(dim, dim);
        dummyMaze.setAllDoors(State.UNDISCOVERED);
        return dummyMaze.toCharArray();
    }

    /**
     * Creates a folder for saves and then saves a preset file to it.
     */
    public void save() {
        final File saveFolder = new File(FileSystemView.getFileSystemView().getDefaultDirectory()
                + "/trivia-maze");
        saveFolder.mkdir();
        save(new File(saveFolder + "/quickSave.ser"));
    }

    /**
     * Saves a given file to saves folder.
     *
     * @param theSaveFile   the specified file.
     */
    public void save(final File theSaveFile) {
        Serializer.save(new Memento(myRooms, myPath, myQuestionMap,
                myPlayerLocation, myGoalLocation, myStartLocation), theSaveFile);
    }

    /**
     * Loads a preset file from the saves folder.
     *
     * @return true if successful, else false.
     */
    public boolean load() {
        return load(new File(FileSystemView.getFileSystemView().getDefaultDirectory()
                + "/trivia-maze/quickSave.ser"));
    }

    /**
     * Loads a given file from saves folder.
     *
     * @param theSaveFile   the specified file.
     * @return true if successful, else false.
     */
    public boolean load(final File theSaveFile) {
        return Serializer.load(theSaveFile)
                .map(memento -> {
                    restore(memento);
                    myController.updateMap(false);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Restores a maze to a saved state via a memento.
     *
     * @param theMemento    the memento containing the state to restore.
     */
    private void restore(final Memento theMemento) {
        myRooms = theMemento.mySavedRooms;
        myPath = theMemento.mySavedPath;
        myHeight = myRooms.length;
        myWidth = myRooms[0].length;
        myQuestionMap = theMemento.mySavedQuestionMap;
        myPlayerLocation = theMemento.mySavedPlayerLocation;
        myGoalLocation = theMemento.mySavedGoalLocation;
        myStartLocation = theMemento.mySavedStartLocation;
    }

    /**
     * Memento contains the state of a maze.
     */
    static class Memento implements Serializable {

        /**
         * Class version number.
         */
        @Serial
        private static final long serialVersionUID = -4739895132858153478L;

        /**
         * The rooms in the maze.
         */
        private final Room[][] mySavedRooms;
        /**
         * The path to the goal location.
         */
        private final Path mySavedPath;
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
        /**
         * The start location.
         */
        private final Room mySavedStartLocation;

        /**
         * Creates a memento.
         *
         * @param theRooms          the rooms to save.
         * @param thePath           the path to save.
         * @param theQuestionMap    the questions and their doors to save.
         * @param thePlayerLocation the player's location to save.
         * @param theGoalLocation   the goal location to save.
         * @param theStartLocation  the starting location to save.
         */
        private Memento(final Room[][] theRooms, final Path thePath,
                        final Map<Door, Question> theQuestionMap,
                        final Room thePlayerLocation,
                        final Room theGoalLocation,
                        final Room theStartLocation) {
            mySavedRooms = theRooms;
            mySavedPath = thePath;
            mySavedQuestionMap = theQuestionMap;
            mySavedPlayerLocation = thePlayerLocation;
            mySavedGoalLocation = theGoalLocation;
            mySavedStartLocation = theStartLocation;
        }
    }
}
