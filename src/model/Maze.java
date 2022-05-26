package model;

import model.mazecomponents.*;
import model.questions.Question;
import model.questions.QuestionFactory;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;

import static model.mazecomponents.Direction.*;

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
    public static final char PLAYER_SYMBOL = '●';
    /**
     * Represents maze walls.
     */
    public static final char WALL = '█';
    /**
     * Maze height in number of rooms.
     */
    private final int myHeight;
    /**
     * Maze width in number of rooms.
     */
    private final int myWidth;
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
     * Constructs a maze of arbitrary size.
     *
     * @param theRows the number of rows the maze should have.
     * @param theCols the number of columns the maze should have.
     */
    public Maze(final int theRows, final int theCols) {
        myRooms = generateRoomMatrix(theRows, theCols);
        myWidth = myRooms[0].length;
        myHeight = myRooms.length;
        myQuestionMap = new HashMap<>();
        myPlayerLocation = chooseRandomRoom();
        myPlayerLocation.visit();
        myGoalLocation = chooseExit(myPlayerLocation);
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
        final int x, y;
        if (rand.nextBoolean()) {
            x = rand.nextInt(myHeight - 2) + 1;
            if (rand.nextBoolean()) {
                y = 0;
            } else {
                y = myWidth - 1;
            }
        } else {
            y = rand.nextInt(myWidth);
            if (rand.nextBoolean()) {
                x = 0;
            } else {
                x = myHeight - 1;
            }
        }
        return myRooms[x][y];
    }

    /**
     * Picks a random room from the maze that is at least half a maze
     * length/width away from the given room.
     *
     * @param theRoom the room to compare to.
     * @return a room that is at least half a maze length/width away from the
     * given room
     */
    private Room chooseExit(final Room theRoom) {
        Room exit;
        do {
            exit = chooseRandomRoom();
        } while ((Math.abs(theRoom.getX() - exit.getX())
                    <= myHeight / 2)
                && (Math.abs(theRoom.getY() - exit.getY())
                    <= myWidth / 2));
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
            for (int col = 0; col < myRooms[row].length; col++) {
                if (row + 1 < myHeight) {
                    doors.push(new Door(myRooms[row][col], SOUTH,
                            myRooms[row + 1][col], NORTH));
                }
                if (col + 1 < myRooms[row].length) {
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
//                myQuestionMap.put(door, qf.createQuestion());
            }
        }
        qf.cleanUp();
    }

    public int getWidth() {
        return myWidth;
    }

    public int getHeight() {
        return myHeight;
    }

    /**
     * Moves the player to an adjacent room based on the direction.
     *
     * @param theDirection the direction to move the player in.
     * @return if the move was successful.
     */
    public boolean move(final Direction theDirection) {
        boolean successfulMove = false;
        final int x = myPlayerLocation.getX();
        final int y = myPlayerLocation.getY();
        State doorState = myPlayerLocation.getDoorState(theDirection);
        if (doorState == State.OPEN || doorState == State.CLOSED) {
            successfulMove = true;
            switch (theDirection) {
                case NORTH -> myPlayerLocation = myRooms[x][y - 1];
                case EAST -> myPlayerLocation = myRooms[x + 1][y];
                case SOUTH -> myPlayerLocation = myRooms[x][y + 1];
                case WEST -> myPlayerLocation = myRooms[x - 1][y];
            }
            myPlayerLocation.visit();
        }
        return successfulMove;
    }


    /**
     * Gets the question associated with the door in the specified location.
     *
     * @param theDirection the direction to look in
     * @return the question associated with the door in the specified location.
     */
    public Optional<Question> getQuestion(final Direction theDirection) {
        return Optional.ofNullable(myQuestionMap.get(myPlayerLocation.getDoor(theDirection)));
    }

    /**
     * Gets the door state in the direction specified.
     *
     * @param theDirection the direction to look in
     * @return the state of the door in the direction.
     */
    public State getDoorState(final Direction theDirection) {
        return Optional.ofNullable(myPlayerLocation.getDoorState(theDirection)).orElse(State.WALL);
    }

    /**
     * Alters specified Door in the player's Room to specified State.
     *
     * @param theDirection the Direction of the Door.
     * @param theState     the new State of the Door.
     */
    public void changeDoorState(final Direction theDirection,
                                final State theState) {
        Optional.ofNullable(myPlayerLocation.getDoor(theDirection)).ifPresent(door -> door.setState(theState));
    }

    /**
     * Checks if player has reached the goal room.
     *
     * @return boolean representing success or lack thereof.
     */
    public boolean atGoal() {
        return myPlayerLocation == myGoalLocation;
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
     * Determines if there is no longer a viable path to the goal, meaning
     * the game is lost.
     *
     * @return if the game is no longer winnable.
     */
    public boolean gameLoss() {
        return BFSRunner.findPath(this).isEmpty();
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
        final char[][] mazeFrame =
                new char[myHeight * 2 + 1][myWidth * 2 + 1];
        for (char[] row : mazeFrame) Arrays.fill(row, WALL);
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
        for (char[] row : theMatrix) sj.add(String.valueOf(row));
        return sj.toString();
    }

    // FOR TESTING
    public static void main(final String[] theArgs) {
        Random r = new Random();
        Maze maze = new Maze(r.nextInt(8)+3, r.nextInt(8)+3);
        System.out.println(maze);
        maze.quickLoad();
        System.out.println(maze);
    }
}
