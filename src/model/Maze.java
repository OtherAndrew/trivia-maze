package model;

import java.util.LinkedList;
import java.util.Random;

import static model.Direction.*;

// FOR TESTING
import static model.State.CLOSED;

public class Maze {

    private final Room[][] myRooms;

    public Maze(final int theRows, final int theCols) {
        myRooms = new Room[theRows][theCols];
        for (int row = 0; row < myRooms.length; row++) {
            for (int col = 0; col < myRooms[row].length; col++) {
                myRooms[row][col] = new Room(row, col);
            }
        }
        generateMaze(generatePossibleDoors());
    }

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

    private void generateMaze(LinkedList<Door> theDoors) {
        Random rand = new Random();
        HashMapDisjointSet diset = new HashMapDisjointSet(myRooms);
        while (diset.getSize() > 1) {
            int doorIndex = rand.nextInt(theDoors.size());
            Door door = theDoors.get(doorIndex);
            Room room1 = door.getRoom1();
            Room room2 = door.getRoom2();
            if (!diset.find(room1).equals(diset.find(room2))) {
                door.setState(CLOSED);
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
