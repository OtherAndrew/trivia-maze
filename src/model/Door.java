package model;

public interface Door {
    
    /**
     * Gets the state of the door.
     * @return the state of the door
     */
    State knock();
    
    /**
     * Gets the adjoining rooms of the door.
     * @return myAdjoiningRooms the adjoining rooms
     */
    Pair<Room> getMyAdjoiningRooms();
    
    
}
