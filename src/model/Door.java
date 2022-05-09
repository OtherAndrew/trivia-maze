package model;
/**
 * Door is an interface that represents a door.
 *
 * @author Andrew Nguyen
 * @version 05/08/2022
 */
public interface Door {
    
    /**
     * @return the state of the door
     */
    State knock();
    
    /**
     * @return the pair of rooms on either side of the door.
     */
    Pair<Room> getAdjRooms();
    
    
}
