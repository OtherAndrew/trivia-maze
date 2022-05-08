package model;

/**
 * Dev is a class that represents a developer of the game as a player
 *
 * @author Timon Tukei
 * @version 5/7/22
 */
public class Dev implements Player {

    /**
     * The location of the player
     */
    protected Location myLocation;

    /**
     * Creates a developer player at the start of the maze.
     */
    public Dev() {
        this.myLocation = new Location(0,0);
    }

    /**
     * Creates a devleoper player at any location on the map.
     * @param theLocation the location of the devleoper
     */
    public Dev(final Location theLocation) {
        this.myLocation = theLocation;
    }

    /**
     * Gets the developers location
     * @return the developers location
     */
    public Location getMyLocation() {
        return myLocation;
    }

    /**
     * Sets the developers location.
     * @param myLocation the location to be updated to
     */
    private void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    /**
     * Moves the player through a door.
     * @param theDoor the door to move through
     * @return succces indicatior
     */
    pulbic boolean move(final Door theDoor) {
        if (theDoor.kock() == State.LOCKED || theDoor.kock() == State.NOTVISITED) {
            return false
        } else {
            if (theDoor.getMyAdjoiningRooms().getFirstElement().getMyLocation().equals(this.getMyLocation())) {
                setMyLocation(theDoor.getMyAdjoiningRooms().getSecondElement().getMyLocation());
            } else {
                setMyLocation(theDoor.getMyAdjoiningRooms().getFirstElement().getMyLocation());
            }
        }
    }

    /**
     * Teleports the developer to any room in the maze.
     * @param theRoom
     */
    public void teleport(final Room theRoom) {
        this.setMyLocation(theRoom.getMyLocation());
    }
}