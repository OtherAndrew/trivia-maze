package model;

/**
 * Maze is a class that
 *
 * @author
 * @version 05/08/2022
 */
public class Maze {

    /**
     * The maze.
     */
    private Graph<Room> myMaze;

    /**
     * The rooms.
     */
    private Vertex<Room> myRooms;
    /**
     * The doors in the maze.
     */
    private Set<Door> myDoors;
    /**
     * The player's current location.
     */
    private static Location myCheckpoint;

    public Maze (final int theX, final int theY) {
        myMaze = new AdjMatrixGraph<Room>();
        myDoors = new HashSet<Door>();
        myRooms = new Vertex[theX][theY];
        myCheckpoint = new Location(0, 0);
    }

}