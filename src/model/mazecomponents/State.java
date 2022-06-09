package model.mazecomponents;

/**
 * The state of a door.
 */
public enum State {

    /**
     * Unable to passed through, but able to be opened.
     */
    CLOSED,

    /**
     * Able to be passed through freely.
     */
    OPENED,

    /**
     * Unable to be passed through or opened.
     */
    LOCKED,

    /**
     * Unable to be passed through or opened.
     */
    UNDISCOVERED
}
