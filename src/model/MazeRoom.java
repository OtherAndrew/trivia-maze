package model;

/**
 * MazeRoom is a class that represents a room in the maze.
 *
 * @author Andrew Nguyen
 * @version 05/07/2022
 */
public class MazeRoom implements Room {
    private final Door[] myDoors;
    private final Location myLocation;

    private boolean myVisited;

    /**
     * Constructs a MazeRoom.
     * @param theDoors the set of doors on each side of the room.
     * @param theLocation the x and y coordinates of the room relative to
     *                    the top left corner.
     */
    public MazeRoom(final Door[] theDoors, final Location theLocation) {
        myDoors = theDoors;
        myVisited = false;
        myLocation = theLocation;
    }

    /**
     * @return the set of doors on each side of the room.
     */
    public Door[] getDoors() {
        return myDoors;
    }

    /**
     * Marks the room as visited.
     */
    public void visit() {
        myVisited = true;
    }

    /**
     * @return if the room has been visited.
     */
    public boolean isVisited() {
        return myVisited;
    }

    /**
     * @return the x and y coordinates of the room relative to the top left
     *         corner.
     */
    public Location getLocation() {
        return myLocation;
    }

    /**
     * Walks through the door corresponding to the specified direction.
     * @param theDirection the direction to walk through.
     */
    public void walkThrough(final Direction theDirection) {


    }
}
