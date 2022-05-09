package model;

/**
 * Room is a interface that represents a room in the maze.
 *
 * @author Andrew Nguyen
 * @version 05/08/2022
 */
public interface Room {
    /**
     * @return the set of doors on each side of the room.
     */
    Door[] getDoors();

    /**
     * Marks this room as visited.
     */
    void visit();

    /**
     * @return if this room has been visited.
     */
    boolean isVisited();

    /**
     * @return the X and Y coordinates of this room relative to the top left
     *         room.
     */
    Location getLocation();
}
