package model.mazecomponents;

import java.util.Arrays;

public enum State {

    /**
     * The state of a door.
     * - Closed: Unable to be passed through, but able to be opened. Will show
     *   a question
     * - Open: Able to be passed through freely, does not show a question.
     * - Locked: Unable to be passed through or opened. Will show a question.
     * - Wall: No door is present
     */
    CLOSED, OPEN, LOCKED, WALL
}
