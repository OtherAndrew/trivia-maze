package model.mazecomponents;

public enum State {

    /**
     * The state of a door.
     * - Closed: Unable to be passed through, but able to be opened. Will show
     *   a question
     * - Open: Able to be passed through freely, does not show a question.
     * - Locked: Unable to be passed through or opened. Will show a question.
     * - Undiscovered: Unable to be passed through or opened. does not show a question.
     */
    CLOSED, OPEN, LOCKED, UNDISCOVERED
}
