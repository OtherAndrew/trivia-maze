package model;
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
     * @return the X position.
     */
    public int getX() {
        return myX;
    }

    /**
     * @return the Y position.
     */
    public int getY() {
        return myY;
    }
}