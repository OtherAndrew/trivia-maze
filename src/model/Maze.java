package model;

import model.mazecomponents.*;
import model.questions.Question;
import model.questions.QuestionFactory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static model.mazecomponents.Direction.*;
import static model.mazecomponents.State.CLOSED;
import static model.mazecomponents.State.OPEN;

/**
 * Maze is a class that represents a maze with doors.
 */
public class Maze implements Serializable {

    /**
     * The rooms in the maze.
     */
    private final Room[][] myRooms;
    /**
     * Doors with corresponding question.
     */
    private final Map<Door, Question> myQuestionMap;
    /**
     * The player's location.
     */
    private Room myPlayerLocation;
    /**
     * The goal location.
     */
    private final Room myGoalLocation;

    /**
     * Constructs a maze of arbitrary size.
     *
     * @param theRows the number of rows the maze should have.
     * @param theCols the number of columns the maze should have.
     */
    public Maze(final int theRows, final int theCols) {
        myRooms = new Room[theRows][theCols];
        myQuestionMap = new HashMap<>();

        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++)
                myRooms[row][col] = new Room(row, col);
        }

        // TODO: Random starting location and goal, with latter being forced
        //  to be chosen a certain distance from the former
        myPlayerLocation = myRooms[0][0];
        myGoalLocation = myRooms[theRows - 1][theCols - 1];

        generateMaze(generatePossibleDoors());
    }

    /**
     * Generates a list of doors for every possible door position.
     *
     * @return a list of doors for every possible door position.
     */
    private LinkedList<Door> generatePossibleDoors() {
        final LinkedList<Door> doors = new LinkedList<>();
        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                if (row + 1 < myRooms.length) {
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
                // myQuestionMap.put(door, qf.createQuestion());
            }
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
     * @param theDirection the Direction of the Door.
     * @param theState     the new State of the Door.
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
        return myPlayerLocation == myGoalLocation;
    }

    public Room getGoalLocation() {
        return myGoalLocation;
    }

    public Room getPlayerLocation() {
        return myPlayerLocation;
    }

    public boolean gameLoss() {
        return BFSRunner.findPath(this).isEmpty();
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
        final Maze maze = new Maze(6, 6);
        System.out.println(maze);
        System.out.println(maze.gameLoss());
        System.out.println(BFSRunner.findPath(maze));
    }
}
