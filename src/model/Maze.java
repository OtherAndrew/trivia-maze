package model;

import model.mazecomponents.*;
import model.questions.Question;
import model.questions.QuestionFactory;

import java.util.*;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.*;

/**
 * Maze is a class that represents a maze with doors.
 */
public class Maze {

    /**
     * The rooms in the maze.
     */
    private final Room[][] myRooms;
    /**
     * Doors with corresponding question.
     */
    private final Map<Door, Question> myQuestionMap;
    /**
     * The goal location.
     */
    private final Room myGoalLocation;
    /**
     * The player's location.
     */
    private Room myPlayerLocation;

    /**
     * Constructs a maze of arbitrary size.
     *
     * @param theRows the number of rows the maze should have.
     * @param theCols the number of columns the maze should have.
     */
    public Maze(final int theRows, final int theCols) {
        myRooms = new Room[theRows][theCols];
        myQuestionMap = new HashMap<>();

        // TODO: Random starting location and goal, with latter being forced
        //  to be chosen a certain distance from the former
        myPlayerLocation = myRooms[0][0];
        myGoalLocation = myRooms[theRows - 1][theCols - 1];

        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
        generateMaze(generatePossibleDoors());
    }

    /**
     * Generates a list of doors for every possible door position.
     *
     * @return a list of doors for every possible door position.
     */
    private List<Door> generatePossibleDoors() {
        final List<Door> doors = new LinkedList<>();
        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                if (row + 1 < myRooms.length) {
                    doors.add(new Door(myRooms[row][col], SOUTH,
                            myRooms[row + 1][col], NORTH));
                }
                if (col + 1 < myRooms[row].length) {
                    doors.add(new Door(myRooms[row][col], EAST,
                            myRooms[row][col + 1], WEST));
                }
            }
        }
        return doors;
    }

    /**
     * Generates a randomized maze.
     *
     * @param theDoors the set of doors to join into a maze.
     */
    private void generateMaze(final List<Door> theDoors) {
        final Random rand = new Random();
        final QuestionFactory qf = new QuestionFactory();
        final HashMapDisjointSet diset = new HashMapDisjointSet(myRooms);
        while (diset.getSize() > 1) {
            final int doorIndex = rand.nextInt(theDoors.size());
            final Door door = theDoors.get(doorIndex);
            final Room room1 = door.getRoom1();
            final Room room2 = door.getRoom2();
            if (!diset.find(room1).equals(diset.find(room2))) {
                diset.join(room1, room2);
                door.setState(CLOSED);
                myQuestionMap.put(door, qf.createQuestion());
            }
            theDoors.remove(doorIndex);
        }
        qf.cleanUp();
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
        if (myPlayerLocation.getDoorState(theDirection) == OPEN) {
            successfulMove = true;
            switch (theDirection) {
                case NORTH -> myPlayerLocation = myRooms[x][y - 1];
                case EAST -> myPlayerLocation = myRooms[x + 1][y];
                case SOUTH -> myPlayerLocation = myRooms[x][y + 1];
                case WEST -> myPlayerLocation = myRooms[x - 1][y];
            }
        }
        return successfulMove;
    }

    /**
     * Gets the question associated with the door in the specified location.
     *
     * @param theDirection the direction to look in
     * @return the question associated with the door in the specified location.
     */
    public Question getQuestion(final Direction theDirection) {
        return myQuestionMap.get(myPlayerLocation.getDoor(theDirection));
    }

    /**
     * Alters specified Door in the player's Room to specified State.
     *
     * @param theDirection  the Direction of the Door.
     * @param theState      the new State of the Door.
     */
    public void changeDoorState(final Direction theDirection,
                                final State theState) {
        myPlayerLocation.getDoor(theDirection).setState(theState);
    }

    /**
     * Checks if player has reached the goal room.
     *
     * @return boolean representing success or lack thereof.
     */
    public boolean atGoal() {
        return myPlayerLocation.equals(myGoalLocation);
    }

    // FOR TESTING
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                if (myRooms[row][col].hasDoor(SOUTH)) {
                    if (myRooms[row][col].checkDoorState(SOUTH, CLOSED)) {
                        sb.append("*");
                    } else {
                        sb.append("_");
                    }
                } else {
                    sb.append("_");
                }
                if (myRooms[row][col].hasDoor(EAST)) {
                    if (myRooms[row][col].checkDoorState(EAST, CLOSED)) {
                        sb.append("");
                    } else {
                        sb.append("|");
                    }
                } else {
                    sb.append("|");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // FOR TESTING
    public static void main(final String[] theArgs) {
        final Maze maze = new Maze(2, 2);
        System.out.println(maze);
    }
}
