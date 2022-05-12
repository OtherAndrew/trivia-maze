package model.mazecomponents;
/**
 * Location is a class that represents a set of X and Y coordinates relative
 * to top left.
 *
 * @version 05/07/2022
 */
public class Location {
    /**
     * The X position.
     */
    private final int myX;
    /**
     * The Y position.
     */
    private final int myY;

    /**
     * Constructs a Location object.
     * @param theX the X position.
     * @param theY the Y position.
     */
    public Location(final int theX, final int theY) {
        myX = theX;
        myY = theY;
    }

    /**
     * Constructs a safe copy of another Location object.
     * @param theOtherLocation another location object.
     */
    public Location(final Location theOtherLocation) {
        myX = theOtherLocation.x();
        myY = theOtherLocation.y();
    }

    /**
     * Gets the X component of the location.
     * @return the X position.
     */
    public int x() {
        return myX;
    }

    /**
     * Gets the Y component of the location.
     * @return the Y position.
     */
    public int y() {
        return myY;
    }
}