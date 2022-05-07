package model;

public class Dev implements Player {

    /**
     * The location of the player
     */
    protected Location myLocation;

    public Dev() {
        this.myLocation = new Location(0,0);
    }

    public Dev(final Location theLocation) {
        this.myLocation = theLocation;
    }

    /**
     * Moves the player through a door.
     * @param theDoor the door to move through
     * @return succces indicatior
     */
    pulbic boolean move(final Door theDoor) {
        if (theDoor.kock() ==)
    }
}