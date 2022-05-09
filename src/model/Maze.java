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
        this.myRooms = setUpRoooms(this.myRooms);

        setupHorizontalDoors(this.myRooms);
        setupVerticalDoors(this.myRooms);
        myCheckpoint = new Location(0, 0);
    }

    /**
     * Gets the maze.
     * @return the maze
     */
    public Graph<Room> getMyMaze() {
        return myMaze;
    }

    /**
     * Gets the doors.
     * @return the doors
     */
    public Set<Door> getMyDoors() {
        return myDoors;
    }

    /**
     * Gets the rooms.
     * @return the rooms
     */
    public Vertex<Room>[][] getMyRooms() {
        return myRooms;
    }


    /**
     * Sets up the rooms in the maze.
     * @param theRooms the rooms to be set up
     * @return a set up collection of rooms
     */
    private Vertex<Room>[][] setUpRooms(final Vertex<Room>[][] theRooms) {
        for (int row = 0; row < theRooms[0].length; row++) {
            for (int col = 0; col < theRooms.length; col++) {
                final Room room;
                if (row != 0 || col != 0) {
                    room = new GeneralRoom(row, col);
                } else if (row == 0 && col == 0) {
                    room = new Entrance(row, col);
                } else {
                    room = new Exit(row, col);
                }
                theRooms[row][col] = new Vertex<Room>(room);
                this.myMaze.addVertex(theRooms[row][col]);
            }
        }
        return theRooms;
    }

    /**
     * Sets up the horizontal doors in the maze.
     * @param theRooms the rooms that adjoin the doors
     */
    private void setUpHorizontalDoors(final Vertex<Room>[][] theRooms) {
        for (int row = 0; row < theRooms[0].length; row++) {
            for (int col = 0; col < theRooms.length - 1; col++) {
//                question = questionGenerator???
                Room upperRoom = theRooms[row][col];
                Room lowerRoom = theRooms[row][col + 1];
                Pair<Room> roomPair = new Pair<Room>(
                        upperRoom.getLabel(), lowerRoom.getLabel());
                myMaze.addEdge(upperRoom, lowerRoom);
                myDoors.add(new GeneralDoor(question, roomPair));
            }
        }
    }

    /**
     * Sets up the vertical doors in the maze.
     * @param theRooms the rooms that adjoin the doors
     */
    private void setUpVerticalDoors(final Vertex<Room>[][] theRooms) {
        for (int col = 0; col < theRooms.length; col++) {
            for (int row = 0; row < theRooms[0].length - 1; row++) {
                this.myMaze.addEdge(theRooms[row][col], theRooms[row + 1][col]);
                this.myDoors.add(new GeneralDoor(new Pair<>(theRooms[row][col].getLabel(),
                        theRooms[row + 1][col].getLabel())));
            }
        }
    }

}