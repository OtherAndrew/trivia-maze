package model;

import java.util.Random;

/**
 * MazeDoor is a class that represents a door that has an associated trivia
 * question.
 *
 * @author Andrew Nguyen
 * @version 05/07/2022
 */
public class GeneralDoor implements Door {

    /**
     * The door's state.
     */
    private State myState;
    /**
     * The trivia question associated with the door.
     */
    private final Question myQuestion;
    /**
     * The pair of rooms on either side of the door.
     */
    private final Pair<Room> myAdjRooms;

    /**
     * Constructs a GeneralDoor.
     * @param theAdjRooms the pair of rooms on either side of the door.
     */
    public GeneralDoor(final Question theQuestion,
                       final Pair<Room> theAdjRooms) {
        myQuestion = theQuestion;
        myAdjRooms = theAdjRooms;
        myState = State.CLOSED;
    }

    public GeneralDoor() {
        this(null, null);
    }

    /**
     * Opens the door.
     */
    public void open() {
        myState = State.OPEN;
    }

    /**
     * Locks the door.
     */
    public void lock() {
        myState = State.LOCKED;
    }

    /**
     * Closes the door
     */
    public void close() {
        myState = State.CLOSED;
    }

    /**
     * @return the door state.
     */
    public State knock() {
        return myState;
    }

    /**
     * @return the trivia question associated with the door
     */
    public Question ask() {
        return myQuestion;
    }

//    /**
//     * Assigns a random question to the door.
//     * @return
//     */
//    // TODO: move logic to different class?
//    private TriviaQuestion randomQuestion() {
//        TriviaQuestion out;
//        final int randomNumber = new Random().nextInt(3);
//        switch (randomNumber) {
//            case (2) -> out = new TrueFalse();
//            case (1) -> out = new ShortAnswer();
//            default -> out = new MultipleChoice();
//        }
//        return out;
//    }

    /**
     * @return the pair of rooms on either side of the door.
     */
    public Pair<Room> getAdjRooms() {
        return myAdjRooms;
    }
}
