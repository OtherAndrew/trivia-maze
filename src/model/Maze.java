package model;

import model.mazecomponents.Door;
import model.mazecomponents.Room;
import model.questions.Question;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static model.Direction.*;

// FOR TESTING
import static model.mazecomponents.State.CLOSED;

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
     * Constructs a maze of arbitrary size.
     * @param theRows the number of rows the maze should have.
     * @param theCols the number of columns the maze should have.
     */
    public Maze(final int theRows, final int theCols) {
        myRooms = new Room[theRows][theCols];
        myQuestionMap = new HashMap<>();
        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
        generateMaze(generatePossibleDoors());
    }

    /**
     * Generates a list of doors for every possible door position.
     * @return a list of doors for every possible door position.
     */
    private LinkedList<Door> generatePossibleDoors() {
        LinkedList<Door> doors = new LinkedList<>();
        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                if (row + 1 < myRooms.length) {
                    doors.add(new Door(myRooms[row][col], SOUTH,
                            myRooms[row + 1][col], NORTH));
                } if (col + 1 < myRooms[row].length) {
                    doors.add(new Door(myRooms[row][col], EAST,
                            myRooms[row][col + 1], WEST));
                }
            }
        }
        return doors;
    }

    /**
     * Generates a randomized maze.
     * @param theDoors the set of doors to join into a maze.
     */
    private void generateMaze(LinkedList<Door> theDoors) {
        Random rand = new Random();
        HashMapDisjointSet diset = new HashMapDisjointSet(myRooms);
        while (diset.getSize() > 1) {
            int doorIndex = rand.nextInt(theDoors.size());
            Door door = theDoors.get(doorIndex);
            Room room1 = door.getRoom1();
            Room room2 = door.getRoom2();
            if (!diset.find(room1).equals(diset.find(room2))) {
                door.close();
                // assign question to Door?
                diset.join(room1, room2);
            }
            theDoors.remove(doorIndex);
        }
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
        System.out.println(new Maze(5,6));
    }
}
