package model;

import controller.TriviaMaze;
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
    public static final char GOAL = '!';
    /**
     * Represents the player position.
     */
    public static final char PLAYER_SYMBOL = '@';
    /**
     * Represents the player at goal position.
     */
    public static final char PLAYER_AT_GOAL_SYMBOL = 'G';
    /**
     * Represents the player at start position.
     */
    public static final char PLAYER_AT_START_SYMBOL = 'S';
    /**
     * Represents the start position.
     */
    public static final char START_SYMBOL = '*';
    /**
     * Represents maze walls.
     */
    public static final char WALL_SYMBOL = '█';

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

    private Maze(final int theRows, final int theCols) throws IllegalArgumentException {
        if (theRows < 4 || theRows > 10 || theCols < 4 || theCols > 10) {
            throw new IllegalArgumentException("dimensions passed to Maze " +
                    "cannot be outside the range of 4-10 (passed values: " + theRows + ", " + theCols);
        }
        build(theRows, theCols);
    }

    public void build(final int theRows, final int theCols)  {
        myHeight = theRows;
        myWidth = theCols;
        myRooms = generateRoomMatrix(theRows, theCols);
        myQuestionMap = new HashMap<>();
        myStartLocation = chooseRandomRoom();
        myPlayerLocation = myStartLocation;
        myGoalLocation = chooseExit();
        generateMaze(generatePossibleDoors());
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
                myQuestionMap.put(door, qf.createQuestion());
            }
        }
        qf.cleanUp();
    }

    public void attemptMove(final Direction theDirection) {
        if (myPlayerLocation.hasDoor(theDirection)) {
            State doorState = myPlayerLocation.getDoorState(theDirection);
            if (doorState == CLOSED) {
                final Question question = getQuestion(theDirection);
                myController.updateQA(question.getQuery(),
                        question.getAnswers());
            } else if (doorState == OPEN) {
                move(theDirection);
            } else {
                myController.updateQA();
            }
        } else {
            myController.updateQA();
        }
    }

    /**
     * Moves the player to an adjacent room in the specified direction.
     *
     * @param theDirection the direction to move the player.
     */
    private void move(final Direction theDirection) {
        myPlayerLocation = myPlayerLocation.getOtherSide(theDirection);
        myPlayerLocation.visit();
        myController.updateMap(false);
        myController.updateQA();
    }

    /**
     * Changes the state of a closed door in the direction based on the player's
     * response. If the player's response is correct then the door will be
     * opened and the player is moved to the adjacent room in the direction.
     * If the player's response is incorrect then the door will be locked and
     * the player will remain in place. If the player is moved to the goal
     * room then the game is won. If the locked door prevents movement to the
     * goal then the game is lost.
     *
     * @param theDirection the direction to move in.
     * @param theResponse the player's response.
     */
    public void respond(final Direction theDirection,
                        final String theResponse) {
        if (myPlayerLocation.hasDoor(theDirection)
                && myPlayerLocation.getDoorState(theDirection) == CLOSED) {
            if (getQuestion(theDirection).checkAnswer(theResponse.toLowerCase().trim())) {
                myPlayerLocation.setDoorState(theDirection, OPEN);
                move(theDirection);
                atGoal();
            } else {
                myPlayerLocation.setDoorState(theDirection, LOCKED);
                myController.updateMap(false);
                myController.updateQA();
                gameLoss();
//                if (!gameLoss()) {
//                    myController.updateMap(false);
//                    myController.updateQA();
//                }
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
     * Checks if player is in the start room.
     *
     * @return if the player is at the start room.
     */
    public boolean atStart() {
        return atRoom(myStartLocation);
    }

    /**
     * Checks if player has reached the goal room.
     */
    public void atGoal() {
        if (atRoom(myGoalLocation)) {
            endGame(true);
        }
    }

    /**
     * Determines if there is no longer a viable path to the goal, meaning
     * the game is lost.
     */
    public void gameLoss() {
        if (BFSRunner.findPath(this).isEmpty()) {
            endGame(false);
        }
    }

    /**
     * Determines if the player is in the specified room.
     *
     * @param theRoom the room to look in.
     * @return if the player is in the room.
     */
    public boolean atRoom(final Room theRoom) {
        return myPlayerLocation == theRoom;
    }

    private void endGame(final boolean theWinStatus) {
        myController.endGame(theWinStatus);
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
     * Gets the room where the start is located.
     */
    public Room getStartLocation() {
        return myStartLocation;
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
     * Determines the number of rooms visited by the player.
     *
     * @return the number of rooms visited by the player.
     */
    public int getRoomVisitedNum() {
        return getRoomVisitedNum(true);
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
                        out[row][col] = PLAYER_AT_START_SYMBOL;
                    } else if (currentRoom.equals(myGoalLocation)) {
                        out[row][col] = PLAYER_AT_GOAL_SYMBOL;
                    } else {
                        out[row][col] = PLAYER_SYMBOL;
                    }
                } else if (currentRoom.equals(myGoalLocation)) {
                    out[row][col] = GOAL;
                } else if (currentRoom.equals(myStartLocation)) {
                    out[row][col] = START_SYMBOL;
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
            Arrays.fill(row, WALL_SYMBOL);
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
        return concatenateMatrix(toCharArray());
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

    /**
     * Returns a representation of the Room the player is in along with the
     * doors and walls immediately surrounding it.
     *
     * @return a representation of the player room.
     */
    public char[][] playerRoomToCharArray() {
        final char[][] out = generateWallMatrix(1, 1);
        out[1][1] = PLAYER_SYMBOL;
        if (myPlayerLocation.hasDoor(NORTH)) {
            out[0][1] = myPlayerLocation.getDoor(NORTH).toChar();
        }
        if (myPlayerLocation.hasDoor(SOUTH)) {
            out[2][1] = myPlayerLocation.getDoor(SOUTH).toChar();
        }
        if (myPlayerLocation.hasDoor(EAST)) {
            out[1][2] = myPlayerLocation.getDoor(EAST).toChar();
        }
        if (myPlayerLocation.hasDoor(WEST)) {
            out[1][0] = myPlayerLocation.getDoor(WEST).toChar();
        }
        return out;
    }

    /**
     * Returns a representation of the Room the player is in along with the
     * doors and walls immediately surrounding it.
     *
     * @return a representation of the player room.
     */
    public String playerRoomToString() {
        return concatenateMatrix(playerRoomToCharArray());
    }

    public char[][] generateDummy() {
        // final int dim = new Random().nextInt(7) + 4;
        int dim = 4;
        final Maze dummyMaze = new Maze(dim, dim);
        dummyMaze.setAllDoors(State.UNDISCOVERED);
        return dummyMaze.toCharArray();
    }

    public void save() {
        final boolean quickSave = new File("saves").mkdir();
        save(new File("saves/quickSave.ser"));
    }

    public void save(final File theSaveFile) {
        Serializer.save(new Memento(myRooms, myQuestionMap,
                myPlayerLocation, myGoalLocation, myStartLocation), theSaveFile);
    }

    public boolean load() {
        return load(new File("saves/quickSave.ser"));
    }

    public boolean load(final File theSaveFile) {
        final Optional<Memento> loadFile = Serializer.load(theSaveFile);
        final boolean successfulLoad = loadFile.isPresent();
        if (successfulLoad) {
            restore(loadFile.get());
            myController.updateMap(false);
        }
        return successfulLoad;
//        Serializer.load(theSaveFile).ifPresent(this::restore);
//        myController.updateMap(false);
    }

    private void restore(final Memento theMemento) {
        myRooms = theMemento.mySavedRooms;
        myHeight = myRooms.length;
        myWidth = myRooms[0].length;
        myQuestionMap = theMemento.mySavedQuestionMap;
        myPlayerLocation = theMemento.mySavedPlayerLocation;
        myGoalLocation = theMemento.mySavedGoalLocation;
        myStartLocation = theMemento.mySavedStartLocation;
    }

    static class Memento implements Serializable {

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
        /**
         * The start location.
         */
        private final Room mySavedStartLocation;

        private Memento(final Room[][] theRooms,
                        final Map<Door, Question> theQuestionMap,
                        final Room thePlayerLocation,
                        final Room theGoalLocation,
                        final Room theStartLocation) {
            mySavedRooms = theRooms;
            mySavedQuestionMap = theQuestionMap;
            mySavedPlayerLocation = thePlayerLocation;
            mySavedGoalLocation = theGoalLocation;
            mySavedStartLocation = theStartLocation;
        }
    }
}
