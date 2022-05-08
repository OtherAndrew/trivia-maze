package model;

public enum State {

    /**
     * The state of a door.
     * - Closed: Unable to be passed through, but able to be opened. Will show
     *   a question
     * - Open: Able to be passed through freely, does not show a question.
     * - Locked: Unable to be passed through or opened. Will show a question.
     * - Wall: Unable to be passed through or opened. Does not show a question.
     */
    CLOSED, OPEN, LOCKED, WALL;
}
