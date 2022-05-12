/*
 * TCSS 342
 * 
 * Pair
 * 
 * Formatted code to meet course coding conventions.
 */

package model.mazecomponents;

/**
 * A pair consists of two elements of the same type. This class illustrates the
 * definition of a generic type with type parameter <tt>T</tt>.
 *
 * @author Andrew Nguyen
 * @author Simon Gray (original)
 * @author Alan Fowler (original)
 * @version 1.1
 * 
 * @param <T>
 */
public class Pair<T> {
    
    /**
     * The first element of this Pair.
     */
    private final T myFirstElement;

    /**
     * The second element of this Pair.
     */
    private final T mySecondElement;

    /**
     * Construct an instance of a <tt>Pair</tt> initialized to the given
     * elements.
     * 
     * @param theFirstElement the first element of this pair
     * @param theSecondElement the second element of this pair
     * @throws NullPointerException if either <tt>e1</tt> or <tt>e2</tt> is
     *             <tt>null</tt>.
     */
    public Pair(final T theFirstElement, final T theSecondElement) {
        if ((theFirstElement == null) || (theSecondElement == null)) {
            throw new NullPointerException();
        }
        myFirstElement = theFirstElement;
        mySecondElement = theSecondElement;
    }

    /**
     * Return the value of the first element of this pair.
     * 
     * @return the first element of this pair
     */
    public T first() {
        return myFirstElement;
    }

    /**
     * Return the value of the second element of this pair.
     * 
     * @return the second element of this pair
     */
    public T second() {
        return mySecondElement;
    }

    /**
     * Returns a string representation of the object: this is the string
     * representation of element 1 followed by the string representation of
     * element 2.
     * 
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "< " + myFirstElement + ", " + mySecondElement + " >";
    }
}
