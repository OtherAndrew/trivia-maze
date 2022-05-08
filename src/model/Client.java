package model;

public class Client implements Player {

    /**
     * The location of the player.
     */
    protected Location myLocation;

    /**
     * Creates a Client at any location on the map.
     * @param theLocation the location of the client
     */
    public Client(final Location theLocation) {
        myLocation = theLocation;
    }

    /**
     * Creates a client at the start of the maze.
     */
    public Client() {
        this(new Location(0,0));
    }

    /**
     * Gets the player's location.
     * @return the player's location.
     */
    public Location getLocation() {
        return myLocation;
    }

    /**
     * Sets the player's location.
     * @param theLocation the location to be updated to.
     */
    public void setLocation(final Location theLocation) {
        myLocation = theLocation;
    }

    /**
     * Moves the player through a door.
     * @param theDoor the door to move through.
     * @return if move was successful.
     */
    public boolean move(final Door theDoor) {
        boolean out = false;
        if (theDoor.knock() == State.OPEN) {
            out = true;
            Pair<Room> adjRooms = theDoor.getAdjRooms();
            Location currentLocation = adjRooms.getFirstElement().getLocation();
            if (currentLocation.equals(myLocation)) {
                setLocation(adjRooms.getSecondElement().getLocation());
            } else {
                setLocation(adjRooms.getFirstElement().getLocation());
            }
        }
        return out;
    }
}