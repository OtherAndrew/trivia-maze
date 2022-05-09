package model;
/**
 * Player is an interface that represents a player in the maze.
 *
 * @version 05/07/2022
 */
public interface Player {
    /**
     * Gets the player's location.
     * @return the player's location.
     */
    Location getLocation();

    /**
     * Moves the player though a door.
     * @param theDoor the door to move through.
     * @return if the move was successful.
     */
    boolean move(Door theDoor);

    /**
     * Sets the player's location.
     * @param theLocation the location to be updated to.
     */
    void setLocation(Location theLocation);

}