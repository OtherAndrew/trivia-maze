package model;

/**
 * Dev is a class that represents a developer of the game as a player
 *
 * @author Timon Tukei
 * @author Andrew Nguyen
 * @version 5/7/22
 */
public class Dev extends Client {

    /**
     * Creates a Dev at any location on the map.
     * @param theLocation the location of the client
     */
    public Dev(final Location theLocation) {
        super(theLocation);
    }

    /**
     * Creates a Dev at the start of the maze.
     */
    public Dev() {
        super(new Location(0,0));
    }

    /**
     * Teleports the developer to any room in the maze.
     * @param theRoom the room to teleport to.
     */
    public void teleport(final Room theRoom) {
        setLocation(theRoom.getLocation());
    }

    /**
     * Moves the player through a door.
     * @param theDoor the door to move through.
     * @return if move was successful.
     */
    @Override
    public boolean move(final Door theDoor) {
        Pair<Room> adjRooms = theDoor.getAdjRooms();
        Location currentLocation = adjRooms.getFirstElement().getLocation();
        if (currentLocation.equals(myLocation)) {
            setLocation(adjRooms.getSecondElement().getLocation());
        } else {
            setLocation(adjRooms.getFirstElement().getLocation());
        }
        return true;
    }
}